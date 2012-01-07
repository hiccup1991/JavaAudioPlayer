package SJmp3.video.ts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProgramMapSection
{
	private int pid;
	
	private Map<Integer, ProgramElementaryStream> program_map = new HashMap<Integer, ProgramElementaryStream>();
	
	public ProgramMapSection(int pid)
	{
		this.pid = pid;
	}
	
	public ProgramElementaryStream getProgramElementaryStream(int pid)
	{
		return program_map.get(pid);
	}
	
	public ProgramElementaryStream getVideoStream()
	{
		return getStream(VIDEO);
	}
	
	public ProgramElementaryStream getAudioStream()
	{
		return getStream(AUDIO);
	}
	
	public ProgramElementaryStream getSubtitles()
	{
		return getStream(SUB);
	}
	
	// 0x02 ITU-T Rec. H.262 | ISO/IEC 13818-2 Video or ISO/IEC 11172-2 constrained parameter video stream
	// 0x03 ISO/IEC 11172-3 Audio
	public static final int VIDEO = 16;
	public static final int AUDIO = 3;
	public static final int SUB = 18;
	
	private ProgramElementaryStream getStream(int type)
	{
		Iterator<Integer> it = program_map.keySet().iterator();
		while(it.hasNext())
		{
			ProgramElementaryStream pes = program_map.get(it.next());
			if (pes.stream_type == type)
			{
				return pes;
			}
		}
		return null;
	}
	
	private synchronized void updateMap(int stream_type, int elementary_PID)
	{
		ProgramElementaryStream pes = program_map.get(elementary_PID);
		if (pes == null)
		{
			pes = new ProgramElementaryStream(stream_type, elementary_PID, 10, 1024);
			program_map.put(elementary_PID, pes);
			pes.start();
		}
		pes.state = 0;
	}
	
	private synchronized void closeStreams()
	{
		Iterator<Integer> it = program_map.keySet().iterator();
		while(it.hasNext())
		{
			ProgramElementaryStream pes = program_map.get(it.next());
			if (pes.state++ != 0)
			{
				pes.release();
			}
		}
	}
	
	public synchronized void closeAllStreams()
	{
		Iterator<Integer> it = program_map.keySet().iterator();
		while(it.hasNext())
		{
			ProgramElementaryStream pes = program_map.get(it.next());
			pes.release();
		}
	}
	
	public int getNumberOfStreams()
	{
		return program_map.size();
	}
	
	public int getPid()
	{
		return pid;
	}
	
	public void read(TsPacket packet)
	{
		int i = 0;
		if (packet.payload_unit_start_indicator == 1)
		{
			int pointer_field = packet.payload[i++];
			if (pointer_field == 0)
			{
				int table_id = packet.payload[i++] & 0xff;
				
				if (table_id == 0x2) // program_association_section
				{
					boolean section_syntax_indicator = ((packet.payload[i] >> 7) &1) == 1;
					boolean _0 = false;
					int reserved_2 = 0x3;
					int section_length = packet.payload[i++] & 0xf;
					section_length <<= 8;
					section_length |= packet.payload[i++] & 0xff;
					
					int program_number = packet.payload[i++] & 0xff;
					program_number <<= 8;
					program_number |= packet.payload[i++] & 0xff;
					
					reserved_2 = 0x3;
					int version_number = (packet.payload[i] >> 1) & 0x1f;
					boolean current_next_indicator = (packet.payload[i++] &1) == 1;
					
					int section_number = packet.payload[i++] & 0xff;
					int last_section_number = packet.payload[i++] & 0xff;
					
					int next = packet.payload[i++] & 0xff;
					next <<= 8;
					next |= packet.payload[i++] & 0xff;
					int reserved_3 = 0x7;
					int PCR_PID = next & 0x1fff;
					
					next = packet.payload[i++] & 0xff;
					next <<= 8;
					next |= packet.payload[i++] & 0xff;
					int reserved_4 = 0xf;
					int program_info_length = next & 0xfff;
					if (program_info_length > 0)
					{
						byte descriptor[] = new byte[program_info_length];
					}
					i += program_info_length;
					while (i < packet.payload.length -4)
					{
						
						int stream_type = packet.payload[i++] & 0xff; 
						
						next = packet.payload[i++] & 0xff;
						next <<= 8;
						next |= packet.payload[i++] & 0xff;
						reserved_3 = 0x7;
						int elementary_PID = next & 0x1fff;
						
						next = packet.payload[i++] & 0xff;
						next <<= 8;
						next |= packet.payload[i++] & 0xff;
						reserved_4 = 0xf;
						int ES_info_length = next & 0xfff;
						if (ES_info_length > 0)
						{
							byte descriptor[] = new byte[ES_info_length];
						}
						i += ES_info_length;
						
						updateMap(stream_type, elementary_PID);
					}
					
					int crc32 = packet.payload[i++] & 0xff;
					crc32 <<= 8;
					crc32 |= packet.payload[i++] & 0xff;
					crc32 <<= 8;
					crc32 |= packet.payload[i++] & 0xff;
					crc32 <<= 8;
					crc32 |= packet.payload[i++] & 0xff;
				} else
				{
					throw new UnsupportedOperationException("table_id");
				}
			} else
			{
				throw new UnsupportedOperationException("payload_unit_start_indicator");
			}
		}
		closeStreams();
	}
}
package SJmp3.video.ts;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import SJmp3.video.stream.ts_player;

public class mpegts extends Thread
{
	private BufferedInputStream stream = null;
	
	public static final int PACKET_SIZE = 187;
	
	private ProgramAssociationTable programAssociationTable = null;
	
	private ProgramMapSection programMapSection;
	
	private boolean release = false;
	
	private ts_player ts_player;
	
	public mpegts(String spec, ts_player ts_player) throws IOException
	{
		this.ts_player = ts_player;
		URL url = new URL(spec);
		InputStream in = url.openStream();
		stream = new BufferedInputStream(in, 1024);
	}
	
	public void run()
	{
		try
		{
			byte pak[] = new byte[PACKET_SIZE];
			int b = 0;
			while (!release)
			{
				if ((b = stream.read()) != -1)
				{
					if ((b == 0x47))
					{
						for (int i = 0; i < pak.length; i++)
						{
							
							pak[i] = (byte) stream.read();
						}
	
						TsPacket tp = new TsPacket(pak);
						getTable(tp);
	
						if (programAssociationTable != null)
						{
							if (tp.pid == programAssociationTable.program_map_PID)
							{
								if (programMapSection == null)
									programMapSection = new ProgramMapSection(tp.pid);
	
								programMapSection.read(tp);
							} else
							{
								if (programMapSection != null)
								{
									ProgramElementaryStream pes = programMapSection
											.getProgramElementaryStream(tp.pid);
									if (pes != null)
									{
										pes.write(tp.payload);
									} else
									{}
								}
							}
						}
					} else {}
				} else
				{
					release = true;
					ts_player.close();
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void getTable(TsPacket p)
	{
		switch (p.pid)
		{
		case 0x0000: // Program Association Table
			programAssociationTable = new ProgramAssociationTable();
			programAssociationTable.read(p);
			break;
		case 0x0001: // Conditional Access Table
			//break;
		case 0x0002: // Transport Stream Description Table
			//break;
		case 0x0003: // IPMP Control Information Table
			//break;
		case 0x1FFF: // Null packet
			//break;
		//default:
			//break;
		}
		//0x0004-0x000F Reserved
		//0x0010 ... 0x1FFE - May be assigned as network_PID, Program_map_PID, elementary_PID, or for other purposes
		
	}
	
	public boolean isRunning()
	{
		if (programMapSection != null)
		{
			return isAlive() && programMapSection.getNumberOfStreams() > 0;
		}
		return false;
	}
	
	public synchronized ArrayBlockingQueue<byte[]> getVideoSampleQueue()
	{
		ProgramElementaryStream video = programMapSection.getVideoStream();
		return video.getSampleQueue();
	}
	
	public synchronized ArrayBlockingQueue<byte[]> getAudioSampleQueue()
	{
		ProgramElementaryStream audio = programMapSection.getAudioStream();
		return audio.getSampleQueue();
	}
	
	public synchronized ArrayBlockingQueue<byte[]> getSubtitlesSampleQueue()
	{
		ProgramElementaryStream sub = programMapSection.getSubtitles();
		if (sub != null)
			return sub.getSampleQueue();
		else return null;
	}
	
	public void close()
	{
		release = true;
		if (stream != null)
		{
			try
			{
				stream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			if (programMapSection != null)
			{
				programMapSection.closeAllStreams();
			}
		}
	}
}
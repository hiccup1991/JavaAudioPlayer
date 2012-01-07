package SJmp3.video.ts;

public class ProgramAssociationTable
{
	public int network_PID;
	public int program_map_PID;
	
	public void read(TsPacket packet)
	{
		int i = 0;
		if (packet.payload_unit_start_indicator == 1)
		{
			int pointer_field = packet.payload[i++];
			if (pointer_field == 0)
			{
				int table_id = packet.payload[i++] & 0xff;
				
				if (table_id == 0) // program_association_section
				{
					boolean section_syntax_indicator = ((packet.payload[i] >> 7) &1) == 1;
					boolean _0 = false;
					int reserved_2 = 0x3;
					int section_length = packet.payload[i++] & 0xf;
					section_length <<= 8;
					section_length |= packet.payload[i++] & 0xff;
					
					int transport_stream_id = packet.payload[i++] & 0xff;
					transport_stream_id <<= 8;
					transport_stream_id |= packet.payload[i++] & 0xff;
					
					reserved_2 = 0x3;
					int version_number = packet.payload[i] >> 1 & 0x1f;
					boolean current_next_indicator = (packet.payload[i++] &1) == 1;
					int section_number = packet.payload[i++] & 0xff;
					int last_section_number = packet.payload[i++] & 0xff;
					
					while (i < packet.payload.length - 4)
					{
						int program_number = packet.payload[i++] & 0xff;
						program_number <<= 8;
						program_number |= packet.payload[i++] & 0xff;
						
						int next = packet.payload[i++] & 0xff;
						next <<= 8;
						next |= packet.payload[i++] & 0xff;
						
						int reserved_3 = 0x7;
						if (program_number == 0)
						{
							network_PID = next & 0x1fff;
						} else
						{
							program_map_PID = next & 0x1fff;
						}
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
	}
}
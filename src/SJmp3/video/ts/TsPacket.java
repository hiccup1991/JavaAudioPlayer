package SJmp3.video.ts;

public class TsPacket
{
	public int prefix;
	
	public int transport_error_indicator;
	public int payload_unit_start_indicator;
	public int transport_priority;
	public int pid;
	public int scrambling_control;
	public int adaptation_field_exist;
	public int continuity_counter;
	
	public byte payload[];
	
	public TsPacket(byte data[])
	{
		int i = 0;
		prefix |= data[i++] & 0xff;
		prefix <<= 8;
		prefix |= data[i++] & 0xff;
		prefix <<= 8;
		prefix |= data[i++] & 0xff;
		
		readPrefix();
		
		int adaptation_field_lenth = 0;
		
		int test_r = 3;
		
		if (adaptation_field_exist == 2 || adaptation_field_exist == 3)
		{
			adaptation_field_lenth = data[i] & 0xff;
			AdaptationField af = new AdaptationField(data, i);
			i += adaptation_field_lenth+1;
			test_r++;
		}
		
		if (adaptation_field_exist == 1 || adaptation_field_exist == 3)
		{
			payload = new byte[data.length - i];
			System.arraycopy(data, i, payload, 0, payload.length);
			
			if (test_r+ adaptation_field_lenth + payload.length != 187)
			{
				throw new UnsupportedOperationException("check payload size");
			}
		}
		
	}
	
	private void readPrefix()
	{
		transport_error_indicator = (prefix >> 23) & 1;
		payload_unit_start_indicator = (prefix >> 22) & 1; 		// 	1 	1 means start of PES data or PSI otherwise zero only.
		transport_priority  = (prefix >> 21) & 1; 				//	1 means higher priority than other packets with the same PID.
		pid = (prefix >> 8) & 0x1fff;							// 	13 	Packet ID
		scrambling_control = (prefix >> 6) &3; 					//'00' = Not scrambled.   The following per DVB spec:[12]   '01' = Reserved for future use,   '10' = Scrambled with even key,   '11' = Scrambled with odd key
		
		adaptation_field_exist = (prefix >> 4) & 3;				// 01 = no adaptation fields, payload only	
																	// 10 = adaptation field only
																	// 11 = adaptation field and payload
		continuity_counter = prefix & 0xf;
	}
	
	public void printPrefix()
	{
		int payload_unit_start_indicator = (prefix >> 23) & 1; 		// 	1 	1 means start of PES data or PSI otherwise zero only.
		int transport_priority  = (prefix >> 22) & 1; 				//	1 means higher priority than other packets with the same PID.
		int pid = (prefix >> 8) & 0x1fff;							// 	13 	Packet ID
		int scrambling_control = (prefix >> 6) &3; 					//'00' = Not scrambled.   The following per DVB spec:[12]   '01' = Reserved for future use,   '10' = Scrambled with even key,   '11' = Scrambled with odd key
		
		int adaptation_field_exist = (prefix >> 4) & 3;				// 01 = no adaptation fields, payload only	
																	// 10 = adaptation field only
																	// 11 = adaptation field and payload
		int continuity_counter = prefix & 0xf;
	}
}
package SJmp3.video.ts;

public class AdaptationField
{
	public int adaptation_field_length;
	
	public boolean discontinuity_indicator;
	public boolean random_access_indicator;
	public boolean elementary_stream_priority_indicator;
	public boolean PCR_flag;
	public boolean OPCR_flag;
	public boolean splicing_point_flag;
	public boolean transport_private_data_flag;
	public boolean adaptation_field_extension_flag;
	
	public long program_clock_reference_base;
	public int program_clock_reference_extension;
	
	public long original_program_clock_reference_base;
	public int original_program_clock_reference_extension;
	
	public int splice_countdown;
	
	public byte private_data_byte[];
	
	public AdaptationField(byte data[], int offset)
	{
		int af_offset = offset;
		adaptation_field_length = data[offset++] & 0xff;
		
		if (adaptation_field_length > 0)
		{
			int flags = data[offset++] & 0xff;
			discontinuity_indicator = ((flags >>> 7) & 1) != 0;
			
			random_access_indicator = ((flags >>> 6) & 1) != 0;
			elementary_stream_priority_indicator = ((flags >>> 5) & 1) != 0;
			PCR_flag = ((flags >>> 4) & 1) != 0;
			OPCR_flag = ((flags >>> 3) & 1) != 0;
			splicing_point_flag = ((flags >>> 2) & 1) != 0;
			transport_private_data_flag = ((flags >>> 1) & 1) != 0;
			adaptation_field_extension_flag = ((flags >>> 0) & 1) != 0;
			
			if (PCR_flag)
			{
				for (int j = 0; j < 6; j++)
				{
					program_clock_reference_base <<= 8;
					program_clock_reference_base |= data[offset++];
				}
				program_clock_reference_extension = (int)(program_clock_reference_base & 0x1ffL);
				program_clock_reference_base >>>= 17;
			}
			if (OPCR_flag)
			{
				for (int j = 0; j < 6; j++)
				{
					original_program_clock_reference_base <<= 8;
					original_program_clock_reference_base |= data[offset++];
				}
				original_program_clock_reference_extension = (int)(original_program_clock_reference_base & 0x1ffL);
				original_program_clock_reference_base >>>= 17;
			}
			if (splicing_point_flag)
			{
				splice_countdown = data[offset++] & 0xff;
			}
			if (transport_private_data_flag)
			{
				int transport_private_data_length = data[offset++] & 0xff;
				private_data_byte = new byte[transport_private_data_length];
				for (int i = 0; i < transport_private_data_length; i++)
				{
					private_data_byte[i] = data[offset++];
				}
			}
			
			if (adaptation_field_extension_flag)
			{
				int daptation_field_extension_length = data[offset] & 0xff;
				adaptationFieldExtension(data, offset+1, daptation_field_extension_length-1);
				offset += daptation_field_extension_length;
			}
			for (; offset < af_offset + adaptation_field_length;)
			{
				int stuffing_byte = data[offset++] & 0xff;
				if (stuffing_byte != 0xff)
				{
					//xxx
				}
			}
		}
	}
	
	private void adaptationFieldExtension(byte data[], int offset, int daptation_field_extension_length)
	{
		
		boolean ltw_flag = ((data[offset] >>> 7) & 1) != 0;
		boolean piecewise_rate_flag = ((data[offset] >>> 6) & 1) != 0;
		boolean seamless_splice_flag = ((data[offset++] >>> 5) & 1) != 0;
		//reserved 5 bslbf
		
		daptation_field_extension_length -= 1;
		if (ltw_flag)
		{
			boolean ltw_valid_flag = false;	// 1 bslbf
			short ltw_offset = 0;			// 15 uimsbf
			ltw_offset |= data[offset++];
			ltw_offset <<= 8;
			ltw_offset |= data[offset++];
			if (ltw_offset < 0)
			{
				ltw_valid_flag = true;
				ltw_offset ^= 0x8000;
			}
			daptation_field_extension_length -= 2;
		}
		if (piecewise_rate_flag)
		{
			//reserved 2 bslbf
			int piecewise_rate = 0; // 22 uimsbf
			piecewise_rate |= data[offset++];
			piecewise_rate <<= 8;
			piecewise_rate |= data[offset++];
			piecewise_rate <<= 8;
			piecewise_rate |= data[offset++];
			piecewise_rate >>= 2;
			
			daptation_field_extension_length -= 3;
		}
		if (seamless_splice_flag)
		{
			int splice_type = (data[offset] >>> 4) & 0xf;
			int DTS_next_AU = (data[offset] >>> 5) & 0x7; // 32..30
			int marker_bit = (data[offset++] >>> 7) & 0x1;
			
			// 29..15
			DTS_next_AU = 0;
			DTS_next_AU |= data[offset++];
			DTS_next_AU <<= 8;
			DTS_next_AU |= data[offset++];
			marker_bit = DTS_next_AU & 1;
			DTS_next_AU >>= 1;
			
			// 14..0
			DTS_next_AU = 0;
			DTS_next_AU |= data[offset++];
			DTS_next_AU <<= 8;
			DTS_next_AU |= data[offset++];
			marker_bit = DTS_next_AU & 1;
			DTS_next_AU >>= 1;
			
			daptation_field_extension_length -= 5;
		}
		for (int i = 0; i < daptation_field_extension_length; i++)
		{
			int reserved = data[offset++];
		}
	}
}
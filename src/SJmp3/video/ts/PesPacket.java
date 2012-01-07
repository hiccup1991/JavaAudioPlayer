package SJmp3.video.ts;

import java.io.IOException;

public class PesPacket
{
	public int stream_id;
	public int stream_number;
	public int PES_packet_length;
	
	public long pts;
	public long dts;
	
	public byte[] sample;
	
	public PesPacket(int stream_id, int stream_number, int PES_packet_length)
	{
		this.stream_id = stream_id;
		this.stream_number = stream_number;
		this.PES_packet_length = PES_packet_length;
	}
	
	public static final int program_stream_map = 0xBC;
	public static final int private_stream_1 = 0xBD;
	public static final int padding_stream = 0xBE;
	public static final int private_stream_2 = 0xBF;
	// audio stream 0xC0..0xDF
	// video stream 0xE0..0xEF
	public static final int ECM = 0xF0;
	public static final int EMM = 0xF1;
	public static final int DSMCC_stream = 0xF2;
	public static final int iso_13522_stream = 0xF3;
	public static final int h2221_typeA = 0xF4;
	public static final int h2221_typeB = 0xF5;
	public static final int h2221_typeC = 0xF6;
	public static final int h2221_typeD = 0xF7;
	public static final int h2221_typeE = 0xF8;
	public static final int ancillary_stream = 0xF9;
	public static final int iso_144961_SL_packetized_stream = 0xFA;
	public static final int iso_144961_FlexMux_stream = 0xFB;
	// reserved data stream FC..FE
	public static final int program_stream_directory = 0xFF;
	
	public static final int fast_forward = 0;
	public static final int slow_motion = 1;
	public static final int freeze_frame = 2;
	public static final int fast_reverse = 3;
	public static final int slow_reverse = 4;
	
	public void read(Bitstream bitstream) throws IOException
	{
		if (stream_id != program_stream_map
				&& stream_id != padding_stream
				&& stream_id != private_stream_2
				&& stream_id != ECM
				&& stream_id != EMM
				&& stream_id != program_stream_directory
				&& stream_id != DSMCC_stream
				&& stream_id != h2221_typeE)
		{

			int _10 = bitstream.getBits(2);
			int PES_scrambling_control = bitstream.getBits(2);
			boolean PES_priority = bitstream.getBit();
			boolean data_alignment_indicator = bitstream.getBit();
			boolean copyright = bitstream.getBit();
			boolean original_or_copy = bitstream.getBit();
			int PTS_DTS_flags = bitstream.getBits(2);
			boolean ESCR_flag = bitstream.getBit();
			boolean ES_rate_flag = bitstream.getBit();
			boolean DSM_trick_mode_flag = bitstream.getBit();
			boolean additional_copy_info_flag = bitstream.getBit();
			boolean PES_CRC_flag = bitstream.getBit();
			boolean PES_extension_flag = bitstream.getBit();
			int PES_header_data_length = bitstream.getBits(8);
			if (PTS_DTS_flags == 2)
			{
				int _0010 = bitstream.getBits(4);
				int PTS_32_30 = bitstream.getBits(3);
				boolean marker_bit = bitstream.getBit();
				int PTS_29_15 = bitstream.getBits(15);
				marker_bit = bitstream.getBit();
				int PTS_14_0 = bitstream.getBits(15);
				marker_bit = bitstream.getBit();

				pts = PTS_32_30;
				pts <<= 15;
				pts |= PTS_29_15;
				pts <<= 15;
				pts |= PTS_14_0;
			}
			if (PTS_DTS_flags == 3)
			{
				int _0011 = bitstream.getBits(4);
				int PTS_32_30 = bitstream.getBits(3);
				boolean marker_bit = bitstream.getBit();
				int PTS_29_15 = bitstream.getBits(15);
				marker_bit = bitstream.getBit();
				int PTS_14_0 = bitstream.getBits(15);
				marker_bit = bitstream.getBit();
				int _0001 = bitstream.getBits(4);
				int DTS_32_30 = bitstream.getBits(3);
				marker_bit = bitstream.getBit();
				int DTS_29_15 = bitstream.getBits(15);
				marker_bit = bitstream.getBit();
				int DTS_14_0 = bitstream.getBits(15);
				marker_bit = bitstream.getBit();

				pts = PTS_32_30;
				pts <<= 15;
				pts |= PTS_29_15;
				pts <<= 15;
				pts |= PTS_14_0;

				dts = DTS_32_30;
				dts <<= 15;
				dts |= DTS_29_15;
				dts <<= 15;
				dts |= DTS_14_0;
			}
			if (ESCR_flag)
			{
				int reserved = bitstream.getBits(2);
				int ESCR_base_32_30 = bitstream.getBits(3);
				boolean marker_bit = bitstream.getBit();
				int ESCR_base_29_15 = bitstream.getBits(15);
				marker_bit = bitstream.getBit();
				int ESCR_base_14_0 = bitstream.getBits(15);
				marker_bit = bitstream.getBit();
				int ESCR_extension = bitstream.getBits(9);
				marker_bit = bitstream.getBit();

				long escr = ESCR_base_32_30;
				escr <<= 15;
				escr |= ESCR_base_29_15;
				escr <<= 15;
				escr |= ESCR_base_14_0;
			}
			if (ES_rate_flag)
			{
				boolean marker_bit = bitstream.getBit();
				int ES_rate = bitstream.getBits(22);
				marker_bit = bitstream.getBit();
			}
			if (DSM_trick_mode_flag)
			{
				int trick_mode_control = bitstream.getBits(3);
				if (trick_mode_control == fast_forward)
				{
					int field_id = bitstream.getBits(2);
					boolean intra_slice_refresh = bitstream.getBit();
					int frequency_truncation = bitstream.getBits(2);
				} else if (trick_mode_control == slow_motion)
				{
					int rep_cntrl = bitstream.getBits(5);
				} else if (trick_mode_control == freeze_frame)
				{
					int field_id = bitstream.getBits(2);
					int reserved = bitstream.getBits(3);
				} else if (trick_mode_control == fast_reverse)
				{
					int field_id = bitstream.getBits(2);
					int intra_slice_refresh = bitstream.getBits(1);
					int frequency_truncation = bitstream.getBits(2);
				} else if (trick_mode_control == slow_reverse)
				{
					int rep_cntrl = bitstream.getBits(5);
				} else
				{
					int res = bitstream.getBits(5);
				}
			}
			if (additional_copy_info_flag)
			{
				boolean marker_bit = bitstream.getBit();
				int additional_copy_info = bitstream.getBits(7);
			}
			if (PES_CRC_flag)
			{
				int previous_PES_packet_CRC = bitstream.getBits(16);
			}
			if (PES_extension_flag)
			{
				boolean PES_private_data_flag = bitstream.getBit();
				boolean pack_header_field_flag = bitstream.getBit();
				boolean program_packet_sequence_counter_flag = bitstream.getBit();
				boolean P_STD_buffer_flag = bitstream.getBit();
				int reserved = bitstream.getBits(3);
				boolean PES_extension_flag_2 = bitstream.getBit();
				if (PES_private_data_flag)
				{
					// PES_private_data 128 bslbf
					byte[] PES_private_data = new byte[16];
					for (int i = 0; i < PES_private_data.length; i++)
					{
						PES_private_data[i] = (byte) bitstream.getBits(8);
					}
				}
				if (pack_header_field_flag)
				{
					int pack_field_length = bitstream.getBits(8);
					pack_header();
				}
				if (program_packet_sequence_counter_flag)
				{
					boolean marker_bit = bitstream.getBit();
					int program_packet_sequence_counter = bitstream.getBits(7);
					marker_bit = bitstream.getBit();
					boolean MPEG1_MPEG2_identifier = bitstream.getBit();
					int original_stuff_length = bitstream.getBits(6);
				}
				if (P_STD_buffer_flag)
				{
					int _01 = bitstream.getBits(2);
					boolean P_STD_buffer_scale = bitstream.getBit();
					int P_STD_buffer_size = bitstream.getBits(13);
				}
				if (PES_extension_flag_2)
				{
					boolean marker_bit = bitstream.getBit();
					int PES_extension_field_length = bitstream.getBits(7);
					for (int i = 0; i < PES_extension_field_length; i++)
					{
						int reserved1 = bitstream.getBits(8);
					}
				}
			}

			int left = PES_packet_length - bitstream.getPosition() / 8;
			if (stream_id != 192)
			{
				while (bitstream.showBits(8) == 0xff && left-- > 0)
				{
					// stuffing_byte
					bitstream.skipBits(8);
				}
			}
			sample = new byte[left];
			for (int i = 0; i < left;)
			{
				int PES_packet_data_byte = bitstream.getBits(8);
				sample[i++] = (byte) PES_packet_data_byte;
			}
		}
		else if(stream_id == program_stream_map
						|| stream_id == private_stream_2
						|| stream_id == ECM
						|| stream_id == EMM
						|| stream_id == program_stream_directory
						|| stream_id == DSMCC_stream
						|| stream_id == h2221_typeE)
		{
			for (int i = 0; i < PES_packet_length; i++)
			{
				int PES_packet_data_byte = bitstream.getBits(8);
			}
		}
		else if ( stream_id == padding_stream)
		{
			for (int i = 0; i < PES_packet_length; i++)
			{
				int padding_byte = bitstream.getBits(8);
			}
		}
	}
	
	public void pack_header()
	{
		/*pack_start_code 32 bslbf
		'01' 2 bslbf
		system_clock_reference_base [32..30] 3 bslbf
		marker_bit 1 bslbf
		system_clock_reference_base [29..15] 15 bslbf
		marker_bit 1 bslbf
		system_clock_reference_base [14..0] 15 bslbf
		marker_bit 1 bslbf
		system_clock_reference_extension 9 uimsbf
		marker_bit 1 bslbf
		program_mux_rate 22 uimsbf
		marker_bit 1 bslbf
		marker_bit 1 bslbf
		reserved 5 bslbf
		pack_stuffing_length 3 uimsbf
		for (i = 0; i < pack_stuffing_length; i++) {
		stuffing_byte 8 bslbf
		}
		if (nextbits() = = system_header_start_code) {
		system_header ()
		}*/
		}


}

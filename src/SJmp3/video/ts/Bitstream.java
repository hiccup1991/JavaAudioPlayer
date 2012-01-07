package SJmp3.video.ts;

public class Bitstream
{
	private byte[] bitstream;
	private int offset;
	private int start;
	private int length;
	private int bufa;
	private int bufb;
	private int position;
	
	
	public void setData(byte[] bitstream, int dataOffset, int bitstreamLength)
	  {
	    this.bitstream = bitstream;

	    start = offset = dataOffset;
	    length = bitstreamLength;

	    try
	    {
	      bufa  = (bitstream[offset + 0] & 0xff) << 24;
	      bufa |= (bitstream[offset + 1] & 0xff) << 16;
	      bufa |= (bitstream[offset + 2] & 0xff) <<  8;
	      bufa |= (bitstream[offset + 3] & 0xff);

	      bufb  = (bitstream[offset + 4] & 0xff) << 24;
	      bufb |= (bitstream[offset + 5] & 0xff) << 16;
	      bufb |= (bitstream[offset + 6] & 0xff) <<  8;
	      bufb |= (bitstream[offset + 7] & 0xff);
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
	      // bufa and bufb have been filled as much as they can be...
	    }

	    offset += 8;

	    position = 0;
	  }

	  public int getPosition()
	  {
	    return (8 * (offset - 8 - start) + position);
	  }

	  public int getLength()
	  {
	    return length;
	  }

	  public void byteAlign()
	  {
	    int remainder = position % 8;

	    if (remainder != 0)
	    {
	      skipBits(8 - remainder);
	    }
	  }

	  public void skipBits(final int bits)
	  {
	    position += bits;

	    if (position >= 32)
	    {
	      bufa = bufb;
	      
	      if (offset + 3 < start + length)
	      {
	        bufb =  (bitstream[offset + 0] & 0xff) << 24 |
	                (bitstream[offset + 1] & 0xff) << 16 |
	                (bitstream[offset + 2] & 0xff) <<  8 |
	                (bitstream[offset + 3] & 0xff);
	      }
	      else 
	      {
	        bufb = 0;
	        
	        if (offset < start + length)
	        {
	          for (int i = 0; i < 4; i++)
	          {
	            bufb <<= 8;
	            
	            if (offset + i < start + length)
	            {
	              bufb |= bitstream[offset + i] & 0xff;
	            }
	          }
	        }
	      }
	      
	      offset += 4;
	      position -= 32;
	    }
	  }

	  public int showBits(final int bits)
	  {
	    int nbit = (bits + position) - 32;

	    if (nbit > 0)
	    {
	      return ((bufa & (0xffffffff >>> position)) << nbit) | (bufb >>> (32 - nbit));
	    }

	    return (bufa & (0xffffffff >>> position)) >>> (32 - position - bits);
	  }

	  public int getBits(final int n) {
	    int ret = showBits(n);

	    skipBits(n);

	    return ret;
	  }
	  
	  public byte[] getData()
	  {
		  return this.bitstream;
	  }

	  public boolean getBit()
	  {
	    return getBits(1) != 0;
	  }

	  // number of bits to next byte alignment

	  public int numBitsToByteAlign()
	  {
	    int n = (32 - position) % 8;
	    return n == 0 ? 8 : n;
	  }

	  // show nbits from next byte alignment

	  private int showBitsFromByteAlign(int bits)
	  {
	    int bspos = position + numBitsToByteAlign();
	    int nbit = (bits + bspos) - 32;

	    if (bspos >= 32)
	    {
	      return bufb >>> (32 - nbit);
	    }
	    else if (nbit > 0)
	    {
	      return ((bufa & (0xffffffff >>> bspos)) << nbit) | (bufb >>> (32 - nbit));
	    }
	    else
	    {
	      return (bufa & (0xffffffff >>> bspos)) >>> (32 - bspos - bits);
	    }
	  }


}

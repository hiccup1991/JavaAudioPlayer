package SJmp3.video.ts;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ProgramElementaryStream extends Thread
{
	public int stream_type;
	
	public int elementary_PID;
	
	private byte es_info[];
	
	public int state;
	
	private boolean release = false;
	
	private PipedInputStream pipe_in;
	
	private PipedOutputStream pipe_out;
	
	private Bitstream bitstream;
	
	private ArrayBlockingQueue<byte[]> sampleQueue;
	
	public ArrayBlockingQueue<byte[]> getSampleQueue()
	{
		synchronized(sampleQueue)
		{
			return sampleQueue;
		}
	}
	
	public ProgramElementaryStream(int stream_type, int elementary_PID, int queue_cap, int ps)
	{
		this.stream_type = stream_type;
		this.elementary_PID = elementary_PID;
		
		sampleQueue = new ArrayBlockingQueue<byte[]>(queue_cap);
		
		pipe_out = new PipedOutputStream();
		try
		{
			pipe_in = new  PipedInputStream(pipe_out, ps);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		bitstream = new Bitstream();
	}
	
	public synchronized void write(byte b[]) throws IOException
	{
		pipe_out.write(b);
	}
	
	public synchronized void write(int b) throws IOException
	{
		pipe_out.write(b);
	}
	
	public synchronized void release()
	{
		release = true;
		try
		{
			pipe_in.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			pipe_out.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			while (!release)
			{
				int next = 0;
				int b = 0;
				while ((b = pipe_in.read()) != -1)
				{
					next <<= 8;
					next |= (0xff & b);
					if ((next & 0xffffff) == 1)
					{
						int stream_id = pipe_in.read();

						if (stream_id >= 192 && stream_id < 240) // 192(0xC0),
																	// 224(0xE0)
						{
							int stream_number = stream_id & 0xf;
							int len = pipe_in.read();
							len <<= 8;
							len |= pipe_in.read();

							byte data[] = new byte[len];
							for (int i = 0; i < len; i++)
							{
								data[i] = (byte) pipe_in.read();
							}
							bitstream.setData(data, 0, data.length);
							PesPacket packet = new PesPacket(stream_id, stream_number, len);
							packet.read(bitstream);

							sampleQueue.offer(packet.sample, 1 << 10, TimeUnit.MILLISECONDS);
						} else if (stream_id == 250)
						{
							int stream_number = stream_id & 0xf;
							int len = pipe_in.read();
							len <<= 8;
							len |= pipe_in.read();

							byte data[] = new byte[len];
							for (int i = 0; i < len; i++)
							{
								data[i] = (byte) pipe_in.read();
							}

							bitstream.setData(data, 0, data.length);
							PesPacket packet = new PesPacket(stream_id, stream_number, len);
							packet.read(bitstream);

							int x = packet.sample[0] & 0xff;
							x <<= 8;
							x |= packet.sample[1] & 0xff;
							if (x > 2)
								sampleQueue.offer(packet.sample, 1 << 10, TimeUnit.MILLISECONDS);
						} else
						{
							int stream_number = stream_id & 0xf;
							int len = pipe_in.read();
							len <<= 8;
							len |= pipe_in.read();
							for (int i = 0; i < len; i++)
							{
								pipe_in.read();
							}
						}
					}
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
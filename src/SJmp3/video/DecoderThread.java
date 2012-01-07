package SJmp3.video;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.xvid.XviDDecoder;
import org.xvid.YV12Image;

public class DecoderThread extends Thread
{
	private XviDDecoder xvidDecoder;
	private BlockingQueue<YV12Image> decodedVideoSampleQueue;
	private BlockingQueue<byte[]> videoSampleQueue;
	
	private int width;
	private int height;
	
	private boolean release_flag = false;
	
	public DecoderThread(BlockingQueue<byte[]> videoSampleQueue, 
			BlockingQueue<YV12Image> decodedVideoSampleQueue, int width, int height)
	{
		this.width = width;
		this.height = height;
		
		this.xvidDecoder = new XviDDecoder(width, height);
		this.decodedVideoSampleQueue = decodedVideoSampleQueue;
		this.videoSampleQueue = videoSampleQueue;
	}
	
	public void stopDecoder()
	{
		release_flag = true;
		xvidDecoder.decode(null, 0, -1, new YV12Image(0, 0));
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void run()
	{
		try
		{
			int image_type = 0;
			int num_of_samples = 0;
			int decoded_frames_cnt = 0;
			while(!release_flag)
			{
				byte sample[] = videoSampleQueue.take();
				
				num_of_samples++;
				
				int used_bytes = 0;
				while (used_bytes < sample.length-5)
				{
					YV12Image image = new YV12Image(width, height);
					if (image_type < 4)
					{
						used_bytes += xvidDecoder.decode(sample, used_bytes , sample.length-used_bytes, image);
					} else
					{
						used_bytes += xvidDecoder.decode(null, 0, -1, image);
					}
					decoded_frames_cnt++;
					image_type = image.getFrameType();
					
					decodedVideoSampleQueue.offer(image, 1<<25, TimeUnit.MILLISECONDS);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void readHeader() throws InterruptedException
	{
		int wait = 0;
		while (wait < 10000)
		{
			if (videoSampleQueue.isEmpty())
			{
				wait += 100;
				Thread.sleep(100);
				continue;
			}
			byte[] sample = videoSampleQueue.peek();
			int sc = 0;
			sc = sample[0] & 0xff;	sc <<= 8;
			sc |= sample[1] & 0xff;	sc <<= 8;
			sc |= sample[2] & 0xff;	sc <<= 8;
			sc |= sample[3] & 0xff;
			if (sc == 0x120)
			{
				xvidDecoder.decode(sample, 0, sample.length, null);

				width = xvidDecoder.getWidth();
				height = xvidDecoder.getHeight();
				xvidDecoder.decode(null, 0, -1, new YV12Image(0, 0));
				return;
			} else
				videoSampleQueue.take();
		}
	}
}
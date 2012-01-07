package SJmp3.video;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.MemoryImageSource;
import java.util.concurrent.BlockingQueue;
import javax.swing.JPanel;
import org.xvid.XviDImage;
import org.xvid.YV12Image;

public class Screen extends JPanel implements Runnable
{
	private static final long serialVersionUID = 7369199688464090008L;

	private Image image;
	
	private int width;
	
	private int height;
	
	private double samplerate;
	
	private int rgb[];
	
	private MemoryImageSource source;
	
	private BlockingQueue<YV12Image> decodedVideoSampleQueue;
	
	private BlockingQueue<byte[]> subtiles;
	
	private Thread screenThread;
	
	public Screen(int width, int height, double samplerate, BlockingQueue<YV12Image> decodedVideoSampleQueue, BlockingQueue<byte[]> subtiles)
	{
		this.width = width;
		this.height = height;
		this.samplerate = samplerate;
		this.decodedVideoSampleQueue = decodedVideoSampleQueue;
		this.subtiles = subtiles;
		
		setPreferredSize(new Dimension(width, height));
		
		rgb = new int[width*height];
		source = new MemoryImageSource(width, height, rgb, 0, width);
		source.setAnimated(true);
		image = createImage(source);
		
		screenThread = new Thread(this);
		screenThread.start();
		
		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				if (ComponentEvent.COMPONENT_RESIZED == e.getID())
				{
					Component c = (Component)e.getSource();
		            Dimension new_size = c.getSize();
		            double w = new_size.getWidth()/Screen.this.width;
		            double h = new_size.getHeight()/Screen.this.height;
		            sx = sy = w < h ? w : h;
				}
			}
		});
	}
	
	private int[] getRGB()
	{
		return rgb;
	}
	
	private double sx = 1;
	private double sy  = 1;
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.black);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		
		g2.scale(sx, sy);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		int x = (int)((getWidth()/sx-width)/2.0);
		int y = (int)((getHeight()/sy-height)/2.0);
		g2.drawImage(image, x, y, width, height, this);
		
		showSubtitiles(g2, x, y);
	}
	
	public void update()
	{
		source.newPixels(0,0, width, height);
	}
	
	private int duration = 50;
	private String subs = "";
	private int st = 0;
	
	private void showSubtitiles(Graphics2D g2, int x, int y)
	{
		g2.setFont(g2.getFont().deriveFont(14f));
		if (st++ < duration)
		{
		    FontMetrics metrics = g2.getFontMetrics();
		    int adv = metrics.stringWidth(subs);
			
			g2.setColor(Color.white);
			g2.drawString(subs, x + (width-adv+2)/2, y + height*4/5);
		} else if (subtiles != null && !subtiles.isEmpty())
		{
			try
			{
				byte sub[] = subtiles.take();
				st = 0;
				int len = sub[0] & 0xff;
				len <<= 8;
				len |= sub[1] & 0xff;
				subs = new String(sub, 2, sub.length-2);
				duration = len / 20 *30;
				if (duration < 45) duration =45;
				//subs = duration +", " + subs;
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private long current_time;
	
	private int time_increment_resolution;
	
	private int fixed_time_increment;
	
	public int getCurrentTime()
	{
		if (time_increment_resolution == 0) return 0;
		return (int)current_time/time_increment_resolution;
	}
	
	public double getFPS()
	{
		double fps = time_increment_resolution/(double)fixed_time_increment;
		return Math.round(fps*100)/100.0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		try
		{
			long prev = 0;
			int current_time_base = 0;
			long time = System.nanoTime();
			while (!stop)
			{
				synchronized (this)
				{
					if (pause) wait();
				}
				
				YV12Image image = decodedVideoSampleQueue.take();
				if (image.getTimeIncrementResolution() == 0) continue;
							
				long left = System.nanoTime()-time;
				current_time = image.getTime();
				
				time_increment_resolution = image.getTimeIncrementResolution();
				fixed_time_increment = image.getFixedTimeIncrement();
				
				long n = 0;
				if (fixed_time_increment == 0)
					n = (long)((1000/samplerate)*1000000);
				else
					n = (long)(((current_time-prev)/(double)time_increment_resolution)*1000000000);
				n -= left;
				n = n/fast_forward;
				long millis = n/1000000;
				long nanos = n%1000000;
				
				/*System.out.println("time: " + image.getTime() + ", inc: " + image.getTimeIncrement() 
						+ ", resolution: " + image.getTimeIncrementResolution() 
						+ ", fixed resolution: " + image.getFixedTimeIncrement() 
						+ ", type: " + image.getFrameType() + ", wait: " + millis);*/
				
				millis = millis > 0 ? millis : 0;
				nanos = nanos > 0 ? nanos : 0;
				
				Thread.sleep(millis, (int)nanos);
				time = System.nanoTime();
				
				XviDImage.convertColourspace(image, image.getWidth(), image.getHeight(), getRGB());
				update();
				
				prev = image.getTime();
				
				/*if (image.getTimeBase() != current_time_base)
				{
					System.out.println("time base: " + image.getTimeBase() + ", fps: " 
							+ time_increment_resolution/(double)image.getFixedTimeIncrement());
					current_time_base = image.getTimeBase();
				}*/
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean pause = false;
	
	private boolean stop = false;
	
	private int fast_forward = 1;
	
	public synchronized void pause()
	{
		this.pause = true;
	}
	
	public synchronized void resume()
	{
		this.pause = false;
		notify();
	}
	
	public boolean isPaused()
	{
		return pause;
	}
	
	public void stop() throws InterruptedException
	{
		if(pause) resume();
		
		this.stop = true;
		if (decodedVideoSampleQueue.isEmpty())
			decodedVideoSampleQueue.offer(new YV12Image(0, 0));
		screenThread.join();
		decodedVideoSampleQueue.clear();
	}
	
	public boolean isScreenThreadAlive()
	{
		return screenThread.isAlive();
	}
	
	public int fastForward()
	{
		return fast_forward = fast_forward == 8 ? 1 : fast_forward*2;
	}
}
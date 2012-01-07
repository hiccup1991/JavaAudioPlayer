package SJmp3.video.stream;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.swing.JOptionPane;
import SJmp3.video.DecoderThread;
import SJmp3.video.ts.mpegts;

import org.xvid.YV12Image;

public class ts_player
{
	private DecoderThread decoderThread;
	
	private BlockingQueue<byte[]> videoSampleQueue;
	
	private BlockingQueue<byte[]> subtitles;
	
	private BlockingQueue<YV12Image> decodedVideoSampleQueue;
	
	private TSView view;
	
	private String url;
	
	public ts_player(String url) throws Exception
	{
		this.url = url;
		play();
	}
	
	public void play() throws Exception
	{
		if(start_ts())
		{
			decodedVideoSampleQueue = new ArrayBlockingQueue<YV12Image>(24);
			
			decoderThread = new DecoderThread(videoSampleQueue, decodedVideoSampleQueue, 0, 0);
			decoderThread.readHeader();
			int width = decoderThread.getWidth();
			int height = decoderThread.getHeight();
			double samplerate = 0;
			decoderThread.start();
			
			view = new TSView();
			view.setTitle("jv_player - " + url);
			view.setSize(new Dimension(400, view.getSize().height));
			view.showScreen(decodedVideoSampleQueue, subtitles, width, height, samplerate);
			view.setVisible(true);
			view.addWindowListener(new WindowAdapter()
			{
				@Override
				public void windowClosing(WindowEvent e)
				{
					close();
				}
			});
		}
	}
	
	public void close()
	{
		new Thread()
		{
			public void run()
			{
				view.dispose();
				decoderThread.stopDecoder();

				try
				{
					if (decodedVideoSampleQueue.remainingCapacity() == 0)
						decodedVideoSampleQueue.take();
					if (videoSampleQueue.isEmpty())
						videoSampleQueue.offer(new byte[5]);
					decoderThread.join();

				} catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				audioThread.release();
				ts.close();
			}
		}.start();
		
	}
	
	private mpegts ts;
	
	private AudioThread audioThread;
	
	private boolean start_ts()
	{
		try
		{
			ts = new mpegts(url, this);
			ts.start();
			Thread.sleep(2000);
			
			if (ts.isRunning())
			{
				videoSampleQueue = ts.getVideoSampleQueue();
				
				subtitles = ts.getSubtitlesSampleQueue();
				
				BlockingQueue<byte[]> audioSampleQueue = ts.getAudioSampleQueue();
				
				audioThread = new AudioThread(audioSampleQueue);
				return true;
			} else
			{
				JOptionPane.showMessageDialog(view, "socket closed", "error", JOptionPane.ERROR_MESSAGE);
				ts.close();
			}
			
			
		} catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
		}
		catch (InterruptedException e){	e.printStackTrace();}
		return false;
	}
	
	public static void main(String args[]) throws Exception
	{
		new ts_player("http://localhost:1234");
	}
}
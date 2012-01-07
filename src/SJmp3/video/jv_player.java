package SJmp3.video;

import java.awt.Dimension;
import java.io.File;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import org.xvid.YV12Image;

public class jv_player
{
	private ParserWrapper wrapper;
	
	private DecoderThread decoderThread;
	
	private BlockingQueue<byte[]> videoSampleQueue;
	
	private BlockingQueue<YV12Image> decodedVideoSampleQueue;
	
	private AudioPlayer audio_player;
	
	private AudioDevice audio_device;
	
	private PipedInputStream audio_stream;
	
	private File file;
	
	private View view;
	
	private int mode = 0;
	
	public jv_player(File file) throws Exception
	{
		this.file = file;
		view = new View(this);
		view.setSize(new Dimension(400, view.getSize().height));
		view.setVisible(true);
		play();
	}
	
	public void play() throws Exception
	{
		if (isRunning()) stop();
		
		if (file != null)
		{
			wrapper = ParserWrapper.newParserWrapper(file);
			
			if (!"mp4v".equals(wrapper.getVideoCodec()) 
					&& !"xvid".equalsIgnoreCase(wrapper.getVideoCodec())
					&& !"divx".equalsIgnoreCase(wrapper.getVideoCodec())
					&& !"dx50".equalsIgnoreCase(wrapper.getVideoCodec())
                                        //&& !"avc1".equalsIgnoreCase(wrapper.getVideoCodec()) 
                                        //&& !"div4".equalsIgnoreCase(wrapper.getVideoCodec())                                
					&& !"yv12".equalsIgnoreCase(wrapper.getVideoCodec()))
			{
				System.out.println(wrapper.getVideoCodec());
                                wrapper.dispose();
				JOptionPane.showMessageDialog(view, "Could not play this file, codec must be mp4v/xvid."
						, "error", JOptionPane.ERROR_MESSAGE);
				return;
			}
				
			videoSampleQueue = new ArrayBlockingQueue<byte[]>(30);
			
			if ("mp3".equals(wrapper.getAudioCodec()) || "mp4a".equals(wrapper.getAudioCodec()) || "A_MPEG/L3".equals(wrapper.getAudioCodec()))
			{
				audio_stream = new PipedInputStream();
				wrapper.startVisualSampleReader(videoSampleQueue, audio_stream);
				runAudioPlayer(audio_stream);
			} else
			{
				wrapper.startVisualSampleReader(videoSampleQueue);
			}
			
			decodedVideoSampleQueue = new ArrayBlockingQueue<YV12Image>(5);
			
			int width = (int)wrapper.getVideoResolutionWidth();
			int height = (int)wrapper.getVideoResolutionHeight();
			
			double samplerate = wrapper.getVideoSamplerate();
			
			decoderThread = new DecoderThread(videoSampleQueue, decodedVideoSampleQueue, width, height);
			decoderThread.start();
			
			view.showScreen(decodedVideoSampleQueue, width, height, samplerate);
			mode = PlayerActionListener.PLAY;
		}
	}
	
	public void stop()
	{
		if (!isRunning()) return;
		
		try
		{
			view.getScreen().stop();
			
			decoderThread.stopDecoder();
			if (decodedVideoSampleQueue.remainingCapacity() == 0)
				decodedVideoSampleQueue.take();
			if (videoSampleQueue.isEmpty())
				videoSampleQueue.offer(new byte[0]);
			decoderThread.join();
			
			view.getPlayer().audioResume();
			
			wrapper.stopVisualSampleReader();
			
			if (audio_player != null)
				audio_player.close();
			
			// update view
			view.updateOnStop();
			
			mode = PlayerActionListener.STOP;
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private void runAudioPlayer(final InputStream in)
	{
		// should run in new thread or samples reading thread will block if 
		// queue capacity is less then the number of samples in the first chunk
		new Thread()
		{
			public void run()
			{
				try
				{
					audio_device = FactoryRegistry.systemRegistry().createAudioDevice();
					audio_player = new AudioPlayer(in, audio_device);
					audio_player.play();
				} catch (JavaLayerException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void audioPause()
	{
		if (audio_player != null)
			audio_player.pause();
	}
	
	public void audioResume()
	{
		if (audio_player != null)
		{
			decodedVideoSampleQueue.clear();
			audio_player.play();
		}
	}
	
	public int getMode()
	{
		return mode;
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
	}
	
	public void setFile(File file)
	{
		this.file = file;
	}
	
	public boolean isRunning()
	{
		return view.isRunning();
	}
	
	public void skipAudio(boolean skip)
	{
		if (audio_player != null)
			audio_player.skip(skip);
	}
	
	public ParserWrapper getParserWrapper()
	{
		return wrapper;
	}
	
	public File getFile()
	{
		return file;
	}
	
	public static void main(String args[]) throws Exception
	{
		new jv_player(null);
	}
}
	
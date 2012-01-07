package SJmp3.video.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.BlockingQueue;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import SJmp3.video.AudioPlayer;

public class AudioThread extends Thread
{
	private BlockingQueue<byte[]> audioSampleQueue;
	
	private AudioPlayer audio_player;
	
	private AudioDevice audio_device;
	
	private PipedInputStream audio_stream;
	
	private PipedOutputStream audio_out;
	
	public AudioThread(BlockingQueue<byte[]> audioSampleQueue) throws IOException
	{
		this.audioSampleQueue = audioSampleQueue;
		
		audio_out = new PipedOutputStream();
		audio_stream = new PipedInputStream(audio_out);
		
		runAudioPlayer(audio_stream);
		
		start();
	}
	
	private boolean release = false;
	
	public synchronized void release()
	{
		release = true;
		
		try
		{
			audio_player.close();
			audio_player.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
	public void run()
	{
		while (!release)
		{
			try
			{
				synchronized (audio_stream)
				{
					byte audio[] = audioSampleQueue.take();
					audio_out.write(audio);
					audio_out.flush();
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void runAudioPlayer(final InputStream in)
	{
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
}
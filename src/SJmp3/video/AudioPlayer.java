package SJmp3.video;

import java.io.InputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

public class AudioPlayer extends Thread
{
	private Bitstream bitstream;
	private Decoder decoder;
	private AudioDevice audio;
	private boolean stop = false;
	public boolean pause = true;
	private boolean skip = false;

	private SampleBuffer output;
	
	public AudioPlayer(InputStream stream) throws JavaLayerException
	{
		this(stream, null);
	}

	public AudioPlayer(InputStream stream, AudioDevice device) throws JavaLayerException
	{
		bitstream = new Bitstream(stream);

		if (device!=null) audio = device;
		else audio = FactoryRegistry.systemRegistry().createAudioDevice();
		audio.open(decoder = new Decoder());
		
		start();
	}
	
	public void run()
	{
		boolean ret = true;
		
		try
		{		
			while(!stop || ret)
			{
				if (pause)
				{
					synchronized (audio)
					{
						audio.wait();
					}
				}
				if (!skip)
					ret = decodeFrame();
				else
					skipFrame();
			}
		} catch (JavaLayerException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		if (audio != null)
		{
			audio.flush();
			synchronized (this)
			{
				close();
			}
		}
	}

	public synchronized void play()
	{
		pause = false;
		synchronized (audio)
		{
			audio.notify();
		}
	}
	
	public void close()
	{
		stop = true;
		audio.close();
		try
		{
			bitstream.close();
		}
		catch (BitstreamException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private boolean decodeFrame() throws JavaLayerException
	{
		try
		{
			if (audio == null) return false;

			Header h = bitstream.readFrame();
			if (h == null) return false;
			
			output = (SampleBuffer) decoder.decodeFrame(h, bitstream);
			audio.write(output.getBuffer(), 0, output.getBufferLength());

			bitstream.closeFrame();
		}
		catch (RuntimeException ex)
		{
			throw new JavaLayerException("Exception decoding audio frame", ex);
		}
		return true;
	}
	
	private boolean skipFrame() throws JavaLayerException
	{
		Header h = bitstream.readFrame();
		if (h == null) return false;
		bitstream.closeFrame();
		return true;
	}
	
	public void clear_buffer()
	{
		output.clear_buffer();
	}
	
	public void skip(boolean skip)
	{
		this.skip = skip;
	}
	
	public synchronized void pause()
	{
		pause = true;
	}
}

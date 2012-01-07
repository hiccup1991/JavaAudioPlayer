package SJmp3.video;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.IntBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import net.sourceforge.parser.mp4.box.AudioSampleEntry;
import net.sourceforge.parser.mp4.box.SyncSampleBox;
import net.sourceforge.parser.mp4.box.VisualSampleEntry;
import net.sourceforge.parser.mp4.box.SampleToChunkBox.SampleToChunkTableEntry;
import net.sourceforge.parser.util.TreeNode;
import SJmp3.video.Mp4ParserWrapper.SyncPoint;

public class Mp4SampleReader extends Thread
{
	private boolean release_flag = false;
	private BlockingQueue<byte[]> videoSampleQueue;
	private Mp4ParserWrapper wrapper;
	private PipedInputStream audio_stream;
	private PipedOutputStream audio_out;
	
	private TreeNode currentVideoTrack;
	private TreeNode currentAudioTrack;
	
	public Mp4SampleReader(Mp4ParserWrapper wrapper, BlockingQueue<byte[]> videoSampleQueue) throws IOException
	{
		this(wrapper, videoSampleQueue, null);
	}
	
	public Mp4SampleReader(Mp4ParserWrapper wrapper, BlockingQueue<byte[]> videoSampleQueue, InputStream audio_stream) throws IOException
	{
		this.wrapper = wrapper;
		this.videoSampleQueue = videoSampleQueue;
		
		currentVideoTrack = wrapper.getVideoTrack(0);
		currentAudioTrack = wrapper.getAudioTrack(0);
		
		if (audio_stream != null)
		{
			this.audio_out = new PipedOutputStream((PipedInputStream)audio_stream);
			this.audio_stream = (PipedInputStream)audio_stream;
		}
		start();
	}
	
	public TreeNode getAudioTrack()
	{
		return currentAudioTrack;
	}
	
	public TreeNode getVideoTrack()
	{
		return currentVideoTrack;
	}
	
	public void run()
	{
		while(!release_flag)
		{
			try
			{
				if (audio_stream != null)
					readSamples();
				else
					readVideoSamples();
				
				release_flag = true;
			} catch (Exception e)
			{
				e.printStackTrace();
				release_flag = true;
			}
		}
	}
	
	public void release()
	{
		release_flag = true;
		try
		{
			if (videoSampleQueue.remainingCapacity() == 0)
				videoSampleQueue.take();
			videoSampleQueue.clear();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private SyncPoint videoSyncPoint = null;
	private SyncPoint audioSyncPoint = null;
	
	public synchronized void setSyncPoint(SyncPoint videoSyncPoint, SyncPoint audio_sync_point)
	{
		this.videoSyncPoint = videoSyncPoint;
		this.audioSyncPoint = audio_sync_point;
	}
	
	public void readVideoSamples() throws Exception
	{
		TreeNode video_trak = currentVideoTrack;
		SampleToChunkTableEntry video_sample_to_chunk[] = wrapper.getSampleToChunkTable(video_trak);
		IntBuffer video_sample_size = wrapper.getSampleSizeTable(video_trak);
		IntBuffer video_chunk_offset = wrapper.getChunkOffsetTable(video_trak);
		
		int video_sample_index = 0;
		int num_of_video_samples = 0;
		int video_sample_to_chunk_index = 0;
		int video_sample_description_index = 0;
		int video_chunk_index = 0;
		
		for (int i = 0; i < video_chunk_offset.capacity(); i++)
		{
			if (release_flag) return;
			
			if (videoSyncPoint != null)
			{
				video_chunk_index = videoSyncPoint.chunk_index;
				video_sample_to_chunk_index = videoSyncPoint.sample_to_chunk_index;
				video_sample_index = videoSyncPoint.sample_index - videoSyncPoint.sample_index_in_chunk;
				videoSyncPoint = null;
			}
			
			if (video_sample_to_chunk_index < video_sample_to_chunk.length)
			{
				if (video_chunk_index + 1 >= video_sample_to_chunk[video_sample_to_chunk_index].first_chunk)
				{
					num_of_video_samples = video_sample_to_chunk[video_sample_to_chunk_index].samples_per_chunk;
					video_sample_description_index = video_sample_to_chunk[video_sample_to_chunk_index++].sample_description_index;
				}
			}
			
			wrapper.getParser().getByteStream().position(video_chunk_offset.get(video_chunk_index));
			for (int j = 0; j < num_of_video_samples; j++)
			{
				byte sample_data[] = new byte[video_sample_size.get(video_sample_index)];
				wrapper.getParser().getByteStream().read(sample_data);

				VisualSampleEntry vse = (VisualSampleEntry)wrapper.getSampleEntry(video_trak, video_sample_description_index-1);
				//VideoSample sample = new VideoSample(sample_data, vse.decoder_config, video_sample_description_index);
				
				byte sample[] = null;
				if (i == 0)
				{
					sample = new byte[vse.decoder_config.length + sample_data.length];
					System.arraycopy(vse.decoder_config, 0, sample, 0, vse.decoder_config.length);
					System.arraycopy(sample_data, 0, sample, vse.decoder_config.length, sample_data.length);
					
				} else
					sample = sample_data;
				
				if(!release_flag && !videoSampleQueue.offer(sample, 1<<25, TimeUnit.MILLISECONDS))
				{
					throw new Exception("video sample queue timeout");
				}
				video_sample_index++;
			}
			video_chunk_index++;
		}
	}
	
	public void readSamples() throws Exception
	{
		TreeNode video_trak = currentVideoTrack;
		SampleToChunkTableEntry video_sample_to_chunk[] = wrapper.getSampleToChunkTable(video_trak);
		IntBuffer video_sample_size = wrapper.getSampleSizeTable(video_trak);
		IntBuffer video_chunk_offset = wrapper.getChunkOffsetTable(video_trak);
		
		int video_sample_index = 0;
		int num_of_video_samples = 0;
		int video_sample_to_chunk_index = 0;
		int video_sample_description_index = 0;
		int video_chunk_index = 0;
		
		TreeNode audio_trak = currentAudioTrack;
		SampleToChunkTableEntry audio_sample_to_chunk[] = wrapper.getSampleToChunkTable(audio_trak);
		IntBuffer audio_sample_size = wrapper.getSampleSizeTable(audio_trak);
		IntBuffer audio_chunk_offset = wrapper.getChunkOffsetTable(audio_trak);
		
		int audio_sample_index = 0;
		int num_of_audio_samples = 0;
		int audio_sample_to_chunk_index = 0;
		int audio_sample_description_index = 0;
		int audio_chunk_index = 0;
		
		int skip_samples = 0;
		
		while (video_chunk_index < video_chunk_offset.capacity() && audio_chunk_index < audio_chunk_offset.capacity())
		{
			if (release_flag) return;
			
			if (videoSyncPoint != null)
			{
				video_chunk_index = videoSyncPoint.chunk_index;
				video_sample_to_chunk_index = videoSyncPoint.sample_to_chunk_index;
				video_sample_index = videoSyncPoint.sample_index - videoSyncPoint.sample_index_in_chunk;
				skip_samples = videoSyncPoint.sample_index_in_chunk-1;
				
				audio_chunk_index = audioSyncPoint.chunk_index;
				audio_sample_index = audioSyncPoint.first_sample_index;
				audio_sample_to_chunk_index = audioSyncPoint.sample_to_chunk_index;
				
				videoSyncPoint = null;
				audioSyncPoint = null;
			}
			
			if (video_chunk_offset.get(video_chunk_index) < audio_chunk_offset.get(audio_chunk_index))
			{
				if (video_sample_to_chunk_index < video_sample_to_chunk.length)
				{
					if (video_chunk_index + 1 >= video_sample_to_chunk[video_sample_to_chunk_index].first_chunk)
					{
						num_of_video_samples = video_sample_to_chunk[video_sample_to_chunk_index].samples_per_chunk;
						video_sample_description_index = video_sample_to_chunk[video_sample_to_chunk_index++].sample_description_index;
					}
				}
				
				wrapper.getParser().getByteStream().position(video_chunk_offset.get(video_chunk_index));
				for (int j = 0; j < num_of_video_samples; j++)
				{
					byte sample_data[] = new byte[video_sample_size.get(video_sample_index++)];
					wrapper.getParser().getByteStream().read(sample_data);

					if (skip_samples-- > 0) continue;
					
					VisualSampleEntry vse = (VisualSampleEntry)wrapper.getSampleEntry(video_trak, video_sample_description_index-1);
					//VideoSample sample = new VideoSample(sample_data, vse.decoder_config, video_sample_description_index);
					
					byte sample[] = null;
					if (video_sample_index == 1)
					{
						sample = new byte[vse.decoder_config.length + sample_data.length];
						System.arraycopy(vse.decoder_config, 0, sample, 0, vse.decoder_config.length);
						System.arraycopy(sample_data, 0, sample, vse.decoder_config.length, sample_data.length);
						
					} else
						sample = sample_data;
					
					if(!release_flag)
						videoSampleQueue.offer(sample_data, 1<<25, TimeUnit.MILLISECONDS);
				}
				video_chunk_index++;
			} else
			{
				if (audio_sample_to_chunk_index < audio_sample_to_chunk.length)
				{
					if (audio_chunk_index + 1 >= audio_sample_to_chunk[audio_sample_to_chunk_index].first_chunk)
					{
						num_of_audio_samples = audio_sample_to_chunk[audio_sample_to_chunk_index].samples_per_chunk;
						audio_sample_description_index = audio_sample_to_chunk[audio_sample_to_chunk_index++].sample_description_index;
					}
				}
					
				wrapper.getParser().getByteStream().position(audio_chunk_offset.get(audio_chunk_index));
				for (int j = 0; j < num_of_audio_samples; j++)
				{
					byte sample_data[] = new byte[audio_sample_size.get(audio_sample_index)];
					wrapper.getParser().getByteStream().read(sample_data);
					
					AudioSampleEntry ase = (AudioSampleEntry)wrapper.getSampleEntry(audio_trak, audio_sample_description_index-1);
					
					audio_out.write(sample_data);
					
					audio_sample_index++;
				}
				audio_chunk_index++;
			}
		}
	}
	
	private synchronized void test_setSyncPoint()
	{
		SyncSampleBox syncSampleBox = (SyncSampleBox)wrapper.getTrackTable(currentVideoTrack, "stss");
		
		TreeNode video_trak = currentVideoTrack;
		TreeNode audio_trak = currentAudioTrack;
		
		int sample_index = syncSampleBox.table[1300];
		SyncPoint vsp = wrapper.findSyncPoint_BySampleIndex(sample_index, video_trak);
		
		IntBuffer video_chunk_offset = wrapper.getChunkOffsetTable(video_trak);
		IntBuffer audio_chunk_offset = wrapper.getChunkOffsetTable(audio_trak);
		
		int video_chunk_index = vsp.chunk_index;
		
		int offset = video_chunk_offset.get(video_chunk_index);
		
		int audio_chunk_index = ~Mp4ParserWrapper.binarySearch(audio_chunk_offset, offset);
		SyncPoint asp = wrapper.findSyncPoint_ByChunkIndex(audio_chunk_index, audio_trak);
		
		this.videoSyncPoint = vsp;
		this.audioSyncPoint = asp;
	}
}
package SJmp3.Threads;

import SJmp3.Actions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.flac.FlacInfoReader;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.flac.FlacTag;
import org.kc7bfi.jflac.FLACDecoder;
import org.kc7bfi.jflac.metadata.StreamInfo;
import SJmp3.Threads.Utilities;

/**
 * This class gives information (audio format and comments) about Flac input or URL.
 * 
 * @author Besmir Beqiri
 */
public class FlacInfo extends TagInfo {

    protected StreamInfo info = null;
    protected int bitspersample = AudioSystem.NOT_SPECIFIED;

    /**
     * Load and parse Flac info from File.
     *
     * @param file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    @Override
    public void load(File file) throws IOException, UnsupportedAudioFileException {
        size = file.length();
        location = file.getPath();

        FileInputStream is = new FileInputStream(file);
        FLACDecoder decoder = new FLACDecoder(is);
        decoder.readMetadata();
        info = decoder.getStreamInfo();
        
        try {
            AudioFile flacFile = AudioFileIO.read(file);
            FlacTag flacTag = (FlacTag) flacFile.getTag();
            FlacInfoReader fir = new FlacInfoReader();
            GenericAudioHeader gah = fir.read(new RandomAccessFile(file, "r"));

            if (gah != null) {
                Actions.encoding = gah.getEncodingType();
                Actions.channels = Integer.parseInt(gah.getChannels());
                Actions.channels = gah.getChannelNumber();
                Actions.freq = gah.getSampleRateAsNumber();
                Actions.bitrate = (int) gah.getBitRateAsNumber();
                Actions.duration = gah.getTrackLength();
            }
            if (flacTag != null) {
                Actions.title = flacTag.getFirst(FieldKey.TITLE);
                Actions.author = flacTag.getFirst(FieldKey.ARTIST);
                Actions.album = flacTag.getFirst(FieldKey.ALBUM);
                Actions.date = flacTag.getFirst(FieldKey.YEAR);
                Actions.genre = flacTag.getFirst(FieldKey.GENRE);
                Actions.track = flacTag.getFirst(FieldKey.TRACK);
                comment = flacTag.getFirst(FieldKey.COMMENT);
            }
        } catch (CannotReadException | ReadOnlyFileException ex) {
            throw new IOException(ex);
        } catch (TagException | InvalidAudioFrameException ex) {
            throw new UnsupportedAudioFileException("Not Flac audio format");
        }
    }

    /**
     * Load and parse Flac info from an URL.
     *
     * @param input
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    @Override
    public void load(URL input) throws IOException, UnsupportedAudioFileException {
        location = input.toString();
        FLACDecoder decoder = new FLACDecoder(input.openStream());
        decoder.readMetadata();
        info = decoder.getStreamInfo();
        AudioFileFormat aff = AudioSystem.getAudioFileFormat(input);
        loadInfo(aff);
    }

    /**
     * Load and parse Flac info from an input stream.
     *
     * @param input
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    @Override
    public void load(InputStream input) throws IOException, UnsupportedAudioFileException {
        FLACDecoder decoder = new FLACDecoder(input);
        decoder.readMetadata();
        info = decoder.getStreamInfo();
        AudioFileFormat aff = AudioSystem.getAudioFileFormat(input);
        loadInfo(aff);
    }

    /**
     * Load Flac info from an AudioFileFormat.
     *
     * @param aff the audio file format
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     */
    protected void loadInfo(AudioFileFormat aff) throws UnsupportedAudioFileException {
        Actions.encoding = aff.getType().toString();
        if (!encodingType.equalsIgnoreCase("flac")) {
            throw new UnsupportedAudioFileException("Not Flac audio format");
        }
        Actions.channels = info.getChannels();
        Actions.freq = info.getSampleRate();
        Actions.sizeSampleBit = info.getBitsPerSample();
        Actions.duration = Math.round(info.getTotalSamples() / info.getSampleRate());
    }

    public long getSize() {
        return size;
    }

    public String getLocation() {
        return location;
    }

    public int getBitsPerSample() {
        return bitspersample;
    }

    @Override
    public String getCodecDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><b>Encoding Type: </b>");
        sb.append(getEncodingType().toUpperCase());
        sb.append("<br><b>Sampling rate: </b>");
        sb.append(getSampleRateAsNumber()).append(" Hz");
        sb.append("<br><b>Bitrate: </b>");
        sb.append(getBitRateAsNumber()).append(" Kbps");
        sb.append("<br><b>Channels: </b>");
        sb.append(getChannelsAsNumber());
        if (size > 0) {
            sb.append("<br><b>Size: </b>");
            sb.append(Utilities.byteCountToDisplaySize(size));
        }
        sb.append("</html>");
        return sb.toString();
    }
}

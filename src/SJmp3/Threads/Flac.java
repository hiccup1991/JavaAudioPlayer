package SJmp3.Threads;

import SJmp3.Actions;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.kc7bfi.jflac.FLACDecoder;
import org.kc7bfi.jflac.apps.Decoder;
import org.kc7bfi.jflac.apps.SeekTablePlayer;
import org.kc7bfi.jflac.io.BitInputStream;
import org.kc7bfi.jflac.metadata.SeekPoint;
import org.kc7bfi.jflac.metadata.SeekTable;
import org.kc7bfi.jflac.metadata.StreamInfo;
import org.kc7bfi.jflac.sound.spi.FlacAudioFileReader;
//import org.kc7bfi.jflac.sound.spi.FlacAudioFormat;

public class Flac {
    
    public AudioInputStream ais = null;
    public FileInputStream fis = null;
    public BufferedInputStream bis = null;
    public InputStream nis = null; 
    public AudioFileFormat aff=null;
    public File flacFile;
    public URL flacUrl;
    public FlacAudioFileReader fafr;
    public BitInputStream bitis;
    public SeekPoint seekPOINT;
    public SeekTable seekTABLE;
    public StreamInfo streamINFO;
    public SeekTablePlayer seekTablePLAYER;
    public FLACDecoder flacDECODER;
    //public FlacAudioFormat flacAF;
    
    public void playFlac ()  { 
        try {
            //int currentFrame=0;
            // from a wave File
            flacFile = new File("eatfood.flac");
            flacUrl = new URL("http://www.zzz.com/eatfood.flac");
            nis=flacUrl.openStream();
            fis = new FileInputStream("snd.flac");
            bis = new BufferedInputStream(fis);
            ////////////
            fafr=new FlacAudioFileReader();
            aff=fafr.getAudioFileFormat(flacFile);
            aff=fafr.getAudioFileFormat(bis);
            aff=fafr.getAudioFileFormat(flacUrl);
            ais=fafr.getAudioInputStream(flacFile);
            ais=fafr.getAudioInputStream(bis);
            ais=fafr.getAudioInputStream(flacUrl);
            //////////
            bitis=new BitInputStream(bis);
            bitis=flacDECODER.getBitInputStream();
            bitis.reset();
            //////////////////////
            try 
            {
               seekPOINT=new SeekPoint(bitis); 
               seekPOINT.getFrameSamples();
               seekPOINT.getSampleNumber();
               seekPOINT.getStreamOffset();
            } 
            catch (IOException ex) {
                Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
            }
            ///////////////////////
            try 
            {
               seekTABLE=new SeekTable(bitis, Actions.frames, true); 
               //seekTABLE=new SeekTable(seekPOINT[],true);
               seekTABLE.getSeekPoint(11);
               seekTABLE.calcLength();
               seekTABLE.numberOfPoints();
            } 
            catch (IOException ex) {
                Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
            }  
            ///////////////////////////
            streamINFO=new StreamInfo(bitis,Actions.frames,true);
            streamINFO=flacDECODER.getStreamInfo();
            streamINFO.addTotalSamples(Actions.currentFrame);
            streamINFO.calcLength();
            streamINFO.compatiable(streamINFO);
            streamINFO.getAudioFormat();
            streamINFO.getBitsPerSample();
            streamINFO.getChannels();
            streamINFO.getSampleRate();
            Actions.frames=(int)streamINFO.getTotalSamples();
            //////////////////
            seekTablePLAYER=new SeekTablePlayer();
            seekTablePLAYER.processStreamInfo(streamINFO);
            seekTablePLAYER.play("eatfood.flac", Actions.currentFrame, Actions.frames);
            seekTablePLAYER.processMetadata(seekTABLE);
            //seekTablePLAYER.processPCM(pcm);
            seekTablePLAYER.processStreamInfo(streamINFO);
            /////////////////////////////
            flacDECODER=new FLACDecoder(bis);
            flacDECODER.addFrameListener(seekTablePLAYER);
            flacDECODER.addPCMProcessor(seekTablePLAYER);
            flacDECODER.getStreamInfo();
            flacDECODER.getBitInputStream();
            flacDECODER.readMetadata();
            flacDECODER.readMetadata(streamINFO);
            flacDECODER.decode();
            flacDECODER.decode(seekPOINT, seekPOINT);
            //fd.
            /////////////////////
            //flacAF=new FlacAudioFormat(streamINFO);
            //flacAF.getProperty("frames");
            //flacAF.properties();
            /////////////////////////
            Decoder flac2wav=new Decoder();
            flac2wav.decode("in.flac", "out.wav");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Flac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Flac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Flac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Flac.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
}

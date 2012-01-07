package SJmp3.Threads;
import SJmp3.Actions;
import SJmp3.LoadSave.LoadFileMp3;
import SJmp3.SJmp3gui;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.kc7bfi.jflac.FLACDecoder;
import org.kc7bfi.jflac.apps.Analyser;
import org.kc7bfi.jflac.apps.Decoder;
import org.kc7bfi.jflac.apps.SeekTablePlayer;
import org.kc7bfi.jflac.io.BitInputStream;
import org.kc7bfi.jflac.metadata.Metadata;
import org.kc7bfi.jflac.metadata.SeekPoint;
import org.kc7bfi.jflac.metadata.SeekTable;
import org.kc7bfi.jflac.metadata.StreamInfo;
import org.kc7bfi.jflac.sound.spi.FlacAudioFileReader;
//import org.kc7bfi.jflac.sound.spi.FlacAudioFormat;
//import sun.net.ftp.FtpProtocolException;

public class Play_Thread extends Thread {
    
    public AdvancedPlayer player;
    public InputStream nis=null;
    public BufferedInputStream bis=null;   
    public FileInputStream fis = null;
    public AudioFileFormat aff=null;
    public AudioFormat af = null; 
    public AudioInputStream ais = null;
    public File fmp3; 
    public Clip clip=null;
    public DataLine.Info info;
    ////////////////////////////
    public FlacAudioFileReader fafr;
    public StreamInfo streamINFO;
    public BitInputStream bitis;
    public FLACDecoder flacDECODER;
    //public FlacAudioFormat faf;
    public SeekPoint seekPOINT;
    public SeekTable seekTABLE;
    public SeekTablePlayer seekTablePLAYER;
    public Sequencer sequencer;
    public Sequence sequence;
    public Metadata [] metaDATA;
    
    public Play_Thread () 
     {  
        start();  
     }
    
    public void getId3tagFile () {
        Actions.date=(String) aff.properties().get("date");
        Actions.title=(String) aff.properties().get("title");
        Actions.channels=(Integer)aff.properties().get("mp3.channels");
        Actions.bitrate=(Integer) aff.properties().get("mp3.bitrate.nominal.bps");
        Actions.freq=(Integer) aff.properties().get("mp3.frequency.hz");
        Actions.sizeFile=(Integer) aff.properties().get("mp3.length.bytes");
        Actions.sizeFrame=(Integer) aff.properties().get("mp3.framesize.bytes");            
        Actions.frames=(Integer) aff.properties().get("mp3.length.frames");
        Actions.author=(String) aff.properties().get("author");
        Actions.encoding=(String) aff.properties().get("mp3.version.encoding");   
        Actions.layer=(String) aff.properties().get("mp3.version.layer");
        Actions.mpeg=(String) aff.properties().get("mp3.version.mpeg");
        Actions.track=(String) aff.properties().get("mp3.id3tag.track"); 
        Actions.genre=(String) aff.properties().get("mp3.id3tag.genre");               
        Actions.album=(String) aff.properties().get("album");
        Actions.vbr=(Boolean)aff.properties().get("mp3.vbr");
        Actions.crc=(Boolean)aff.properties().get("mp3.crc");
        Actions.duration=(Long) aff.properties().get("duration");
        Actions.fps=(Float) aff.properties().get("mp3.framerate.fps");
        Actions.duration=Actions.duration/1000;            
    }
    
    public void getId3tagStream () {
        Actions.title=(String) aff.properties().get("title");
        Actions.channels=(Integer)aff.properties().get("mp3.channels");
        Actions.bitrate=(Integer) aff.properties().get("mp3.bitrate.nominal.bps");
        Actions.freq=(Integer) aff.properties().get("mp3.frequency.hz");
        Actions.sizeFrame=(Integer) aff.properties().get("mp3.framesize.bytes");            
        Actions.author=(String) aff.properties().get("author");
        Actions.album=(String) aff.properties().get("album");
        Actions.vbr=(Boolean)aff.properties().get("mp3.vbr");
        Actions.crc=(Boolean)aff.properties().get("mp3.crc");
        Actions.fps=(Float) aff.properties().get("mp3.framerate.fps");
        Actions.encoding=(String) aff.properties().get("mp3.version.encoding");  
        Actions.layer=(String) aff.properties().get("mp3.version.layer");
        Actions.mpeg=(String) aff.properties().get("mp3.version.mpeg");                 
    } 

    public void playMp123 () {
        try 
         {
           player = new AdvancedPlayer(bis);
         } 
        catch (JavaLayerException ex) 
         {
           Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
         }
        player.setPlayBackListener(new PlaybackListener() {
        @Override
        public void playbackFinished(PlaybackEvent event) {
            Actions.currentFrame = Math.round((Actions.SPT)*Actions.fps/1000);
               //event.getFrame();
            }
        }); 
        Actions.NextFlag=false;
        try 
         {
           player.play(Actions.currentFrame, Actions.frames);
         } 
        catch (JavaLayerException ex) 
         {
           Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
           Actions.WrongPathUrlException("Unsupported Audio !\nJavaLayerException");            
         }
        player.close();
        Actions.NextFlag=true;        
    }
    
    public void getAudioFormatInfo () {
        Actions.title=af.toString();
        //Actions.title=aff.properties().toString();
        Actions.channels=af.getChannels();              //System.out.println(Actions.channels);
        Actions.sizeFrame=af.getFrameSize();            //System.out.println(Actions.sizeFrame);
        Actions.encoding=af.getEncoding().toString();   //System.out.println(Actions.encoding);
        Actions.fps=af.getFrameRate();                  //System.out.println(Actions.fps);
        Actions.freq=(int) af.getSampleRate();          //System.out.println(Actions.freq);
        Actions.sizeFile=Actions.sizeFrame*Actions.frames;
        Actions.bitrate=(int)(8*Actions.sizeFrame*Actions.fps); 
        Actions.sizeSampleBit=af.getSampleSizeInBits(); //System.out.println(Actions.sizeSampleBit);
        //System.out.println(af.getSampleSizeInBits());
        //AudioFormat.Encoding.ALAW;
        //AudioFormat.Encoding.ULAW; 
        //AudioFormat.Encoding.PCM_FLOAT; 
        //AudioFormat.Encoding.PCM_SIGNED;   
        //AudioFormat.Encoding.PCM_UNSIGNED; 
    }
    
    public void playWavAuAif () { 
        try 
         {
            ais = AudioSystem.getAudioInputStream(fmp3);
            //if (Actions.affType.equals("aiff-c"))
            // {
            //     info = new DataLine.Info(Clip.class, af);
            //     if (!AudioSystem.isLineSupported(info)) {
            //        System.err.println("Line is not supported");
            //        return; }
            //     clip = (Clip)AudioSystem.getLine(info);
            // }
            //else 
            clip= AudioSystem.getClip();
            clip.open(ais);
            Actions.duration=clip.getMicrosecondLength()/1000;
            Actions.frames=clip.getFrameLength();
            getAudioFormatInfo();
            //Actions.fps=(float)Math.round(1000*(Actions.frames/Actions.duration));
            //Actions.freq=(int)Actions.fps;
            if (Actions.urlFlag==true) 
                Actions.NetStreamGuiOff(); 
            else 
                Actions.NetStreamGuiOn();
            SJmp3gui.TimeTFend.setText(Actions.TimeShow(Actions.duration));
            clip.setFramePosition(Actions.currentFrame); 
            clip.setMicrosecondPosition(1000*Actions.SPT);
            Actions.NextFlag=false;
            clip.start();
            do 
             {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
                }                
             }
            while(Actions.duration-Actions.SPT>0);
            //clip.stop();
            clip.close();
            //Actions.NextFlag=true;            
        } catch (IOException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void getInfoFlacFile () {
        //Actions.title=af.toString();
        //Actions.title=aff.properties().toString();
        Actions.channels=af.getChannels();
        System.out.println("af.getChannels = "+Actions.channels);        
        //Actions.sizeFrame=af.getFrameSize();           
        Actions.encoding=af.getEncoding().toString();
        System.out.println("af.getEncoding = "+Actions.encoding);        
        //Actions.fps=af.getFrameRate();                  
        Actions.freq=(int) af.getSampleRate(); 
        System.out.println("af.getSampleRate = "+Actions.freq);        
        //Actions.bitrate=(int)(8*Actions.sizeFrame*Actions.fps); 
        Actions.sizeSampleBit=af.getSampleSizeInBits();
        System.out.println("af.getSampleSizeInBits = "+Actions.sizeSampleBit);
        //Actions.channels=(int) aff.properties().get("flac.channels");
        //Actions.freq=(Integer)aff.getProperty("flac.sampleRate");
        //Actions.sizeFrame=aff.getFrameLength();            
        Actions.allSamples=(long) aff.properties().get("flac.totalSamples");
        Actions.FlacBlockMax=Integer.parseInt(aff.properties().get("flac.blocksize.max").toString()); 
        Actions.FlacBlockMin=Integer.parseInt(aff.properties().get("flac.blocksize.min").toString());    
        Actions.FlacFrameMax=Integer.parseInt(aff.properties().get("flac.framesize.max").toString()); 
        Actions.FlacFrameMin=Integer.parseInt(aff.properties().get("flac.framesize.min").toString());           
        System.out.println("aff.getProperty(\"flac.totalSamples\") = "+Actions.allSamples);        
        Actions.duration=Actions.allSamples/Actions.freq;
                //(long) aff.getProperty("flac.duration");
        Actions.sizeFile=aff.getByteLength();
        Actions.bitrate= Math.round(Actions.sizeFile*8/Actions.duration); 
        Actions.duration=Actions.duration*1000;  
        SJmp3gui.TimeTFend.setText(Actions.TimeShow(Actions.duration));
    }    

    public void playFlacClip () { 
        Actions.NextFlag=false;
        Actions.duration=Long.MAX_VALUE; 
        getInfoFlacFile ();
        try {
            ais = AudioSystem.getAudioInputStream(bis);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clip= AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clip.open(ais);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        clip.start();
        do 
         {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
            }                
         }
        while(Actions.duration-Actions.SPT>0);
        //clip.stop();
        clip.close();        
    }  
    
    public void playFlacJflac () { 
        Actions.NextFlag=false;
        Actions.duration=Long.MAX_VALUE;
        Actions.frames=Integer.MAX_VALUE;
        getInfoFlacFile ();
        //getAudioFormatInfo();        
        bitis=new BitInputStream(bis);
        try {
            seekPOINT=new SeekPoint(bitis);
            streamINFO=new StreamInfo(bitis,34,true);
            seekTABLE=new SeekTable(bitis, 1800, true);
            //flacDECODER=new FLACDecoder(bis);
            //streamINFO=flacDECODER.getStreamInfo();
            //metaDATA=flacDECODER.readMetadata();            
        } catch (IOException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("metaDATA = "+metaDATA);
        System.out.println("seekPOINT.getFrameSamples = "+seekPOINT.getFrameSamples());
        System.out.println("seekPOINT.getSampleNumber = "+seekPOINT.getSampleNumber());  
        System.out.println("seekPOINT.getStreamOffset = "+seekPOINT.getStreamOffset());        
        System.out.println("seekTABLE.calcLength = "+seekTABLE.calcLength());
        System.out.println("seekTABLE.numberOfPoints = "+seekTABLE.numberOfPoints());
        System.out.println("seekTABLE.getSeekPoint = "+seekTABLE.getSeekPoint(0));  
        System.out.println("streamINFO.calcLength = "+streamINFO.calcLength());
        System.out.println("streamINFO.getAudioFormat = "+streamINFO.getAudioFormat());
        System.out.println("streamINFO.getBitsPerSample = "+streamINFO.getBitsPerSample());
        System.out.println("streamINFO.getChannels = "+streamINFO.getChannels());
        System.out.println("streamINFO.getMaxBlockSize = "+streamINFO.getMaxBlockSize());
        System.out.println("streamINFO.getMaxFrameSize = "+streamINFO.getMaxFrameSize()); 
        System.out.println("streamINFO.getMinBlockSize = "+streamINFO.getMinBlockSize());
        System.out.println("streamINFO.getMinFrameSize = "+streamINFO.getMinFrameSize());
        System.out.println("streamINFO.getSampleRate = "+streamINFO.getSampleRate());
        System.out.println("streamINFO.getTotalSamples = "+streamINFO.getTotalSamples());
        seekTablePLAYER=new SeekTablePlayer();
        seekTablePLAYER.processStreamInfo(streamINFO);
        seekTablePLAYER.processMetadata(seekTABLE);        
        try {
            seekTablePLAYER.play(LoadFileMp3.putf, 0,100);
        } catch (IOException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        do 
        {
            try {
                 Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
            }                
        }
        while(Actions.duration-Actions.SPT>0);
    }    
    
    public void playMIDI () {
        //Actions.frames=Integer.MAX_VALUE;
        Actions.NextFlag=false;
        Actions.duration=Long.MAX_VALUE; 
        getAudioFormatInfo ();
        try {
            sequencer = MidiSystem.getSequencer();
            sequence=MidiSystem.getSequence(bis);
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sequencer.open();
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sequencer.setSequence(sequence);
            //sequencer.setSequence(bis);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
        }
        Actions.duration=sequencer.getMicrosecondLength()/1000;
        Actions.fps=Actions.freq;
        sequencer.setMicrosecondPosition(1000*Actions.SPT);
        sequencer.start();
        do 
        {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
                }                
        }
        while(Actions.duration-Actions.SPT>0);
        sequencer.stop();
        sequencer.close();
    }
    
    public void getGenericInfo () {
        System.out.println(af);             
        System.out.println("AFF type = "+Actions.affType);
        System.out.println(aff); 
        System.out.println("AFF prop = "+aff.properties());
    }

    @Override
    public void run () {
        System.out.println("Play = GO !!!");
        String bufpu=LoadFileMp3.putf.toLowerCase();
        if (bufpu.startsWith("http://")||bufpu.startsWith("ftp://")||bufpu.startsWith("https://"))
            Actions.urlFlag=true;
        else
            Actions.urlFlag=false; 
        try 
         {
          if (Actions.urlFlag==true) // STREAM
            {
             Actions.frames=Integer.MAX_VALUE;
             Actions.duration=Long.MAX_VALUE;                
             URL url=new URL(LoadFileMp3.putf);
             //System.setProperty("http.proxyUser", "koldaev");
             //System.setProperty("http.proxyPassword", "koldaev");             
             //URLConnection conn=url.openConnection(proxy);
             try 
              {
               if (Actions.useProxy.equals("true"))
                { 
                  Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Actions.proxyIP,Integer.parseInt(Actions.proxyPort)));
                  Authenticator authenticator = new Authenticator() {
                  public PasswordAuthentication getPasswordAuthentication() {
                     return (new PasswordAuthentication(Actions.proxyLogin, Actions.proxyPassw.toCharArray()));  
                      }    
                  };
                  Authenticator.setDefault(authenticator);                     
                  nis=url.openConnection(proxy).getInputStream();
                }  
               else  
                  nis=url.openStream(); 
               ///
               bis = new BufferedInputStream(nis); 
               aff = AudioSystem.getAudioFileFormat(bis);  // ONLY BIS - ELSE ERRORS !!!!!!
               Actions.affType=aff.getType().toString().toLowerCase();
               af = aff.getFormat();
               getGenericInfo();
               if (Actions.affType.contains("mp3"))
                {
                    getId3tagStream();
                    Actions.NetStreamGuiOff(); 
                    playMp123();
                }
               if (Actions.affType.contains("wav")||Actions.affType.contains("au")||Actions.affType.contains("aif")||Actions.affType.contains("snd"))
                {
                    playWavAuAif();
                } 
               if (Actions.affType.contains("midi"))
                {
                    playMIDI();
                }
              }
             catch (IOException ff) 
              {
                Actions.WrongPathUrlException("Net File or Stream Not Found !\nWrong Path !\nIOException"); 
                if (bis!=null) bis.close();
                if (nis!=null) nis.close();
              }
            }
          else // urlFlag=false - FILE
            {
             fis = new FileInputStream(LoadFileMp3.putf);            
             bis = new BufferedInputStream(fis); 
             fmp3=new File(LoadFileMp3.putf);  
             //if (LoadFileMp3.putf.toLowerCase().endsWith(".flac"))
             //   {
             //       fafr=new FlacAudioFileReader();
             //       aff=fafr.getAudioFileFormat(fmp3);
             //       //aff.
             //   }
             //else 
                aff = AudioSystem.getAudioFileFormat(fmp3);
             //aff = AudioSystem.getAudioFileFormat(fmp3);
             Actions.affType=aff.getType().toString().toLowerCase();
             af = aff.getFormat();
             getGenericInfo();
             if (Actions.affType.contains("mp3"))
                {
                    getId3tagFile();
                    Actions.NetStreamGuiOn(); 
                    playMp123();
                }
             if (Actions.affType.contains("wav")||Actions.affType.contains("au")||Actions.affType.contains("aif")||Actions.affType.contains("snd"))
                {
                    playWavAuAif();
                }  
             if (Actions.affType.contains("flac"))
                {
                    //getInfoFlacFile ();
                    //playFlacClip(); 
                    playFlacJflac();
                }   
             if (Actions.affType.contains("midi"))
                {
                    playMIDI();
                }                
            }
        if ((Actions.urlFlag==true)&(Actions.NextFlag==true)&(Actions.PlayFlag==true))
          {
            Actions.PlayNext();
            System.out.println("!!!!!!!! Network Stream Play Next !!!!!!!!!!!!");
          }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
            Actions.WrongPathUrlException("File Not Found !\nWrong Path !\nFileNotFoundException");
        } catch (IOException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
            Actions.WrongPathUrlException("Unsupported Audio !\nIOException");            
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
            Actions.WrongPathUrlException("Unsupported Audio !\nUnsupportedAudioFileException"); 
        } catch (NullPointerException nn) {        
            
        } finally {
            try {
                bis.close();
                if (Actions.urlFlag==true) 
                    nis.close();                  
                else
                    fis.close();
            } catch (IOException ex) {
                Logger.getLogger(Play_Thread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException nn) {}
        }
        System.out.println("Play = STOP");
    } // run
}// class

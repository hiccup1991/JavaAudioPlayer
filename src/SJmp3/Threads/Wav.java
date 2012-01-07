
package SJmp3.Threads;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/// formats = au, aiff, wav

public class Wav {
    
    public static void playWav () { 
        AudioInputStream ais = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        InputStream nis = null;
        LineListener listener = null;
        int currentFrame=0;
        try {
                // from a wave File
                            //File soundFile = new File("eatfood.wav");
                fis = new FileInputStream("snd.wav");            
                bis = new BufferedInputStream(fis); 
                ais = AudioSystem.getAudioInputStream(bis);
                // from a URL
                URL url = new URL("http://www.zzz.com/eatfood.wav");
                nis=url.openStream(); 
                bis = new BufferedInputStream(nis);                
                ais = AudioSystem.getAudioInputStream(bis);   
                //
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.setFramePosition(currentFrame); 
                clip.start();
                ////////////////////////
                clip.getFramePosition();
                clip.setFramePosition(0);
                clip.getMicrosecondLength();
                clip.setMicrosecondPosition(0);
                clip.getFormat();
                clip.addLineListener(listener);
                clip.stop();
                ///     PAUSE !!
                if (clip.isRunning())
                 {
                     currentFrame=clip.getFramePosition();
                     clip.stop();
                 }
                //clip.getClass().newInstance().
                //SoundEngine se=new SoundEngine();
                Media hit = new Media("bip.mp3");
                //hit.
            } 
        catch(IOException | UnsupportedAudioFileException | LineUnavailableException exc )
            {                                                                               
                exc.printStackTrace();                                                        
            }  
    }
}

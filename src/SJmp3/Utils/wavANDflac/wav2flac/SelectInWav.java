package SJmp3.Utils.wavANDflac.wav2flac;

import SJmp3.Filters.MFileFilter;
import SJmp3.SJmp3gui;
import static SJmp3.Utils.wavANDflac.wav2flac.WavToFlac.tfWavIn;
import javax.swing.JFileChooser;

public class SelectInWav {

    public static String putf="",putd="";

    public static void Select () {
        JFileChooser myf = new JFileChooser();
        myf.addChoosableFileFilter(new MFileFilter(".wav"));
        //myf.addChoosableFileFilter(new MFileFilter(".MP3"));  
        myf.setAcceptAllFileFilterUsed(false);
	switch (myf.showDialog(SJmp3gui.frame, "Select Input Wav File"))
	 {
	  case JFileChooser.APPROVE_OPTION:
            putf = myf.getSelectedFile().getPath();
            //putd = myf.getSelectedFile()+"";
            tfWavIn.setText(putf);
            break;
	  case JFileChooser.CANCEL_OPTION: break;
	 }
    }// select
    
}

package SJmp3.Utils.wavANDflac.flac2wav;

import SJmp3.Filters.MFileFilter;
import SJmp3.SJmp3gui;
import static SJmp3.Utils.wavANDflac.flac2wav.FlacToWav.tfInFlac;
import javax.swing.JFileChooser;

public class SelectInFlac {

    public static String putf="",putd="";

    public static void Select () {
        JFileChooser myf = new JFileChooser();
        myf.addChoosableFileFilter(new MFileFilter(".flac"));
        //myf.addChoosableFileFilter(new MFileFilter(".MP3"));  
        myf.setAcceptAllFileFilterUsed(false);
	switch (myf.showDialog(SJmp3gui.frame, "Select Input Flac File"))
	 {
	  case JFileChooser.APPROVE_OPTION:
            putf = myf.getSelectedFile().getPath();
            //putd = myf.getSelectedFile()+"";
            tfInFlac.setText(putf);
            break;
	  case JFileChooser.CANCEL_OPTION: break;
	 }
    }// select
    
}

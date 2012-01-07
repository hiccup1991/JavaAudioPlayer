package SJmp3.Utils.wavANDflac.wavIflac;

import SJmp3.SJmp3gui;
import javax.swing.JFileChooser;

public class SelectOutFolder {
    
    public static void Select () {
      JFileChooser myd = new JFileChooser();
      //myd.addChoosableFileFilter(new MFileFilter(".mp3"));
     // myd.addChoosableFileFilter(new MFileFilter(".MP3")); 
      //myd.addChoosableFileFilter(new AudioFileFilter());
      myd.setAcceptAllFileFilterUsed(false);
      myd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      switch (myd.showDialog(SJmp3gui.frame, "Select Output Folder"))
       {
       case JFileChooser.APPROVE_OPTION:
        WavAndFlac.tfOut.setText(myd.getSelectedFile().getPath());
        break;
       case JFileChooser.CANCEL_OPTION:
        break;
       }//switch
    }    
    
}

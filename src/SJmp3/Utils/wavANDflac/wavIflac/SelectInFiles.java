package SJmp3.Utils.wavANDflac.wavIflac;

import SJmp3.Filters.MFileFilter;
import SJmp3.SJmp3gui;
import static SJmp3.Utils.wavANDflac.wavIflac.WavAndFlac.ListFiles;
//import static SJmp3.Utils.wavANDflac.flac2wav.FlacToWav.tfInFlac;
import static SJmp3.Utils.wavANDflac.wavIflac.WavAndFlac.comboSelectType;
import static SJmp3.Utils.wavANDflac.wavIflac.WavAndFlac.jList1;
import java.io.File;
import javax.swing.JFileChooser;

public class SelectInFiles {

    public static String putf="",putd="";
    public static String title;
    public static File [] inFiles;
    public static String [] inFilesString;
    public static JFileChooser myf;

    public static void Select () {
        myf = new JFileChooser();
        myf.setMultiSelectionEnabled(true);
        if (comboSelectType.getSelectedItem().toString().equals("Wav To Flac"))
            {
                myf.addChoosableFileFilter(new MFileFilter(".wav"));
                title="Select Input Wav Files";
            }
        else
            {
                myf.addChoosableFileFilter(new MFileFilter(".flac"));
                title="Select Input Flac Files";
            }
        myf.setAcceptAllFileFilterUsed(false);
	switch (myf.showDialog(SJmp3gui.frame,title))
	 {
	  case JFileChooser.APPROVE_OPTION:
              inFiles=myf.getSelectedFiles();
              System.out.println(inFiles.length);
              for (int i=0; i < inFiles.length; i++)
                {
                  //inFilesString[i]=inFiles[i].getPath();
                  ListFiles.add(inFiles[i].getPath());
                }
              jList1.setListData(ListFiles);
            break;
	  case JFileChooser.CANCEL_OPTION: break;
	 }
    }// select
    
}

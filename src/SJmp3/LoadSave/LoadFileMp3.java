package SJmp3.LoadSave;
import SJmp3.Actions;
import SJmp3.Filters.AudioFileFilter;
import SJmp3.Filters.AudioVideoFileFilter;
import SJmp3.Filters.HFileFilterMp3;
import SJmp3.Filters.MFileFilter;
import SJmp3.SJmp3gui;
import SJmp3.video.jv_player;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
public class LoadFileMp3 {
public static String putf="",putd="";
    public static void Select () {
        JFileChooser myf = new JFileChooser();
        //myf.addChoosableFileFilter(new MFileFilter(".mp3"));
        //myf.addChoosableFileFilter(new MFileFilter(".MP3"));  
        myf.addChoosableFileFilter(new AudioFileFilter());        
        myf.setAcceptAllFileFilterUsed(false);
	switch (myf.showDialog(SJmp3gui.frame, "Open File"))
	{
	 case JFileChooser.APPROVE_OPTION:
         Actions.currentFrame=0;
	 putf = myf.getSelectedFile().getPath();
         if (putf.toLowerCase().endsWith(".avi")||putf.toLowerCase().endsWith(".divx"))
            { 
                File xvideo=new File(putf);
                try 
                {  
                    new jv_player(xvideo);
                    //return;
                    break;
                } 
                catch (Exception ex) {
                    Logger.getLogger(LoadFileMp3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         putd = myf.getSelectedFile()+"";
         System.out.println(putf);
         System.out.println(putd);
         File d=myf.getCurrentDirectory();
         LoadFolderMp3.putf=d.getAbsolutePath();
         LoadFolderMp3.putd=d.getAbsolutePath();
         Actions.currentList3m.add(putf);
         //SJmp3gui.LST.setListData(Actions.zero);
         Actions.List3mToBuferList();
         SJmp3gui.currentLST.setListData(Actions.currentBuferList);         
	 break;
	 case JFileChooser.CANCEL_OPTION:
	 break;
	}
    }// select
}

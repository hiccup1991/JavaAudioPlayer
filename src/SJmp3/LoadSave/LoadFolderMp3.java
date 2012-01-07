package SJmp3.LoadSave;
import SJmp3.Actions;
import SJmp3.Filters.AudioFileFilter;
import SJmp3.Filters.FilterForFolderAll;
import SJmp3.Filters.FilterForFolderCustom;
import SJmp3.Filters.MFileFilter;
import SJmp3.Filters.MFileNameFilter;
import SJmp3.SJmp3gui;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
public class LoadFolderMp3 {
    public static String putf="";
    public static String putd="";
    public static void Select () {
      JFileChooser myd = new JFileChooser();
      //myd.addChoosableFileFilter(new FilterForFolderCustom(".mp3"));
      //myd.addChoosableFileFilter(new FilterForFolderCustom(".wav")); 
      myd.addChoosableFileFilter(new FilterForFolderAll());
      myd.setAcceptAllFileFilterUsed(false);
      myd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      switch (myd.showDialog(SJmp3gui.frame, "Open Folder"))
       {
       case JFileChooser.APPROVE_OPTION:
        Actions.currentFrame=0;
        putf=myd.getSelectedFile().getPath();
        putd=myd.getSelectedFile()+ "";
        //SJmp3gui.TF.setText(putf);
        frolovList(putd);
        Actions.DirArray[Actions.currentPlayer]=putd;
        Actions.M3uArray[Actions.currentPlayer]="";        
        break;
       case JFileChooser.CANCEL_OPTION:
        break;
       }//switch
    }
    public static void frolovList(String szDir) {
        File f = new File(szDir);
        String[] sDirList = f.list();
        int i;
        for(i = 0; i < sDirList.length; i++)
        {
          File f1 = new File(szDir + File.separator + sDirList[i]);
          if(f1.isFile()) 
            {
              System.out.println(szDir + File.separator + sDirList[i]);
              if (Actions.extFileCheck(f1)) 
              Actions.currentList3m.add(szDir + File.separator + sDirList[i]);
            }
          else  
            {
                frolovList(szDir + File.separator + sDirList[i]);
            }     
        }
        //SJmp3gui.LST.setListData(Actions.list3m);
        Actions.List3mToBuferList();
        SJmp3gui.currentLST.setListData(Actions.currentBuferList);        
    }
}

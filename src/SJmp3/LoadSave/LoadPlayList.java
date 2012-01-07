package SJmp3.LoadSave;
import SJmp3.Actions;
import SJmp3.Filters.HFileFilterM3u;
import SJmp3.Filters.MFileFilter;
import SJmp3.SJmp3gui;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
public class LoadPlayList {
    public static String putf="",putd="";
    public static FileInputStream fis;
    public static BufferedReader br;
    public static String bufer="",txt="";
    public static void Select () {
        JFileChooser myf = new JFileChooser();
        myf.addChoosableFileFilter(new MFileFilter(".m3u"));
        myf.addChoosableFileFilter(new MFileFilter(".M3U"));        
        myf.setAcceptAllFileFilterUsed(false);
	switch (myf.showDialog(SJmp3gui.frame, "Load PlayList"))
	{
	 case JFileChooser.APPROVE_OPTION:
         Actions.currentFrame=0;
	 putf = myf.getSelectedFile().getPath();
         putd = myf.getSelectedFile()+"";
         //if (!(putf.endsWith("m3u")||putf.endsWith("M3U"))) 
         // {
         //  JOptionPane.showMessageDialog(null,"Not m3u-file !","Error !",JOptionPane.ERROR_MESSAGE);
         //  break;
         // }         
         System.out.println(putf);
         int aa=SJmp3gui.tabbedPane.getSelectedIndex();
         Actions.M3uArray[aa]=putf;
         Actions.currentM3u=putf;
         Actions.currentDir="";
         Actions.DirArray[aa]="";
         try {
              fis = new FileInputStream(putf);
              br=new BufferedReader(new InputStreamReader(fis));
              int j=0;
                do
                  {
                    if (br.ready()) {bufer=br.readLine();}
                    if (txt.equals(bufer)) {break;} 
                    if (!bufer.startsWith("#EXT")) 
                     {
                         Actions.currentList3m.add(bufer);
                         System.out.println(Actions.currentList3m.elementAt(j));
                         j++;
                     }//if
                    txt=bufer;
                  }
                while (true);
              }
         catch (IOException ex) 
              {
                Logger.getLogger(LoadPlayList.class.getName()).log(Level.SEVERE, null, ex);
              }//catch//catch
         Actions.List3mToBuferList();
         SJmp3gui.currentLST.setListData(Actions.currentBuferList);
        {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(LoadPlayList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(LoadPlayList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         break;
	 case JFileChooser.CANCEL_OPTION:
	 break;
	}//switch
    }// select
}

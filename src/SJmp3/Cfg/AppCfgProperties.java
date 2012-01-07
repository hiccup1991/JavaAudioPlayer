package SJmp3.Cfg;
import SJmp3.Actions;
import SJmp3.SJmp3gui;
import java.io.*;
import java.util.Properties;
public class AppCfgProperties {
 public static void Load () {
    Properties prop = new Properties();
    try {
        prop.load(new InputStreamReader(new FileInputStream("cfg/sjmp3_prop.cfg"),"UTF-8"));
        System.out.println("skin="+prop.getProperty("skin"));
        System.out.println("loop="+prop.getProperty("loop"));        
        Actions.currentLAF=prop.getProperty("skin");
        //Actions.currentLoop=prop.getProperty("loop");
        Actions.currentM3u=prop.getProperty("m3u"); 
        //Actions.currentShuffle=prop.getProperty("shuffle");        
        Actions.currentMixer=Integer.parseInt(prop.getProperty("mixer")); 
        Actions.currentFrame=0;        
        if (!Actions.currentM3u.isEmpty()) 
        {
        FileInputStream fis = new FileInputStream(Actions.currentM3u);
        BufferedReader  br=new BufferedReader(new InputStreamReader(fis));
        String bufer="",txt="";
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
        br.close();
        fis.close();
        //SJmp3gui.LST.setListData(Actions.currentList3m);
        Actions.List3mToBuferList();
        SJmp3gui.currentLST.setListData(Actions.currentBuferList);        
        }
    }
    catch (IOException ex) { 
        Actions.currentLAF="javax.swing.plaf.metal.MetalLookAndFeel";
        //Actions.currentLoop="false";
        Actions.currentM3u="";
        //Actions.currentShuffle="false";        
        Actions.currentMixer=100;
        SJmp3gui.currentLST=SJmp3gui.LST1; 
        Actions.currentList3m=Actions.list3m1; 
        Actions.currentBuferList=Actions.BuferList1;        
    } 
    catch (NullPointerException e) { };
  }
 public static void Save (String s, String lp, String pl, String val, String sh) {
    Properties prop = new Properties();
    try {
        prop.setProperty("skin",s);
        prop.setProperty("loop",lp);
        prop.setProperty("m3u",pl); 
        prop.setProperty("mixer",val); 
        prop.setProperty("shuffle",sh);         
        File cfgdir = new File("cfg");
        cfgdir.mkdir();        
        prop.store(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("cfg/sjmp3_prop.cfg"), "UTF-8")), null);
        } 
    catch (IOException ex) {  ex.printStackTrace();   }
    }
}

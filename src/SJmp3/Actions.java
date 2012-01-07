package SJmp3;
import SJmp3.ClipBoard.ClipboardTextTransfer;
import SJmp3.ClipBoard.ShowClipBoard;
import SJmp3.Threads.Play_Thread;
import SJmp3.Threads.Next_Thread;
import SJmp3.LoadSave.LoadPlayList;
import SJmp3.LoadSave.LoadFileMp3;
import SJmp3.LoadSave.LoadFolderMp3;
import static SJmp3.SJmp3gui.frame;
import SJmp3.Threads.Slider_Thread;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.pushingpixels.lafwidget.animation.AnimationConfigurationManager;
import org.pushingpixels.lafwidget.animation.AnimationFacet;
public class Actions {
    public static String date;
    public static String title;
    public static int channels;
    public static int bitrate;
    public static int freq;
    public static int sizeFile;    
    public static int sizeFrame; 
    public static int sizeSampleBit;    
    public static int frames;
    public static int FlacFrameMax; 
    public static int FlacFrameMin; 
    public static int FlacBlockMax; 
    public static int FlacBlockMin;     
    public static long duration;
    public static long allSamples;
    public static String author;
    public static String album;
    public static Boolean vbr;
    public static Boolean crc;
    public static String encoding; 
    public static String layer; 
    public static String mpeg;  
    public static String track;    
    public static String genre;  
    public static String affType;
    public static Play_Thread PL;
    public static Next_Thread NP;
    public static Slider_Thread ST;
    public static int LSTindex;
    public static int currentFrame = 0;
    public static String [] zero={};
    public static boolean NextFlag=false;
    public static boolean PlayFlag=false;
    public static float fps;
    public static long SPT=0;  // Song Play Time !!
    public static String currentMute;
    public static String currentLAF;
    public static String currentTheme;    
    public static String currentM3u;
    public static String currentDir;    
    public static int currentMixer;
    public static String currentMode;
    public static Random mygen = new Random(new Date().getTime()); 
    public static Vector<String> currentList3m = new Vector<String>();
    public static Vector<String> currentBuferList = new Vector<String>();
    public static Vector<String> list3m1 = new Vector<String>();
    public static Vector<String> BuferList1 = new Vector<String>();  
    public static Vector<String> list3m2 = new Vector<String>();
    public static Vector<String> BuferList2 = new Vector<String>(); 
    public static Vector<String> list3m3 = new Vector<String>();
    public static Vector<String> BuferList3 = new Vector<String>(); 
    public static Vector<String> list3m4 = new Vector<String>();
    public static Vector<String> BuferList4 = new Vector<String>();    
    public static Vector<String> list3m5 = new Vector<String>();
    public static Vector<String> BuferList5 = new Vector<String>(); 
    public static Vector<String> list3m6 = new Vector<String>();
    public static Vector<String> BuferList6 = new Vector<String>();
    public static Vector<String> list3m7 = new Vector<String>();
    public static Vector<String> BuferList7 = new Vector<String>();    
    public static String [] M3uArray=new String[7]; 
    public static String [] DirArray=new String[7];     
    public static int currentPlayer; 
    public static boolean urlFlag=false;
    public static String proxyIP;  
    public static String proxyPort="3128";    
    public static String proxyPassw;   
    public static String proxyLogin; 
    public static String useProxy;  
    public static String useEffects; 
    public ImageIcon mp3icon = new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/waves-green.png"));     
    
    public void infoWavAuSndAif () {
        JOptionPane.showMessageDialog(SJmp3gui.frame,""+title+
                                                     "\nAudio Format = "+affType+
                                                     "\nChannels="+channels+
                                                     "\nEncoding="+encoding+
                                                     "\nSample Size="+sizeSampleBit+
                                                     " bit\nBitRate="+bitrate/1000+
                                                     " kBit/s\nFrame Size="+sizeFrame+
                                                     " bytes\nFrequency="+freq/1000+
                                                     " kHz\nFrames="+frames+
                                                     "\nSize="+sizeFile/1000+
                                                     " Kb\nFPS="+fps+
                                                     " frames/sek\nDuration="+duration/1000+" sek","File/Stream Info",JOptionPane.INFORMATION_MESSAGE,mp3icon);        
    }

    public void infoFlacFile () {
        JOptionPane.showMessageDialog(SJmp3gui.frame,""+title+
                                                     "\nAudio Format = "+affType+
                                                     "\nChannels = "+channels+
                                                     "\nEncoding = "+encoding+
                                                     "\nBitRate = "+bitrate/1000+
                                                     " kBit/s\nFrame Size Max = "+FlacFrameMax+
                                                     "\nFrame Size Min = "+FlacFrameMin+
                                                     "\nBlock Size Max = "+FlacBlockMax+
                                                     " samples\nBlock Size Min = "+FlacBlockMin+
                                                     " samples\nSample Size = "+sizeSampleBit+
                                                     " bit\nSample Rate = "+freq+
                                                     "\nAll Samples = "+allSamples+
                                                     "\nFile Size = "+sizeFile/1024+
                                                     " Kb\nDuration = "+duration/1000+" sek","File/Stream Info",JOptionPane.INFORMATION_MESSAGE,mp3icon);        
    }
    
    public void infoMpgaMp123 () {
        /* JOptionPane.showMessageDialog(predok-frame, Text, Title, Type);
        or JOptionPane.showMessageDialog(predok-frame, Text, Title, Type, Icon);
        */
        if (Actions.affType==null)  
          { JOptionPane.showMessageDialog(SJmp3gui.frame,"File/Stream not load !","Info",JOptionPane.INFORMATION_MESSAGE); return; }
        if (urlFlag==false)
          {
          switch (Actions.affType)  
           {
            case "mp3": JOptionPane.showMessageDialog(SJmp3gui.frame,"Date="+date +
                                                      "\nTitle="+title+
                                                      "\nChannels="+channels+
                                                      "\nBitrate="+bitrate/1000+
                                                      " kbps\nFrequency="+freq/1000+
                                                      " kHz\nSize="+sizeFile/1000+
                                                      " Kb\nFrames="+frames+
                                                      "\nFPS="+fps+
                                                      " frames/sek\nEncoding="+encoding+
                                                      "\nLayer=Layer "+layer+
                                                      "\nMpeg=MPEG-"+mpeg+
                                                      "\nFrame Size="+sizeFrame+
                                                      " bytes\nDuration="+duration/1000+
                                                      " sek\nVariable Bitrate="+vbr+
                                                      "\nCRC="+crc+
                                                      "\nTrack="+track+
                                                      "\nGenre="+genre+
                                                      "\nAuthor="+author+
                                                      "\nAlbum="+album,"File/Stream Info",JOptionPane.INFORMATION_MESSAGE,mp3icon); break;
            case "wave":    infoWavAuSndAif(); break;
            case "au":      infoWavAuSndAif(); break;
            case "aiff":    infoWavAuSndAif(); break;
            case "aiff-c":  infoWavAuSndAif(); break;
            case "snd":     infoWavAuSndAif(); break;  
            case "flac":    infoFlacFile (); break;
            case "midi":    infoWavAuSndAif(); break;
            default: JOptionPane.showMessageDialog(SJmp3gui.frame,"File Not Play !","Error !",JOptionPane.ERROR_MESSAGE);            
           }
         }
        else
         {
          switch (Actions.affType)  
           {
            case "mp3": JOptionPane.showMessageDialog(SJmp3gui.frame,"Title="+title+
                                                      "\nChannels="+channels+
                                                      "\nBitrate="+bitrate/1000+
                                                      " kbps\nFrequency="+freq/1000+
                                                      " kHz\nFPS="+fps+
                                                      " frames/sek\nEncoding="+encoding+
                                                      "\nLayer=Layer "+layer+
                                                      "\nMpeg=MPEG-"+mpeg+                 
                                                      "\nFrame Size="+sizeFrame+
                                                      " bytes\nVariable Bitrate="+vbr+
                                                      "\nCRC="+crc+
                                                      "\nAuthor="+author+
                                                      "\nAlbum="+album,"File/Stream Info",JOptionPane.INFORMATION_MESSAGE,mp3icon); break;
            case "wave":   infoWavAuSndAif(); break;
            case "au":     infoWavAuSndAif(); break;
            case "aiff":   infoWavAuSndAif(); break; 
            case "aiff-c": infoWavAuSndAif(); break;
            case "snd":    infoWavAuSndAif(); break;  
            case "flac":   infoFlacFile (); break;
            case "midi":   infoWavAuSndAif(); break;
            default: JOptionPane.showMessageDialog(SJmp3gui.frame,"File Not Play !","Error !",JOptionPane.ERROR_MESSAGE);
           }
         }
    }

    public void about () {
        About ab=new About(frame,true);
        ab.setVisible(true);
    }

    public static void ClosePlaySliderThreads () {
        PlayFlag=false;
        if (PL!=null) 
            {
                switch(Actions.affType)
                {
                    case "mp3":   PL.player.close();        break;
                    case "wave":  PL.clip.close();          break;
                    case "au":    PL.clip.close();          break; 
                    case "aiff":  PL.clip.close();          break;
                    case "aiff-c":PL.clip.close();          break;
                    case "snd":   PL.clip.close();          break;
                    case "midi":  PL.sequencer.close();     break;
                    case "flac":  PL.seekTablePLAYER=null;  break;                    
                }  
                ST.stop(); 
            }
        NextFlag=false;   
        PL=null;
        ST=null;
    }    

    public static void SelectFile () {
        int index;
        index=SJmp3gui.currentLST.getSelectedIndex();        
        LoadFileMp3.Select();
        SJmp3gui.currentLST.setSelectedIndex(index);        
    }

    public static void SelectFolder () {
        int index;
        index=SJmp3gui.currentLST.getSelectedIndex();         
        LoadFolderMp3.Select(); 
        SJmp3gui.currentLST.setSelectedIndex(index);        
    }  

    public static void GoPlay () {
        if (PlayFlag==true) return;
        try {
             ClosePlaySliderThreads ();
             if (LoadFileMp3.putf!="") 
              { PL = new Play_Thread(); 
                ST = new Slider_Thread("slider");
                PlayFlag=true;
                System.out.println("PlayFlag="+PlayFlag);             
              }
             else 
              {
               JOptionPane.showMessageDialog(SJmp3gui.frame,"FileNotFound !","Error !",JOptionPane.ERROR_MESSAGE);
              }
             System.out.println("Paused Frame = "+currentFrame);
            } 
        catch (NullPointerException e) {}
    }//metod

    public static void pause() {
        if (PlayFlag==false) return;        
        PlayFlag=false;
        if (PL!=null) 
            {
                switch(Actions.affType)
                {
                    case "mp3":  PL.player.stop(); break;
                    case "wave": 
                        Actions.currentFrame=PL.clip.getFramePosition();
                        //Actions.SPT=1000*PL.clip.getMicrosecondPosition();
                        PL.clip.close();  
                        break;
                    case "au": 
                        Actions.currentFrame=PL.clip.getFramePosition();
                        //Actions.SPT=1000*PL.clip.getMicrosecondPosition();
                        PL.clip.close();  
                        break; 
                    case "aiff": 
                        Actions.currentFrame=PL.clip.getFramePosition();
                        //Actions.SPT=1000*PL.clip.getMicrosecondPosition();
                        PL.clip.close();  
                        break;  
                    case "aiff-c": 
                        Actions.currentFrame=PL.clip.getFramePosition();
                        //Actions.SPT=1000*PL.clip.getMicrosecondPosition();
                        PL.clip.close();  
                        break;
                    case "snd": 
                        Actions.currentFrame=PL.clip.getFramePosition();
                        //Actions.SPT=1000*PL.clip.getMicrosecondPosition();
                        PL.clip.close();  
                        break; 
                    case "midi": 
                        Actions.currentFrame=Math.round(Actions.fps*PL.sequencer.getMicrosecondPosition()/1000000);
                        // Actions.currentFrame=Math.round((Actions.SPT)*Actions.fps/1000);
                        //Actions.SPT=1000*PL.clip.getMicrosecondPosition();
                        PL.sequencer.close();  
                        break;                        
                }  
                ST.stop(); 
            }        
        NextFlag=false;
        System.out.println("Paused on = "+currentFrame);             
    }

    public static void AddList() {
        int index;
        index=SJmp3gui.currentLST.getSelectedIndex();         
        LoadPlayList.Select();  
        SJmp3gui.currentLST.setSelectedIndex(index);        
    }

    public static void stopp() {
        SPT=0;        
        ClosePlaySliderThreads ();
        currentFrame=0; 
    }   

    public static void PlayNext () {
        stopp();
        PlayFlag=false;
        NextFlag=false;   
        if (LoadFileMp3.putf!="") 
        {
            try 
            {
            SPT=0;                
            currentFrame=0;
            if (currentMode.equals("shuffle"))
             {
               LSTindex=mygen.nextInt(currentList3m.size());
             }
            else  
             {  
                 if (currentMode.equals("repeat"))
                     LSTindex=SJmp3gui.currentLST.getSelectedIndex();
                 else
                     LSTindex=SJmp3gui.currentLST.getSelectedIndex()+1; 
             }
            SJmp3gui.currentLST.setSelectedIndex(LSTindex);
            System.out.println("LSTindex = "+LSTindex);            
            LoadFileMp3.putf=currentList3m.get(LSTindex);
            //if (LoadFileMp3.putf.toLowerCase().startsWith("http://")||LoadFileMp3.putf.toLowerCase().startsWith("ftp://")||LoadFileMp3.putf.toLowerCase().startsWith("https://"))
                //{ 
                try 
                 { NP.sleep(200); }
                catch (InterruptedException ex) 
                  { Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex); }
               // }
            System.out.println(LoadFileMp3.putf);
            //stopp();
            GoPlay ();
            }
            catch (ArrayIndexOutOfBoundsException e) 
            {
                if (!currentMode.equals("loop"))
                    {LSTindex=SJmp3gui.currentLST.getSelectedIndex()-1;}
                else
                    {
                        SJmp3gui.currentLST.setSelectedIndex(0);
                        Actions.currentFrame=0;
                        Actions.LSTindex=SJmp3gui.currentLST.getSelectedIndex();
                        LoadFileMp3.putf=currentList3m.get(Actions.LSTindex);
                        System.out.println(LoadFileMp3.putf);
                    }
                //stopp();
                GoPlay();
            }//catch//catch
        }
        else
        {
         JOptionPane.showMessageDialog(SJmp3gui.frame,"FileNotFound !","Error !",JOptionPane.ERROR_MESSAGE);
        }
    }  

    public static void PlayPrevious () {
        stopp();
        PlayFlag=false; 
        NextFlag=false;           
        if (LoadFileMp3.putf!="") 
        {
            SPT=0;            
            currentFrame=0;
            LSTindex=SJmp3gui.currentLST.getSelectedIndex();
            if (LSTindex>0) {LSTindex=SJmp3gui.currentLST.getSelectedIndex()-1;}
            SJmp3gui.currentLST.setSelectedIndex(LSTindex);
            System.out.println(LSTindex);
            LoadFileMp3.putf=currentList3m.get(LSTindex);
            System.out.println(LoadFileMp3.putf);
            System.out.println("LSTindex = "+LSTindex);
            //stopp();
            GoPlay ();
        }
            else
        {
            JOptionPane.showMessageDialog(SJmp3gui.frame,"FileNotFound !","Error !",JOptionPane.ERROR_MESSAGE);
        }    
    }  

    public static void FastForward () {
        if (PlayFlag==true) pause();
        if (SPT<0.89*duration) 
         {
            SPT=Math.round(SPT+duration/10);  
            Actions.currentFrame = Math.round((Actions.SPT)*Actions.fps/1000);
            SJmp3gui.SongSlider.setValue(Math.round(1000*Actions.SPT/Actions.duration));
            GoPlay();
         }
        else
         {
            PlayNext();
         } 
    }    

    public static void FastBackward () {
        if (PlayFlag==true) pause();
        if (SPT>0.11*duration) 
         {
            SPT=Math.round(SPT-duration/10);  
            Actions.currentFrame = Math.round((Actions.SPT)*Actions.fps/1000);
            SJmp3gui.SongSlider.setValue(Math.round(1000*Actions.SPT/Actions.duration));
            GoPlay();
         }
        else
         {
            PlayPrevious();
         } 
    }  

    public static String TimeShow (long TTT) {
        String min, sek;
        if (Math.round(TTT/60000)<10)
            min="0"+Math.round(TTT/60000);
        else
            min=""+Math.round(TTT/60000);
        if (Math.round((TTT%60000)/1000)<10)
            sek="0"+Math.round((TTT%60000)/1000);
        else
            sek=""+Math.round((TTT%60000)/1000);
        return min+":"+sek;
    }

    public static void RemoveAll () {
        ClosePlaySliderThreads ();        
        SJmp3gui.currentLST.setListData(Actions.zero);
        Actions.currentFrame=0;
        Actions.SPT=0;        
        Actions.currentList3m.removeAllElements();
        Actions.currentM3u="";
        Actions.currentDir="";        
        Actions.M3uArray[Actions.currentPlayer]="";
        Actions.DirArray[Actions.currentPlayer]="";        
    } 

    public static void RemoveSong () {
      try 
       {
         int bufer=SJmp3gui.currentLST.getSelectedIndex();
         Actions.currentList3m.removeElementAt(bufer);
         Actions.List3mToBuferList();
         SJmp3gui.currentLST.setListData(Actions.currentBuferList);         
        }
      catch (ArrayIndexOutOfBoundsException ee) 
      {
        JOptionPane.showMessageDialog(SJmp3gui.frame,"FileNotFound !","Error !",JOptionPane.ERROR_MESSAGE);          
      }        
    }

    public static void SongUp () {
        try 
          {  
            int index=SJmp3gui.currentLST.getSelectedIndex(); 
            String buf=Actions.currentList3m.elementAt(index);
            if (index-1>=0)
             {
                Actions.currentList3m.removeElementAt(index);
                Actions.currentList3m.insertElementAt(buf, index-1);
                Actions.List3mToBuferList();
                SJmp3gui.currentLST.setListData(Actions.currentBuferList);                       
                SJmp3gui.currentLST.setSelectedIndex(index-1);                
             }
            else  JOptionPane.showMessageDialog(SJmp3gui.frame,"Top List");
          }
        catch (ArrayIndexOutOfBoundsException ee) 
          {          
            JOptionPane.showMessageDialog(SJmp3gui.frame,"File Not Select !","Error !",JOptionPane.ERROR_MESSAGE); 
          }        
    }

    public static void SongDown () {
        try 
          {  
            int index=SJmp3gui.currentLST.getSelectedIndex(); 
            int max=Actions.currentList3m.size()-1;
            String buf=Actions.currentList3m.elementAt(index);
            if (index+1<=max)
             {
                Actions.currentList3m.removeElementAt(index);
                Actions.currentList3m.insertElementAt(buf, index+1);
                Actions.List3mToBuferList();
                SJmp3gui.currentLST.setListData(Actions.currentBuferList);                       
                SJmp3gui.currentLST.setSelectedIndex(index+1);                
             }
            else  JOptionPane.showMessageDialog(SJmp3gui.frame,"End List");
          }
        catch (ArrayIndexOutOfBoundsException ee) 
          {          
            JOptionPane.showMessageDialog(SJmp3gui.frame,"File Not Select !","Error !",JOptionPane.ERROR_MESSAGE); 
          }        
    }  

    public static void SliderMouse () {
     try 
       {  
        if (Actions.PlayFlag==true) Actions.pause();
        Actions.SPT=Actions.duration*SJmp3gui.SongSlider.getValue()/1000;  
        Actions.ST.stop(); //PL=null;
        Actions.currentFrame = Math.round((Actions.SPT)*Actions.fps/1000);
        SJmp3gui.TimeTFcur.setText(Actions.TimeShow(Actions.SPT)); 
       }
     catch (NullPointerException ee) {};
    }

    public static void List3mToBuferList () {
        Actions.currentBuferList.removeAllElements();
        for (int j=0; j<Actions.currentList3m.size(); j++)
            {
             Actions.currentBuferList.add(j+1+") "+Actions.currentList3m.get(j));
            }        
        Actions.BuferList1.removeAllElements();
        for (int j=0; j<Actions.list3m1.size(); j++)
            {
             Actions.BuferList1.add(j+1+") "+Actions.list3m1.get(j));
            }  
        Actions.BuferList2.removeAllElements();
        for (int j=0; j<Actions.list3m2.size(); j++)
            {
             Actions.BuferList2.add(j+1+") "+Actions.list3m2.get(j));
            }  
        Actions.BuferList3.removeAllElements();
        for (int j=0; j<Actions.list3m3.size(); j++)
            {
             Actions.BuferList3.add(j+1+") "+Actions.list3m3.get(j));
            }  
        Actions.BuferList4.removeAllElements();
        for (int j=0; j<Actions.list3m4.size(); j++)
            {
             Actions.BuferList4.add(j+1+") "+Actions.list3m4.get(j));
            }
        Actions.BuferList5.removeAllElements();
        for (int j=0; j<Actions.list3m5.size(); j++)
            {
             Actions.BuferList5.add(j+1+") "+Actions.list3m5.get(j));
            } 
        Actions.BuferList6.removeAllElements();
        for (int j=0; j<Actions.list3m6.size(); j++)
            {
             Actions.BuferList6.add(j+1+") "+Actions.list3m6.get(j));
            }
        Actions.BuferList7.removeAllElements();
        for (int j=0; j<Actions.list3m7.size(); j++)
            {
             Actions.BuferList7.add(j+1+") "+Actions.list3m7.get(j));
            }        
    }

    public static void LSTmouseClick () {
        switch (Actions.currentPlayer) {
            case 0:  
                SJmp3gui.LST2.clearSelection();
                SJmp3gui.LST3.clearSelection();                
                SJmp3gui.LST4.clearSelection();
                SJmp3gui.LST5.clearSelection(); 
                SJmp3gui.LST6.clearSelection();
                SJmp3gui.LST7.clearSelection();                
                break;
            case 1:  
                SJmp3gui.LST1.clearSelection();
                SJmp3gui.LST3.clearSelection();                
                SJmp3gui.LST4.clearSelection();
                SJmp3gui.LST5.clearSelection(); 
                SJmp3gui.LST6.clearSelection();
                SJmp3gui.LST7.clearSelection();                
                break;
            case 2:  
                SJmp3gui.LST1.clearSelection();
                SJmp3gui.LST2.clearSelection();                
                SJmp3gui.LST4.clearSelection();
                SJmp3gui.LST5.clearSelection(); 
                SJmp3gui.LST6.clearSelection();
                SJmp3gui.LST7.clearSelection();                
                break;  
            case 3:  
                SJmp3gui.LST1.clearSelection(); 
                SJmp3gui.LST2.clearSelection();                
                SJmp3gui.LST3.clearSelection();
                SJmp3gui.LST5.clearSelection(); 
                SJmp3gui.LST6.clearSelection();
                SJmp3gui.LST7.clearSelection();                
                break; 
            case 4:  
                SJmp3gui.LST1.clearSelection();
                SJmp3gui.LST2.clearSelection();                
                SJmp3gui.LST3.clearSelection();
                SJmp3gui.LST4.clearSelection();  
                SJmp3gui.LST6.clearSelection();
                SJmp3gui.LST7.clearSelection();                
                break;
            case 5:  
                SJmp3gui.LST1.clearSelection(); 
                SJmp3gui.LST2.clearSelection();                
                SJmp3gui.LST3.clearSelection();
                SJmp3gui.LST4.clearSelection(); 
                SJmp3gui.LST5.clearSelection();
                SJmp3gui.LST7.clearSelection();                
                break; 
            case 6:  
                SJmp3gui.LST1.clearSelection();
                SJmp3gui.LST2.clearSelection();                
                SJmp3gui.LST3.clearSelection();
                SJmp3gui.LST4.clearSelection(); 
                SJmp3gui.LST5.clearSelection();
                SJmp3gui.LST6.clearSelection();                
                break;                
        }        
     try {
        Actions.SPT=0;
        Actions.currentFrame=0;
        Actions.ClosePlaySliderThreads();
        Actions.PlayFlag=false;
        Actions.NextFlag=false;
        if (Actions.ST!=null) {Actions.ST.stop(); }
        Actions.LSTindex=SJmp3gui.currentLST.getSelectedIndex();
        System.out.println(Actions.LSTindex);
        LoadFileMp3.putf=Actions.currentList3m.get(Actions.LSTindex);
        System.out.println(LoadFileMp3.putf);
        System.out.println("LSTindex = "+Actions.LSTindex);
        }
      catch (ArrayIndexOutOfBoundsException e) 
        {
         JOptionPane.showMessageDialog(SJmp3gui.frame,"FileNotFound !","Error !",JOptionPane.ERROR_MESSAGE);
        }
      catch (NullPointerException e) {};                
    }

    public static void DefCfg () {
        Actions.currentLAF="org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel";
        Actions.currentM3u="";
        Actions.currentMixer=100;
        Actions.currentMute="false";        
        for (int j=0; j<7; j++) { Actions.M3uArray[j]=""; Actions.DirArray[j]="";}  
        SJmp3gui.currentLST=SJmp3gui.LST1; 
        Actions.currentList3m=Actions.list3m1; 
        Actions.currentBuferList=Actions.BuferList1; 
        Actions.currentPlayer=0;
        Actions.useProxy="false"; 
        Actions.proxyPort="3128";
        Actions.proxyIP="127.0.0.1";
        Actions.proxyLogin="";
        Actions.proxyPassw="";
        Actions.currentMode="classic";
        Actions.useEffects="false";
    }

    public static void LoadList3m () {
        switch (Actions.currentPlayer) {
            case 0: { SJmp3gui.currentLST=SJmp3gui.LST1; Actions.currentList3m=Actions.list3m1; Actions.currentBuferList=Actions.BuferList1; Actions.currentM3u=Actions.M3uArray[0]; Actions.currentDir=Actions.DirArray[0];} break;
            case 1: { SJmp3gui.currentLST=SJmp3gui.LST2; Actions.currentList3m=Actions.list3m2; Actions.currentBuferList=Actions.BuferList2; Actions.currentM3u=Actions.M3uArray[1]; Actions.currentDir=Actions.DirArray[1];} break;
            case 2: { SJmp3gui.currentLST=SJmp3gui.LST3; Actions.currentList3m=Actions.list3m3; Actions.currentBuferList=Actions.BuferList3; Actions.currentM3u=Actions.M3uArray[2]; Actions.currentDir=Actions.DirArray[2];} break;  
            case 3: { SJmp3gui.currentLST=SJmp3gui.LST4; Actions.currentList3m=Actions.list3m4; Actions.currentBuferList=Actions.BuferList4; Actions.currentM3u=Actions.M3uArray[3]; Actions.currentDir=Actions.DirArray[3];} break; 
            case 4: { SJmp3gui.currentLST=SJmp3gui.LST5; Actions.currentList3m=Actions.list3m5; Actions.currentBuferList=Actions.BuferList5; Actions.currentM3u=Actions.M3uArray[4]; Actions.currentDir=Actions.DirArray[4];} break;   
            case 5: { SJmp3gui.currentLST=SJmp3gui.LST6; Actions.currentList3m=Actions.list3m6; Actions.currentBuferList=Actions.BuferList6; Actions.currentM3u=Actions.M3uArray[5]; Actions.currentDir=Actions.DirArray[5];} break; 
            case 6: { SJmp3gui.currentLST=SJmp3gui.LST7; Actions.currentList3m=Actions.list3m7; Actions.currentBuferList=Actions.BuferList7; Actions.currentM3u=Actions.M3uArray[6]; Actions.currentDir=Actions.DirArray[6];} break;             
        }  
        String bufer="",txt=""; 
        try 
        {
        for (int q=0; q<7; q++)
        {
        if (!Actions.M3uArray[q].isEmpty()) 
         {
          Actions.DirArray[q]="";   
          FileInputStream fis = new FileInputStream(Actions.M3uArray[q]);
          BufferedReader  br=new BufferedReader(new InputStreamReader(fis));
          int j=0;
          do
          {
           bufer=""; txt="";   
           if (br.ready()) {bufer=br.readLine();}
           if (txt.equals(bufer)) {break;} 
           if (!bufer.startsWith("#EXT")) 
              {
                switch (q) {  
                    case 0: Actions.list3m1.add(bufer);break;
                    case 1: Actions.list3m2.add(bufer);break;
                    case 2: Actions.list3m3.add(bufer);break;
                    case 3: Actions.list3m4.add(bufer);break;
                    case 4: Actions.list3m5.add(bufer);break;
                    case 5: Actions.list3m6.add(bufer);break;
                    case 6: Actions.list3m7.add(bufer);break;                    
                }        
                j++;
               }//if
           txt=bufer;
           }
          while (true);
          br.close();
          fis.close();
          Actions.List3mToBuferList();
          switch (q) {  
            case 0: SJmp3gui.LST1.setListData(Actions.BuferList1);break;
            case 1: SJmp3gui.LST2.setListData(Actions.BuferList2);break;
            case 2: SJmp3gui.LST3.setListData(Actions.BuferList3);break;
            case 3: SJmp3gui.LST4.setListData(Actions.BuferList4);break;
            case 4: SJmp3gui.LST5.setListData(Actions.BuferList5);break;
            case 5: SJmp3gui.LST6.setListData(Actions.BuferList6);break;
            case 6: SJmp3gui.LST7.setListData(Actions.BuferList7);break;            
           }                
         }
        else 
         {
           if (!Actions.DirArray[q].isEmpty()) 
           {
             try
              {
                frolovDirList (Actions.DirArray[q],q); 
              }
             catch (NullPointerException nn) 
              {
                Actions.DirArray[q]="";
              }
           }
         }
        }
        SJmp3gui.currentLST.setListData(Actions.currentBuferList);        
        }
        catch (IOException e) {} 
        
    }  

    public static void frolovDirList (String ss,int qq) {
            File f = new File(ss);
            String[] sDirList = f.list();
            int i;
            for(i = 0; i < sDirList.length; i++)
            {
            File f1 = new File(ss + File.separator + sDirList[i]);
            if(f1.isFile()) 
             {
                System.out.println(ss + File.separator + sDirList[i]);
                if (extFileCheck(f1)) 
                {    
                   switch (qq) {  
                    case 0: Actions.list3m1.add(ss + File.separator + sDirList[i]);break;
                    case 1: Actions.list3m2.add(ss + File.separator + sDirList[i]);break;
                    case 2: Actions.list3m3.add(ss + File.separator + sDirList[i]);break;
                    case 3: Actions.list3m4.add(ss + File.separator + sDirList[i]);break;
                    case 4: Actions.list3m5.add(ss + File.separator + sDirList[i]);break;
                    case 5: Actions.list3m6.add(ss + File.separator + sDirList[i]);break;
                    case 6: Actions.list3m7.add(ss + File.separator + sDirList[i]);break;                    
                   }                     
                }
             }
            else  
             {
                frolovDirList(ss + File.separator + sDirList[i],qq);
             }     
            }
            Actions.List3mToBuferList();
            switch (qq) {  
              case 0: SJmp3gui.LST1.setListData(Actions.BuferList1);break;
              case 1: SJmp3gui.LST2.setListData(Actions.BuferList2);break;
              case 2: SJmp3gui.LST3.setListData(Actions.BuferList3);break;
              case 3: SJmp3gui.LST4.setListData(Actions.BuferList4);break;
              case 4: SJmp3gui.LST5.setListData(Actions.BuferList5);break;
              case 5: SJmp3gui.LST6.setListData(Actions.BuferList6);break;
              case 6: SJmp3gui.LST7.setListData(Actions.BuferList7);break;              
             }                 
    }

    public static void CopyToClipBoard (String cps) {
        ClipboardTextTransfer textTransfer = new ClipboardTextTransfer();
        textTransfer.setClipboardContents(cps);          
    }

    public static String PasteFromClipBoard () {
        ClipboardTextTransfer textTransfer = new ClipboardTextTransfer();
        return textTransfer.getClipboardContents();          
    } 

    public static void CopyPathURLtoClipboard () {
       try {
            Actions.CopyToClipBoard(Actions.currentList3m.get(SJmp3gui.currentLST.getSelectedIndex()));
       } catch (ArrayIndexOutOfBoundsException aa) {}        
    }

    public static void PastePathURLfromClipboard () {
        String bufer=Actions.PasteFromClipBoard();
        if (extStringCheck(bufer))
          {
            Actions.currentList3m.add(bufer);
            Actions.List3mToBuferList();
            SJmp3gui.currentLST.setListData(Actions.currentBuferList);        
          }
        else 
          {
            JOptionPane.showMessageDialog(SJmp3gui.frame,"Not Support File/URL ! = "+bufer ,"Error !",JOptionPane.ERROR_MESSAGE);
          }
    }

    public static void MyInstLF(String lf) {
        //UIManager.installLookAndFeel(lf,lf);  
        SJmp3gui.lookAndFeelsDisplay.add(lf);
        SJmp3gui.lookAndFeelsRealNames.add(lf);        
    }

    public static void InstallLF () {
        //MyInstLF("javax.swing.plaf.metal.MetalLookAndFeel");
        //MyInstLF("net.beeger.squareness.SquarenessLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel");                  
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceCeruleanLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel");
        //MyInstLF("org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel");
        //MyInstLF("org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel");
        MyInstLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
        //MyInstLF("com.lipstikLF.LipstikLookAndFeel");  
        //MyInstLF("com.pagosoft.plaf.PgsLookAndFeel");
        //MyInstLF("ch.randelshofer.quaqua.QuaquaLookAndFeel");
        //MyInstLF("org.fife.plaf.OfficeXP.OfficeXPLookAndFeel");
        //MyInstLF("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
        //MyInstLF("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        //MyInstLF("com.jtattoo.plaf.noire.NoireLookAndFeel");
        //MyInstLF("joxy.JoxyLookAndFeel");
        //MyInstLF("com.birosoft.liquid.LiquidLookAndFeel");
    }

    public static void showClipboard () {
        ShowClipBoard sc=new ShowClipBoard(frame,true);
        sc.ClipboardText.setText(Actions.PasteFromClipBoard());
        sc.setVisible(true);        
    }

    public static void deleteTrackFromFS (String path) {
        int reply = JOptionPane.showConfirmDialog(frame, "Delete track\n" +LoadFileMp3.putf+ "\nfrom FileSystem ?","Delete track from FS", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                FileWorker.delete(path);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
            }
            RemoveSong();
        }
        else {        }        
    }
    
    public static void enableEffects () {
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.ARM); 
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.FOCUS);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.FOCUS_LOOP_ANIMATION);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.GHOSTING_BUTTON_PRESS);    
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.GHOSTING_ICON_ROLLOVER);    
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.ICON_GLOW);   
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.PRESS);  
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.ROLLOVER);    
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.SELECTION); 
        //AnimationConfigurationManager.getInstance().setTimelineDuration(500);        
    }
    public static void disableEffects () {
        AnimationConfigurationManager.getInstance().disallowAnimations(AnimationFacet.ARM); 
        AnimationConfigurationManager.getInstance().disallowAnimations(AnimationFacet.FOCUS);
        AnimationConfigurationManager.getInstance().disallowAnimations(AnimationFacet.FOCUS_LOOP_ANIMATION);
        AnimationConfigurationManager.getInstance().disallowAnimations(AnimationFacet.GHOSTING_BUTTON_PRESS);    
        AnimationConfigurationManager.getInstance().disallowAnimations(AnimationFacet.GHOSTING_ICON_ROLLOVER);    
        AnimationConfigurationManager.getInstance().disallowAnimations(AnimationFacet.ICON_GLOW);   
        AnimationConfigurationManager.getInstance().disallowAnimations(AnimationFacet.PRESS);  
        AnimationConfigurationManager.getInstance().disallowAnimations(AnimationFacet.ROLLOVER);    
        //AnimationConfigurationManager.getInstance().disallowAnimations(AnimationFacet.SELECTION); 
        //AnimationConfigurationManager.getInstance().setTimelineDuration(500);        
    }  
    
    public static void NetStreamGuiOn () {
        SJmp3gui.TimeTFend.setText(Actions.TimeShow(Actions.duration)); 
        SJmp3gui.SongSlider.setEnabled(true);
        SJmp3gui.TimeTFend.setEnabled(true);
        SJmp3gui.bFastBackward.setEnabled(true);
        SJmp3gui.bFastForward.setEnabled(true); 
        //SJmp3gui.bPause.setEnabled(true);
        SJmp3gui.mFastBackward.setEnabled(true);
        SJmp3gui.mFastForward.setEnabled(true); 
        //SJmp3gui.mPause.setEnabled(true);
        SJmp3gui.mpFastBackward.setEnabled(true);
        SJmp3gui.mpFastForward.setEnabled(true); 
        //SJmp3gui.mpPause.setEnabled(true);             
        //SJmp3gui.bid3Info.setEnabled(true);                     
    }
    
    public static void NetStreamGuiOff () {    
        SJmp3gui.SongSlider.setEnabled(false);
        SJmp3gui.TimeTFend.setEnabled(false);
        SJmp3gui.TimeTFend.setText("stream");  
        SJmp3gui.bFastBackward.setEnabled(false);
        SJmp3gui.bFastForward.setEnabled(false); 
        SJmp3gui.mFastBackward.setEnabled(false);
        SJmp3gui.mFastForward.setEnabled(false); 
        SJmp3gui.mpFastBackward.setEnabled(false);
        SJmp3gui.mpFastForward.setEnabled(false);         
    }
    
    public static Boolean extFileCheck (File f1) {
        String buf=f1.getName().toLowerCase();
        if (buf.endsWith(".mp1")||buf.endsWith(".mp2")||buf.endsWith(".mp3")||buf.endsWith(".mpga")||buf.endsWith(".wav")||buf.endsWith(".au")||buf.endsWith(".aif")||buf.endsWith(".aiff")||buf.endsWith(".aifc")||buf.endsWith(".snd")||buf.endsWith(".flac")||buf.endsWith(".mid")) 
            return true;
        else 
            return false;
    }
    
    public static Boolean extStringCheck (String bufer) {
        String buf=bufer.toLowerCase();
        if (buf.endsWith(".mp1")||buf.endsWith(".mp2")||buf.endsWith(".mp3")||buf.endsWith(".mpga")||buf.endsWith(".wav")||buf.endsWith(".au")||buf.endsWith(".aif")||buf.endsWith(".aiff")||buf.endsWith(".aifc")||buf.endsWith(".snd")||buf.endsWith(".flac")||buf.endsWith(".mid")||buf.startsWith("http://")||buf.startsWith("ftp://")||buf.startsWith("https://"))    
            return true;
        else 
            return false;
    }
    
    public static void WrongPathUrlException (String mes) {
        Actions.ST.stop();
        Actions.PL=null;            
        JOptionPane.showMessageDialog(SJmp3gui.frame,mes,"Error !",JOptionPane.ERROR_MESSAGE);        
    }
    
    public static void ZeroAudioInfo () {
        Actions.date="";
        Actions.title="";
        Actions.channels=0;
        Actions.bitrate=0;
        Actions.freq=0;
        Actions.sizeFile=0;
        Actions.sizeFrame=0;            
        Actions.frames=0;
        Actions.author="";
        Actions.encoding="";   
        Actions.layer="";
        Actions.mpeg="";
        Actions.track=""; 
        Actions.genre="";               
        Actions.album="";
        Actions.vbr=false;
        Actions.crc=false;
        Actions.duration=0;
        Actions.fps=0; 
        Actions.sizeSampleBit=0;                
    }
}

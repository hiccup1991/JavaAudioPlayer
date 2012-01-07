package SJmp3.Cfg;
import SJmp3.Actions;
import SJmp3.SJmp3gui;
import com.romanenco.configloader.ConfigLoader;
import java.io.*;
public class AppCfgXerces {
  // ПОДДЕРЖИВАЕТ ВЛОЖЕННЫЕ ТЕГИ
  public static FileWriter writeCFG = null;

  public static void Load () {
    ConfigLoader cfg=new ConfigLoader("org.apache.xerces.parsers.SAXParser");
    try {
        cfg.LoadFromFile("cfg/sjmp3_xerces.xml");
        Actions.currentFrame=0; 
        Actions.currentLAF=cfg.getTagValue("sjmp3.config.skin.laf");
        Actions.currentTheme=cfg.getTagValue("sjmp3.config.skin.theme");
        Actions.useEffects=cfg.getTagValue("sjmp3.config.skin.effects");        
        Actions.currentMute=cfg.getTagValue("sjmp3.config.mute");  
        Actions.currentMode=cfg.getTagValue("sjmp3.config.mode");        
        ////////////
        Actions.currentMixer=Integer.parseInt(cfg.getTagValue("sjmp3.config.mixer"));         
        Actions.currentPlayer=Integer.parseInt(cfg.getTagValue("sjmp3.config.player")); 
        SJmp3gui.tabbedPane.setSelectedIndex(Actions.currentPlayer);
        for (int j=0; j<7; j++) 
        {
            Actions.M3uArray[j]=cfg.getTagValue("sjmp3.config.m3u.list"+j);
            Actions.DirArray[j]=cfg.getTagValue("sjmp3.config.dir.folder"+j);            
            if (j==Actions.currentPlayer) Actions.currentM3u=Actions.M3uArray[j];
            if (j==Actions.currentPlayer) Actions.currentDir=Actions.DirArray[j];            
            System.out.println(Actions.M3uArray[j]);
        }
        Actions.proxyIP=cfg.getTagValue("sjmp3.config.proxy.ip");
        Actions.proxyPort=cfg.getTagValue("sjmp3.config.proxy.port"); 
        Actions.proxyPassw=cfg.getTagValue("sjmp3.config.proxy.passw"); 
        Actions.proxyLogin=cfg.getTagValue("sjmp3.config.proxy.login");  
        Actions.useProxy=cfg.getTagValue("sjmp3.config.proxy.use");        
        Actions.LoadList3m();
    }
    //catch (IOException ex)          {  Actions.DefCfg ();  System.out.println(ex); } 
    catch (NullPointerException e)  {  Actions.DefCfg ();  System.out.println(e); }
    catch (RuntimeException e)      {  Actions.DefCfg ();  System.out.println(e); }
  }
 public static void Save () {
    try {
            File cfgdir = new File("cfg");
            cfgdir.mkdir();
            //cfgfile.mkdirs();            
            File cfgfile = new File("cfg/sjmp3_xerces.xml");
            writeCFG = new FileWriter(cfgfile);
            writeCFG.append("<sjmp3>\n");            
            writeCFG.append("  <config>\n");
            writeCFG.append("   <skin>\n");            
            writeCFG.append("     <laf>"+Actions.currentLAF+"</laf>\n");
            writeCFG.append("     <theme>"+Actions.currentTheme+"</theme>\n"); 
            writeCFG.append("     <effects>"+Actions.useEffects+"</effects>\n");            
            writeCFG.append("   </skin>\n");            
            writeCFG.append("   <m3u>\n");
            for (int j=0; j<7; j++)
            {
              writeCFG.append("      <list"+j+">"+Actions.M3uArray[j]+"</list"+j+">\n");                
            }
            writeCFG.append("   </m3u>\n"); 
            writeCFG.append("   <dir>\n");
            for (int j=0; j<7; j++)
            {
              writeCFG.append("      <folder"+j+">"+Actions.DirArray[j]+"</folder"+j+">\n");                
            }
            writeCFG.append("   </dir>\n");             
            writeCFG.append("   <mode>"+Actions.currentMode+"</mode>\n");
            writeCFG.append("   <proxy>\n");            
            writeCFG.append("      <ip>"+Actions.proxyIP+"</ip>\n"); 
            writeCFG.append("      <port>"+Actions.proxyPort+"</port>\n"); 
            writeCFG.append("      <login>"+Actions.proxyLogin+"</login>\n");
            writeCFG.append("      <passw>"+Actions.proxyPassw+"</passw>\n");
            writeCFG.append("      <use>"+Actions.useProxy+"</use>\n");            
            writeCFG.append("   </proxy>\n");            
            writeCFG.append("   <mixer>"+Actions.currentMixer+"</mixer>\n"); 
            writeCFG.append("   <mute>"+Actions.currentMute+"</mute>\n");            
            writeCFG.append("   <player>"+Actions.currentPlayer+"</player>\n");            
            writeCFG.append("  </config>\n");
            writeCFG.append("</sjmp3>\n");            
        } 
    catch (IOException ex) {  ex.printStackTrace();  }
    finally 
            {
                if(writeCFG != null) 
                {
                 try { writeCFG.close(); } 
                 catch (IOException e) { e.printStackTrace(); }
                }
            }            
    }
}

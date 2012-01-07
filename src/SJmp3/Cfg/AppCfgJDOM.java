package SJmp3.Cfg;
import SJmp3.Actions;
import SJmp3.SJmp3gui;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ContentFilter;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class AppCfgJDOM {

    public static void saveCFG () {
        Document xmlDoc = new Document();
        Element sjmp3 = new Element("sjmp3");
            xmlDoc.setRootElement(sjmp3);
        Element config = new Element("config");
            sjmp3.addContent(config);            
        Element skin = new Element("skin");
            config.addContent(skin);
        Element laf = new Element("laf");    
            laf.addContent(Actions.currentLAF);     
            skin.addContent(laf);
        Element theme = new Element("theme");    
            theme.addContent(Actions.currentTheme);     
            skin.addContent(theme); 
        Element effects = new Element("effects");    
            effects.addContent(Actions.useEffects);     
            skin.addContent(effects);            
        Element mixer = new Element("mixer");
            mixer.addContent(""+Actions.currentMixer); 
            config.addContent(mixer);  
        Element mute = new Element("mute");
            mute.addContent(Actions.currentMute); 
            config.addContent(mute);            
        Element player = new Element("player");
            player.addContent(""+Actions.currentPlayer);     
            config.addContent(player);            
            //////////////////////////
        Element mode = new Element("mode");
            mode.addContent(Actions.currentMode);         
            config.addContent(mode);            
            //////////////////////////
        Element m3u = new Element("m3u");
            config.addContent(m3u); 
        Element list1 = new Element("list0");
            list1.addContent(Actions.M3uArray[0]);       
            m3u.addContent(list1); 
        Element list2 = new Element("list1");
            list2.addContent(Actions.M3uArray[1]);       
            m3u.addContent(list2);            
        Element list3 = new Element("list2");
            list3.addContent(Actions.M3uArray[2]);       
            m3u.addContent(list3);  
        Element list4 = new Element("list3");
            list4.addContent(Actions.M3uArray[3]);       
            m3u.addContent(list4);   
        Element list5 = new Element("list4");
            list5.addContent(Actions.M3uArray[4]);       
            m3u.addContent(list5);  
        Element list6 = new Element("list5");
            list6.addContent(Actions.M3uArray[5]);       
            m3u.addContent(list6);   
        Element list7 = new Element("list6");
            list7.addContent(Actions.M3uArray[6]);       
            m3u.addContent(list7);             
            //////////////////////
        Element dir = new Element("dir");
            config.addContent(dir); 
        Element folder1 = new Element("folder0");
            folder1.addContent(Actions.DirArray[0]);       
            dir.addContent(folder1); 
        Element folder2 = new Element("folder1");
            folder2.addContent(Actions.DirArray[1]);       
            dir.addContent(folder2);            
        Element folder3 = new Element("folder2");
            folder3.addContent(Actions.DirArray[2]);       
            dir.addContent(folder3);  
        Element folder4 = new Element("folder3");
            folder4.addContent(Actions.DirArray[3]);       
            dir.addContent(folder4);   
        Element folder5 = new Element("folder4");
            folder5.addContent(Actions.DirArray[4]);       
            dir.addContent(folder5);  
        Element folder6 = new Element("folder5");
            folder6.addContent(Actions.DirArray[5]);       
            dir.addContent(folder6);   
        Element folder7 = new Element("folder6");
            folder7.addContent(Actions.DirArray[6]);       
            dir.addContent(folder7);            
            //////////////////////            
        Element proxy = new Element("proxy");
            config.addContent(proxy); 
        Element ip = new Element("ip");
            ip.addContent(Actions.proxyIP);       
            proxy.addContent(ip);  
        Element port = new Element("port");
            port.addContent(Actions.proxyPort);       
            proxy.addContent(port); 
        Element login = new Element("login");
            login.addContent(Actions.proxyLogin);       
            proxy.addContent(login); 
        Element passw = new Element("passw");
            passw.addContent(Actions.proxyPassw);       
            proxy.addContent(passw); 
        Element use = new Element("use");
            use.addContent(Actions.useProxy);       
            proxy.addContent(use);            
            /////////////////////
        try {
            Format fmt = Format.getPrettyFormat();
            // Выводим созданный XML как поток байт на стандартный
            // вывод и в файл, используя подготовленный формат
            XMLOutputter serializer = new XMLOutputter(fmt);
            serializer.output(xmlDoc, System.out);
            File cfgdir = new File("cfg");
            cfgdir.mkdir();
            serializer.output(xmlDoc, new FileOutputStream(new File("cfg/sjmp3_jdom.xml")));
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void loadCFG () {
      SAXBuilder parser = new SAXBuilder();
      Document xmlDoc;
      try {
        xmlDoc = parser.build(new File("cfg/sjmp3_jdom.xml"));
        // Получаем список всех элементов head, которые
        // содержит корневой элемент
        List elements = xmlDoc.getRootElement().getContent(new ElementFilter("config"));
        System.out.println(elements);
        // Для каждого элемента config получаем значение 
        // вложенных элементов
        Iterator iterator = elements.iterator();
        while(iterator.hasNext())
            {
             Element config = (Element)iterator.next();
             Actions.currentLAF = config.getChild("skin").getChildText("laf");
             Actions.currentTheme = config.getChild("skin").getChildText("theme"); 
             Actions.useEffects = config.getChild("skin").getChildText("effects");             
             Actions.currentMute = config.getChildText("mute");
             Actions.currentMode = config.getChildText("mode");             
             Actions.currentMixer = Integer.parseInt(config.getChildText("mixer")); 
             Actions.currentPlayer = Integer.parseInt(config.getChildText("player"));
             SJmp3gui.tabbedPane.setSelectedIndex(Actions.currentPlayer);             
             ///////////////////
             for (int j=0; j<7; j++) 
                {
                Actions.M3uArray[j]=config.getChild("m3u").getChildText("list"+j);
                Actions.DirArray[j]=config.getChild("dir").getChildText("folder"+j);
                if (j==Actions.currentPlayer) Actions.currentM3u=Actions.M3uArray[j];
                if (j==Actions.currentPlayer) Actions.currentDir=Actions.DirArray[j];                
                System.out.println(Actions.M3uArray[j]);
                }  
             ///////////////////
             Actions.proxyIP = config.getChild("proxy").getChildText("ip");
             Actions.proxyPort = config.getChild("proxy").getChildText("port");
             Actions.proxyPassw = config.getChild("proxy").getChildText("passw");
             Actions.proxyLogin = config.getChild("proxy").getChildText("login");  
             Actions.useProxy = config.getChild("proxy").getChildText("use");
             ////////////////////             
            } // while
        Actions.currentFrame=0;         
        // Получаем все комментарии в документе и выводим для
        // каждого его значение и имя элемента, который содержит
        // этот комментарий
        iterator = xmlDoc.getDescendants(new ContentFilter(ContentFilter.COMMENT));
        while(iterator.hasNext()){
            Content comment = (Content)iterator.next();
            System.out.println(comment.getParentElement().getName()+": "+ comment.getValue());
        }
        Actions.LoadList3m();
      } 
      catch (JDOMException e) { Actions.DefCfg (); e.printStackTrace();  } 
      catch (IOException e)   { Actions.DefCfg (); e.printStackTrace();  }
  }
}

package SJmp3.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MakeJulCfgProp {
  public static FileWriter writeCFG = null;    
  public static void Make () {
    try {
            File cfgdir = new File("cfg");
            cfgdir.mkdir();
            File logdir = new File("log");
            logdir.mkdir();            
            //cfgfile.mkdirs();            
            File cfgfile = new File("cfg/jul.properties");
            writeCFG = new FileWriter(cfgfile);
            writeCFG.append("handlers=java.util.logging.FileHandler, java.util.logging.ConsoleHandler\n");
            //, java.util.logging.SocketHandler, com.agafua.syslog.SyslogHandler\n");
            writeCFG.append(".level=INFO\n");
            writeCFG.append("############\n");
            writeCFG.append("java.util.logging.FileHandler.level=INFO\n");
            writeCFG.append("java.util.logging.FileHandler.pattern = log/app.log\n");
            writeCFG.append("java.util.logging.FileHandler.limit = 999000\n");
            writeCFG.append("java.util.logging.FileHandler.count = 3\n");
            writeCFG.append("java.util.logging.FileHandler.append = true\n");
            writeCFG.append("java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter\n");
            writeCFG.append("############\n");
            writeCFG.append("java.util.logging.ConsoleHandler.level =INFO\n");
            writeCFG.append("java.util.logging.ConsoleHandler.formatter =java.util.logging.SimpleFormatter\n");
            writeCFG.append("#############\n");
            //writeCFG.append("java.util.logging.SocketHandler.level     = INFO\n");
            //writeCFG.append("java.util.logging.SocketHandler.filter    =\n");
            //writeCFG.append("java.util.logging.SocketHandler.formatter =java.util.logging.SimpleFormatter\n");
            //writeCFG.append("java.util.logging.SocketHandler.encoding  =\n");
            //writeCFG.append("java.util.logging.SocketHandler.host      =127.0.0.1\n");
            //writeCFG.append("java.util.logging.SocketHandler.port      =22\n");
            //writeCFG.append("################\n");
            //writeCFG.append("com.agafua.syslog.SyslogHandler.transport = udp\n");
            //writeCFG.append("com.agafua.syslog.SyslogHandler.facility = local4\n");
            //writeCFG.append("com.agafua.syslog.SyslogHandler.port = 514\n");
            //writeCFG.append("com.agafua.syslog.SyslogHandler.hostname = 127.0.0.1\n");            
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

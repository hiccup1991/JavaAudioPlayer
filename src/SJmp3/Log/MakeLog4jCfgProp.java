package SJmp3.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MakeLog4jCfgProp {
  public static FileWriter writeCFG = null;    
  public static void Make () {
    try {
            File cfgdir = new File("cfg");
            cfgdir.mkdir();
            File logdir = new File("log");
            logdir.mkdir();            
            //cfgfile.mkdirs();            
            File cfgfile = new File("cfg/log4j.properties");
            writeCFG = new FileWriter(cfgfile);
            writeCFG.append("log4j.debug=false\n");  
            writeCFG.append("log4j.rootLogger=INFO, FILE, CONSOLE\n");
            // SYSLOG,
            writeCFG.append("############## \n");
            writeCFG.append("log4j.appender.FILE=org.apache.log4j.RollingFileAppender\n");
            writeCFG.append("log4j.appender.FILE.File=log/app.log\n");
            writeCFG.append("log4j.appender.FILE.maxFileSize=999KB\n");
            writeCFG.append("log4j.appender.FILE.maxBackupIndex=1\n");
            writeCFG.append("log4j.appender.FILE.layout=org.apache.log4j.PatternLayout\n");
            writeCFG.append("log4j.appender.FILE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n\n");
            writeCFG.append("#############\n");
            //writeCFG.append("log4j.appender.SYSLOG=org.apache.log4j.net.SyslogAppender\n");
            //writeCFG.append("log4j.appender.SYSLOG.syslogHost=127.0.0.1\n");
            //writeCFG.append("log4j.appender.SYSLOG.layout=org.apache.log4j.PatternLayout\n");
            //writeCFG.append("log4j.appender.SYSLOG.layout.conversionPattern=%d{ISO8601} %-5p [%t] %c{2} %x - %m%n\n");
            //writeCFG.append("log4j.appender.SYSLOG.Facility=LOCAL4\n");
            writeCFG.append("###############\n");
            writeCFG.append("log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender\n");
            writeCFG.append("log4j.appender.CONSOLE.Encoding=utf-8\n");
            writeCFG.append("log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout\n");
            writeCFG.append("log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n\n");
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

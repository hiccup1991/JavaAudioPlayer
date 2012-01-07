package SJmp3.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MakeLog4jCfgXml {
  public static FileWriter writeCFG = null;    
  public static void Make () {
    try {
            File cfgdir = new File("cfg");
            cfgdir.mkdir();
            File logdir = new File("log");
            logdir.mkdir();            
            //cfgfile.mkdirs();            
            File cfgfile = new File("cfg/log4j.xml");
            writeCFG = new FileWriter(cfgfile);
writeCFG.append(" <!DOCTYPE log4j:configuration PUBLIC\n");
writeCFG.append("         \"-//APACHE//DTD LOG4J 1.2//EN\"\n");
writeCFG.append("         \"http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/xml/doc-files/log4j.dtd\">\n");
writeCFG.append(" <log4j:configuration debug=\"false\">\n");
writeCFG.append("     <appender name=\"CONSOLE\" class=\"org.apache.log4j.ConsoleAppender\">\n");
writeCFG.append("         <param name=\"Encoding\" value=\"utf-8\"/>\n");
writeCFG.append("         <layout class=\"org.apache.log4j.PatternLayout\">\n");
writeCFG.append("             <param name=\"ConversionPattern\" value=\"%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n\"/>\n");
writeCFG.append("         </layout>\n");
writeCFG.append("     </appender>\n");
writeCFG.append("     <appender name=\"FILE\" class=\"org.apache.log4j.RollingFileAppender\">\n");
writeCFG.append("         <param name=\"File\" value=\"log/app.log\"/>\n");
writeCFG.append("         <param name=\"maxFileSize\" value=\"999KB\"/>\n");
writeCFG.append("         <param name=\"maxBackupIndex\" value=\"1\"/>\n");
writeCFG.append("         <param name=\"Encoding\" value=\"utf-8\"/>\n");
writeCFG.append("         <layout class=\"org.apache.log4j.PatternLayout\">\n");
writeCFG.append("             <param name=\"ConversionPattern\" value=\"%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n\"/>\n");
writeCFG.append("         </layout>\n");
writeCFG.append("     </appender>\n");
//writeCFG.append("     <appender name=\"SYSLOG\" class=\"org.apache.log4j.net.SyslogAppender\">\n");
//writeCFG.append("         <param name=\"syslogHost\" value=\"127.0.0.1\"/>\n");
//writeCFG.append("         <param name=\"Facility\" value=\"LOCAL4\"/>\n");
//writeCFG.append("         <layout class=\"org.apache.log4j.PatternLayout\">\n");
//writeCFG.append("             <param name=\"ConversionPattern\" value=\"%d{ISO8601} %-5p [%t] %c{2} %x - %m%n\"/>\n");
//writeCFG.append("         </layout>\n");
//writeCFG.append("     </appender>\n");
writeCFG.append("     <root>\n");
writeCFG.append("         <priority value=\"INFO\"/>\n");
writeCFG.append("         <appender-ref ref=\"CONSOLE\"/>\n");
writeCFG.append("         <appender-ref ref=\"FILE\"/>\n");
//writeCFG.append("         <appender-ref ref=\"SYSLOG\"/>\n");
writeCFG.append("     </root>\n");
writeCFG.append(" </log4j:configuration>\n");
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

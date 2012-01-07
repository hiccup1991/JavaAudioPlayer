package SJmp3.Threads;
import SJmp3.Actions;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
public class Next_Thread extends Thread {
    public Next_Thread () {  start();  }
    private static Logger julLog = Logger.getLogger(Next_Thread.class.getName());
    @Override
    public void run () {
        try {
            FileInputStream configFile = new FileInputStream("cfg/jul.properties");
            LogManager.getLogManager().readConfiguration(configFile);        
            //LogManager.getLogManager().readConfiguration(Next_Thread.class.getResourceAsStream("cfg/jul.properties"));
            } 
        catch (IOException e)
            {
                System.err.println("not logger config: " + e.toString());
            }
        catch (NullPointerException ex)
            {
                System.err.println("not logger config: " + ex.toString());
            }            
        do 
        {
            try {   Thread.sleep(1000);   } 
            catch (InterruptedException ex) {
                Logger.getLogger(Next_Thread.class.getName()).log(Level.SEVERE, null, ex);
            }
        if (Actions.SPT > Actions.duration)
            { 
                Actions.PlayNext(); julLog.info("@@@@@@@@@@@ JUL - next song @@@@@@@@@@@@"); 
            }
        }
        while(true);
    }
}

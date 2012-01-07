package SJmp3.Filters;
import java.io.File;
import java.io.FilenameFilter;
public class HFileNameFilterMp3 implements FilenameFilter
 {
    @Override
    public boolean accept(File directory, String fileName) {
        String s = fileName.toLowerCase();
	if (s.endsWith(".mp3"))
                //||fileName.endsWith(".MP3")) 
        { return true; }
        return false;
	}//accept
  }//class
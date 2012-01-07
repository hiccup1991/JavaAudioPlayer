package SJmp3.Filters;
import java.io.*;
public class MFileNameFilter implements FilenameFilter{
    String ext;
    public MFileNameFilter(String ext) {
      this.ext=ext;
    }
    public boolean accept(File dir,String fileName){
       return ext==""||fileName.endsWith(ext);
    }
}

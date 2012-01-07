package SJmp3.Filters;
import java.io.File;
import javax.swing.filechooser.FileFilter;
public class HFileFilterM3u extends FileFilter {
    @Override
    public boolean accept(File f)
     {
	  if (f!=null)
	  {
		  String name=f.getName();
		  int i=name.lastIndexOf('.');
		  if(i>0 && i<name.length()-1)
		  return name.substring(i+1).equalsIgnoreCase("m3u");
	  } //if
        return true;
     }
    @Override
    public String getDescription()
     {
	  return "*.m3u";
     } 
}

package SJmp3.Filters;
import java.io.File;
public class HFileFilterMp3 extends javax.swing.filechooser.FileFilter
 {
 @Override
 public boolean accept(File f)
  {
	  if (f!=null)
	  {
		  String name=f.getName();
		  int i=name.lastIndexOf('.');
		  if(i>0 && i<name.length()-1)
		  return name.substring(i+1).equalsIgnoreCase("mp3");
	  } //if
   return true;
  }
 @Override
  public String getDescription()
  {
	  return "*.mp3";
  }
 }
    


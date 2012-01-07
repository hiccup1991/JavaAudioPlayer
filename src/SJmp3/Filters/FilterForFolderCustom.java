package SJmp3.Filters;

import java.io.File;

public class FilterForFolderCustom extends javax.swing.filechooser.FileFilter { 
 
    String ext;
    public FilterForFolderCustom(String txt){
        this.ext=txt;
    }
    public boolean accept(File f){
        if(f==null)
          return false;
        if(f.isDirectory()){
          return true ;
        }
        else
          return (f.getName().endsWith(ext)||f.getName().endsWith(ext.toUpperCase()));
      }
    public String getDescription(){
          return "Recursive Search for: "+ext;
    }
}
    


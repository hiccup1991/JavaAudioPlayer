package SJmp3.Utils.Mp3SplitterBySize;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;

public class Joiner
{
    private File firstFile;
    private int noOfFiles;
    private String path;
    private String fileNameTemplate;
    private String opSys="/";
    private boolean deleteFiles;


    public void join(File firstFileInp, boolean deleteFiles)
    {
        this.deleteFiles=deleteFiles;
        this.firstFile=firstFileInp;
        this.determineOpSys(firstFile);
        noOfFiles=getNoOfFiles(firstFile);
        File[] fileArr=new File[noOfFiles];
        File fileToCreate=new File(path+opSys+fileNameTemplate);

        int i=0;
        int bufferSize=1024*8;
        byte[] buffer=new byte[1024*8];
        try
        {
            for(i=0;i<noOfFiles;i++)
            {
                fileArr[i]=new File(path+opSys+fileNameTemplate+(i+1)+".split");
            }
            i=0;
            BufferedOutputStream oBuff=new BufferedOutputStream(new FileOutputStream(fileToCreate));
            BufferedInputStream iBuff=new BufferedInputStream(new FileInputStream(fileArr[i]));
            while(true)
            {
                if(iBuff.available()>bufferSize)
                {
                    iBuff.read(buffer);
                    oBuff.write(buffer);
                }
                else
                {
                    byte[] bufferTemp=new byte[iBuff.available()];
                    iBuff.read(bufferTemp);
                    oBuff.write(bufferTemp);
                    oBuff.flush();
                    if(deleteFiles)
                    {
                        fileArr[i].delete();
                    }
                    i++;
                    if(i==noOfFiles)
                    {
                        JOptionPane.showMessageDialog(null,"Job Completed...");
                        break;
                    }
                    else
                    {
                        iBuff = new BufferedInputStream(new FileInputStream(fileArr[i]));
                    }
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }

    }
    private int getNoOfFiles(File firstFileInp)
    {
        int noOfFilesT=1;
        try
        {
            path=firstFileInp.getAbsolutePath().substring(0,firstFileInp.getAbsolutePath().lastIndexOf(opSys));
            
            fileNameTemplate=(firstFileInp.getName()).substring(0,(firstFileInp.getName().lastIndexOf(".")-1));
            
            int i=2;
            while(true)
            {
                File temp=new File(path+opSys+fileNameTemplate+i+".split");
                
                if(temp.exists())
                {
                    noOfFilesT++;
                    i++;
                }
                else
                {
                    temp=null;
                    break;
                }
            }
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        finally
        {
            return noOfFilesT;
        }
    }
    public void determineOpSys(File temp)
    {
        if(temp.getAbsolutePath().lastIndexOf("/")!=-1)
            {
                opSys="/";
            }
            else
            {
                opSys="\\";
            }        
    }

}

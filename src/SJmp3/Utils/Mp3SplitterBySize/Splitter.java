package SJmp3.Utils.Mp3SplitterBySize;

import SJmp3.SJmp3gui;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;

public class Splitter
{
    private int noOfParts;
    private File fileToSplit;
    private String opSys;
    private long partSize;
    boolean parts;
    
    public Splitter(File fileToSplit,int noOfParts,long partSize,boolean parts)
    {
        this.noOfParts=noOfParts;
        this.fileToSplit=fileToSplit;
        this.partSize=partSize;
        this.parts=parts;
    }
    public void split()
    {
        try
        {
            long fileSize=fileToSplit.length();
            String filePath=fileToSplit.getAbsolutePath();
            String directory;
            if(filePath.lastIndexOf("/")!=-1)
            {
                directory=filePath.substring(0,filePath.lastIndexOf("/"));
                opSys="/";
            }
            else
            {
                directory=filePath.substring(0,filePath.lastIndexOf("\\"));
                opSys="\\";
            }
            String fileName=fileToSplit.getName();
            
            if(parts)
            {
                partSize=fileSize/noOfParts;
            }
            else
            {
                noOfParts=(int)(fileSize/partSize);
                noOfParts++;
            }

            File[] fileArr=new File[noOfParts];
            for(int i=0;i<noOfParts;i++)
            {
                fileArr[i]=new File(directory+opSys+fileName+(i+1)+".mp3");
                boolean success=fileArr[i].createNewFile();                
            }


            FileInputStream input=new FileInputStream(fileToSplit);
            BufferedInputStream iBuff=new BufferedInputStream(input);
            int i=0;

            FileOutputStream output=new FileOutputStream(fileArr[i]);
            BufferedOutputStream oBuff=new BufferedOutputStream(output);

            int buffSize=8*1024;
            byte[] buffer=new byte[buffSize];

            while(true)
            {
               if(iBuff.available()<buffSize)
               {
                   byte[] newBuff= new byte[iBuff.available()];
                   iBuff.read(newBuff);
                   oBuff.write(newBuff);
                   oBuff.flush();
                   oBuff.close();
                   JOptionPane.showMessageDialog(SJmp3gui.frame,"Job Completed");
                   break;
               }
               int r=iBuff.read(buffer);

               if(fileArr[i].length()>=partSize)
               {
                   oBuff.flush();
                   oBuff.close();
                   i++;
                   output=new FileOutputStream(fileArr[i]);
                   oBuff=new BufferedOutputStream(output);
               }
               oBuff.write(buffer);               
            }

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(SJmp3gui.frame,e.toString());
        }
    }

}

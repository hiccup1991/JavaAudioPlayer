package SJmp3.Utils.Mp3SplitterByTime;
public class Splitter_ExtMain extends pl.highcom.mp3splitter.Main {
    //public static pl.highcom.mp3splitter.MainFrame mf;
    public static Splitter_ExtMainFrame mf;
    public static void main(String args[]) {
        mf=new Splitter_ExtMainFrame();
        //mf=new pl.highcom.mp3splitter.MainFrame();
        mf.jbClose.setVisible(false);       
    }
}

package SJmp3.Utils.Mp3TagEdit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TagEdit_Ext extends net.sf.fixtag.app.FixTag {
    public static net.sf.fixtag.app.FixTag teFrame;
    public TagEdit_Ext(String title) throws Exception {
        super(title);
    }
    public static void main(String args[]) {
        try {
            teFrame=new net.sf.fixtag.app.FixTag("Mp3 tag Editor");
        } catch (Exception ex) {
            Logger.getLogger(TagEdit_Ext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}

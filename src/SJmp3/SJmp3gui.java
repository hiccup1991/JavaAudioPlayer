package SJmp3;
//import static SJmp3.Actions.ClosePlaySliderThreads;
//import static SJmp3.Actions.SPT;
import SJmp3.Mixer.MixerFrame;
import SJmp3.Mixer.ControlSound;
import SJmp3.Cfg.AppCfgJDOM;
import SJmp3.Cfg.AppCfgXerces;
import SJmp3.Log.MakeJulCfgProp;
import SJmp3.Log.MakeLog4jCfgProp;
import SJmp3.Log.MakeLog4jCfgXml;
import SJmp3.Cfg.ProxyCfg;
import SJmp3.LoadSave.SavePlayList;
import SJmp3.Threads.Next_Thread;
import SJmp3.LoadSave.LoadURL;
import SJmp3.ClipBoard.ShowClipBoard;
import SJmp3.LoadSave.LoadFileMp3;
//import SJmp3.Mp3Converter.EngineIO;
//import SJmp3.Mp3Converter.ExtProgRun;
import SJmp3.Utils.Mp3Converter.WavToMp3_Thread;
import SJmp3.Utils.Mp3SplitterByTime.Splitter_Thread;
import SJmp3.Utils.Mp3TagEdit.TagEdit_Thread;
import SJmp3.Utils.Mp3SplitterBySize.splitGui;
import SJmp3.Utils.SplitterSelect;
import SJmp3.Utils.wavANDflac.WFselect;
import SJmp3.Utils.wavANDflac.flac2wav.FlacToWav;
import SJmp3.video.jv_player;
import SJmp3.Utils.wavANDflac.wav2flac.WavToFlac;
import SJmp3.Utils.wavANDflac.wavIflac.WavAndFlac;
import com.lipstikLF.LipstikLookAndFeel;
import com.lipstikLF.theme.DefaultTheme;
import com.lipstikLF.theme.KlearlooksTheme;
import com.lipstikLF.theme.LightGrayTheme;
//import com.sun.javafx.geom.RoundRectangle2D;
import java.awt.Color;
//import de.muntjak.tinylookandfeel.Theme;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
// или Java-log - или log4j !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.PopupMenuUI;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.pushingpixels.lafwidget.animation.AnimationConfigurationManager;
import org.pushingpixels.lafwidget.animation.AnimationFacet;
//import ui.LameUI;
public class SJmp3gui extends javax.swing.JFrame {
    public SJmp3gui()     
        {     
            initComponents();      
            this.setTitle("Audio Player");
            LST1.setComponentPopupMenu(mPopup);
            LST2.setComponentPopupMenu(mPopup);
            LST3.setComponentPopupMenu(mPopup);
            LST4.setComponentPopupMenu(mPopup); 
            LST5.setComponentPopupMenu(mPopup);
            LST6.setComponentPopupMenu(mPopup); 
            LST7.setComponentPopupMenu(mPopup);            
            SJmp3gui.mrEmeraldDusk.setVisible(false);
            SJmp3gui.mrChallengerDeep.setVisible(false); 
            SJmp3gui.mrLiquid.setVisible(false);
            SJmp3gui.mrMetalOcean.setVisible(false);
            SJmp3gui.mrSquareness.setVisible(false);            
            SJmp3gui.mLipstik.setVisible(false);
            SJmp3gui.mVideoPlayer.setVisible(false);
            //mChangeLF.setVisible(false);
        }
    public static SJmp3gui frame;
    public static JList currentLST;
    public static List<String> lookAndFeelsDisplay = new ArrayList<>();
    public static List<String> lookAndFeelsRealNames = new ArrayList<>();    
    //public static LameUI lu;
    public JPanel contentPane;
    private static final Logger j4log = Logger.getLogger(SJmp3gui.class.getName()); 
    public ImageIcon FrameIcon = new ImageIcon(getClass().getResource("/SJmp3/img/SubFrameIcon.png"));
    public void changeLF() {
        Actions.currentTheme="";
        //for (LookAndFeelInfo each : UIManager.getInstalledLookAndFeels()) {
            //lookAndFeelsDisplay.add(each.getName());
            //lookAndFeelsRealNames.add(each.getClassName());
        //}
        String changeLook = (String) JOptionPane.showInputDialog(this, "Choose Skin Here:", "Select Skin", JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/color_swatch.png")), lookAndFeelsDisplay.toArray(), null);
        if (changeLook != null) {
            for (int a = 0; a < lookAndFeelsDisplay.size(); a++) {
                if (changeLook.equals(lookAndFeelsDisplay.get(a))) {
                        Actions.currentLAF=lookAndFeelsRealNames.get(a);
                        //UIManager.setLookAndFeel(lookAndFeelsRealNames.get(a));
                        //SwingUtilities.updateComponentTreeUI(this);
                        //this.pack();
                        if (Actions.currentLAF.equals("de.muntjak.tinylookandfeel.TinyLookAndFeel"))
                          {
                            mrTinyDefault.setSelected(true);
                            Actions.currentTheme="lib/themes/Default.theme";
                          }
                        if (Actions.currentLAF.equals("com.lipstikLF.LipstikLookAndFeel"))
                          {                        
                            //LipstikLookAndFeel.setMyCurrentTheme(new DefaultTheme());
                            Actions.currentTheme="LipstikDefault";
                            mrLipstikDefault.setSelected(true);
                          }
                        setLF(Actions.currentLAF);
                        break;
                }
            }
        }
    }    
    public void ModeSelectShuffle () {
        mLoop.setSelected(false);
        mShuffle.setSelected(true);
        mRepeat.setSelected(false);
        mClassic.setSelected(false);                        
        bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/media_playlist_shuffle.png")));
        bMode.setToolTipText("Current Mode is Shuffle (Random select)");
        Actions.currentMode="shuffle";        
    }
    public void ModeSelectLoop () {
        mLoop.setSelected(true);
        mShuffle.setSelected(false);
        mRepeat.setSelected(false);
        mClassic.setSelected(false);                        
        bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/loop-orange.png")));
        bMode.setToolTipText("Current Mode is Loop (Repeat List)");
        Actions.currentMode="loop";
    }  
    public void ModeSelectRepeat () {
        mLoop.setSelected(false);
        mShuffle.setSelected(false);
        mRepeat.setSelected(true);
        mClassic.setSelected(false);                        
        bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/One-24.png")));
        bMode.setToolTipText("Current Mode is Repeat Track");
        Actions.currentMode="repeat";
    }     
    public void ModeSelect () {
        switch (Actions.currentMode) {
            case "classic": 
                Actions.currentMode="loop";
                mLoop.setSelected(true);
                mShuffle.setSelected(false);
                mRepeat.setSelected(false);
                mClassic.setSelected(false);                        
                bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/loop-orange.png")));
                bMode.setToolTipText("Current Mode is Loop (Repeat List)");
                break;            
            case "loop":
                Actions.currentMode="shuffle";
                mLoop.setSelected(false);
                mShuffle.setSelected(true);
                mRepeat.setSelected(false);
                mClassic.setSelected(false);                        
                bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/media_playlist_shuffle.png")));
                bMode.setToolTipText("Current Mode is Shuffle (Random select)");
                break;
            case "shuffle": 
                Actions.currentMode="repeat";
                mLoop.setSelected(false);
                mShuffle.setSelected(false);
                mRepeat.setSelected(true);
                mClassic.setSelected(false);                        
                bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/One-24.png")));
                bMode.setToolTipText("Current Mode is Repeat Track");
                break;
            case "repeat": 
                Actions.currentMode="classic";
                mLoop.setSelected(false);
                mShuffle.setSelected(false);
                mRepeat.setSelected(false);
                mClassic.setSelected(true);                        
                bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/next-forward.png")));
                bMode.setToolTipText("Current Mode is No Repeat");
                break;
        }        
    }
    public void MixerInit () {
        if ((Actions.currentMixer>0)&(Actions.currentMixer<100/3))
          SJmp3gui.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/audio_volume_low.png")));   
        if ((Actions.currentMixer>100/3)&(Actions.currentMixer<200/3))
          SJmp3gui.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/audio_volume_medium.png")));
        if (Actions.currentMixer>200/3)
          SJmp3gui.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/audio_volume_high.png")));        
        if (Actions.currentMixer==0)
            SJmp3gui.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/audio_volume_muted.png")));
        if (Actions.currentMute.equals("true")) 
         {
            ControlSound.setMasterOutputMute(true);
            //mf.bMute.setSelected(true);
            System.out.println("Volume set to ZERO = 0");
            //mf.MixerFrameSlider.setEnabled(false);
            SJmp3gui.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/audio_volume_muted.png")));   
         }
        try {
            ControlSound.setMasterOutputVolume((float)Actions.currentMixer/100);
        } catch (RuntimeException rr) {System.out.println("Master output port not found");}
    }        
    private void checkPopup(MouseEvent evt) {
        //contentPane.add(mPopup, java.awt.BorderLayout.CENTER);
        if (evt.getButton() == MouseEvent.BUTTON3) {
            mPopup.show(evt.getComponent(), evt.getX(), evt.getY());
         }
    }    
    public void MixerSet () {
        MixerFrame mf=new MixerFrame(frame,true);
        mf.setLocationRelativeTo(TimeTFend);
        mf.setVisible(true);
        MixerFrame.MixerFrameSlider.setValue(Actions.currentMixer);
        MixerInit();
    }
    public void SaveConfig () {
        AppCfgXerces.Save();
        AppCfgJDOM.saveCFG();  
        MakeJulCfgProp.Make();
        MakeLog4jCfgProp.Make();
        MakeLog4jCfgXml.Make();
    }
    public void setLF (String s) {
       try {
            Actions.currentLAF=s;
            mrMetalOcean.setSelected(false);
            mrAutumn.setSelected(false);        
            mrCreme.setSelected(false);
            mrCremeCoffee.setSelected(false);            
            mrMagellan.setSelected(false);
            mrModerate.setSelected(false); 
            mrSahara.setSelected(false);   
            mrMistAqua.setSelected(false); 
            mrMistSilver.setSelected(false);            
            mrBusinessBlue.setSelected(false);  
            mrOfficeBlue2007.setSelected(false); 
            mrOfficeBlack2007.setSelected(false);  
            mrOfficeSilver2007.setSelected(false);            
            mrNebulaBrickWall.setSelected(false);
            mrDustCoffee.setSelected(false); 
            mrGemini.setSelected(false);            
            mrNebula.setSelected(false); 
            mrDust.setSelected(false); 
            mrRaven.setSelected(false);
            mrGraphiteAqua.setSelected(false); 
            mrGraphite.setSelected(false);  
            mrGraphiteGlass.setSelected(false);            
            mrBusinessBlack.setSelected(false);
            mrBusiness.setSelected(false); 
            mrMariner.setSelected(false); 
            mrCerulean.setSelected(false); 
            mrTwilight.setSelected(false); 
            mrEmeraldDusk.setSelected(false); 
            mrChallengerDeep.setSelected(false);
            mrMetalOcean.setSelected(false);
            mrTinyDefault.setSelected(false);
            mrTinyForest.setSelected(false);
            mrTinyGolden.setSelected(false);
            mrTinyPlastic.setSelected(false);
            mrTinySilver.setSelected(false);
            mrTinyCyan.setSelected(false);
            mrTinyYellow.setSelected(false);  
            mrTinyAquaMarine.setSelected(false);   
            mrTinyMagenta.setSelected(false);
            mrTinyGreen.setSelected(false);
            mrSquareness.setSelected(false);
            mrLiquid.setSelected(false);            
            mrLipstikDefault.setSelected(false);
            mrLipstikKlearlooks.setSelected(false);
            mrLipstikLightGray.setSelected(false);            
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
            if (s.contains("tinylookandfeel")) de.muntjak.tinylookandfeel.Theme.loadTheme(new File(Actions.currentTheme));
            if (s.contains("squareness")||s.contains("lipstik")||s.contains("jtattoo")) {
                javax.swing.UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                SwingUtilities.updateComponentTreeUI(this);
                this.pack();                
                }
            javax.swing.UIManager.setLookAndFeel(s);
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
            //ImageIcon icone = new ImageIcon(getClass().getResource("/SJmp3/img/USSR-16.png"));
            this.setIconImage(FrameIcon.getImage());            
            if (s.contains("substance")) 
                { mSubstance.setText("Substance Selected"); Actions.currentTheme="";}
                else mSubstance.setText("Substance");
            if (s.contains("SubstanceOffice")) mOfficeSkins.setText("Office Skins Selected");
                else mOfficeSkins.setText("Office Skins");
            if (s.contains("SubstanceCreme")) mCremeSkins.setText("Creme Skins Selected");
                else mCremeSkins.setText("Creme Skins");
            if (s.contains("SubstanceMist")) mMistSkins.setText("Mist Skins Selected");
                else mMistSkins.setText("Mist Skins"); 
            if (s.contains("SubstanceDust")) mDustSkins.setText("Dust Skins Selected");
                else mDustSkins.setText("Dust Skins"); 
            if (s.contains("SubstanceNebula")) mNebulaSkins.setText("Nebula Skins Selected");
                else mNebulaSkins.setText("Nebula Skins");  
            if (s.contains("SubstanceBusiness")) mBusinessSkins.setText("Business Skins Selected");
                else mBusinessSkins.setText("Business Skins");  
            if (s.contains("SubstanceGraphite")) mGraphiteSkins.setText("Graphite Skins Selected");
                else mGraphiteSkins.setText("Graphite Skins");            
            if (s.contains("tinylookandfeel")) mTiny.setText("Tiny Selected ");
                else mTiny.setText("Tiny");
            if (s.contains("lipstik")) mLipstik.setText("Lipstik Selected ");
                else mLipstik.setText("Lipstik");            
            switch (Actions.currentLAF) {
                case "javax.swing.plaf.metal.MetalLookAndFeel": SJmp3gui.mrMetalOcean.setSelected(true); break; 
                case "net.beeger.squareness.SquarenessLookAndFeel": SJmp3gui.mrSquareness.setSelected(true); Actions.currentTheme=""; break;
                case "com.birosoft.liquid.LiquidLookAndFeel": SJmp3gui.mrLiquid.setSelected(true); Actions.currentTheme=""; break;
                case "org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel": SJmp3gui.mrSahara.setSelected(true); break; 
                case "org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel": SJmp3gui.mrAutumn.setSelected(true); break;  
                case "org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel": SJmp3gui.mrCreme.setSelected(true); break; 
                case "org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel": SJmp3gui.mrCremeCoffee.setSelected(true); break;                        
                case "org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel": SJmp3gui.mrModerate.setSelected(true); break; 
                case "org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel": SJmp3gui.mrMagellan.setSelected(true); break;  
                case "org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel": SJmp3gui.mrMistAqua.setSelected(true);  break; 
                case "org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel": SJmp3gui.mrMistSilver.setSelected(true);  break;                        
                case "org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel": SJmp3gui.mrOfficeBlue2007.setSelected(true); break; 
                case "org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel": SJmp3gui.mrOfficeBlack2007.setSelected(true); break;
                case "org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel": SJmp3gui.mrOfficeSilver2007.setSelected(true); break;                        
                case "org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel": SJmp3gui.mrNebula.setSelected(true);  break;
                case "org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel": SJmp3gui.mrNebulaBrickWall.setSelected(true);  break;                        
                case "org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel": SJmp3gui.mrGemini.setSelected(true); break;
                case "org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel": SJmp3gui.mrDustCoffee.setSelected(true);  break;
                case "org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel": SJmp3gui.mrDust.setSelected(true);  break;                        
                case "org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel": SJmp3gui.mrRaven.setSelected(true); break;
                case "org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel": SJmp3gui.mrGraphite.setSelected(true); break;                        
                case "org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel": SJmp3gui.mrGraphiteAqua.setSelected(true); break;
                case "org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel": SJmp3gui.mrGraphiteGlass.setSelected(true); break;                        
                case "org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel": SJmp3gui.mrBusinessBlack.setSelected(true); break;
                case "org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel": SJmp3gui.mrBusinessBlue.setSelected(true); break;                        
                case "org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel": SJmp3gui.mrBusiness.setSelected(true); break; 
                case "org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel": SJmp3gui.mrMariner.setSelected(true); break;
                case "org.pushingpixels.substance.api.skin.SubstanceCeruleanLookAndFeel": SJmp3gui.mrCerulean.setSelected(true); break; 
                case "org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel": SJmp3gui.mrTwilight.setSelected(true); break; 
                case "org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel": SJmp3gui.mrEmeraldDusk.setSelected(true); break; 
                case "org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel": SJmp3gui.mrChallengerDeep.setSelected(true); break; 
                case "de.muntjak.tinylookandfeel.TinyLookAndFeel": {
                    if (Actions.currentTheme.equals("lib/themes/Default.theme")) SJmp3gui.mrTinyDefault.setSelected(true);
                    if (Actions.currentTheme.equals("lib/themes/Forest.theme")) SJmp3gui.mrTinyForest.setSelected(true);
                    if (Actions.currentTheme.equals("lib/themes/Golden.theme")) SJmp3gui.mrTinyGolden.setSelected(true);
                    if (Actions.currentTheme.equals("lib/themes/Plastic.theme")) SJmp3gui.mrTinyPlastic.setSelected(true);
                    if (Actions.currentTheme.equals("lib/themes/Silver.theme")) SJmp3gui.mrTinySilver.setSelected(true);
                    if (Actions.currentTheme.equals("lib/themes/My_Cyan.theme")) SJmp3gui.mrTinyCyan.setSelected(true);
                    if (Actions.currentTheme.equals("lib/themes/My_Yellow.theme")) SJmp3gui.mrTinyYellow.setSelected(true);  
                    if (Actions.currentTheme.equals("lib/themes/My_AquaMarine.theme")) SJmp3gui.mrTinyAquaMarine.setSelected(true); 
                    if (Actions.currentTheme.equals("lib/themes/My_Magenta.theme")) SJmp3gui.mrTinyMagenta.setSelected(true);  
                    if (Actions.currentTheme.equals("lib/themes/My_Green.theme")) SJmp3gui.mrTinyGreen.setSelected(true);                    
                    }  break;
                case "com.lipstikLF.LipstikLookAndFeel": {
                    if (Actions.currentTheme.equals("LipstikDefault")) 
                        { SJmp3gui.mrLipstikDefault.setSelected(true); LipstikLookAndFeel.setMyCurrentTheme(new DefaultTheme());}
                    if (Actions.currentTheme.equals("LipstikKlearlooks")) 
                        { SJmp3gui.mrLipstikKlearlooks.setSelected(true); LipstikLookAndFeel.setMyCurrentTheme(new KlearlooksTheme());}
                    if (Actions.currentTheme.equals("LipstikLightGray")) 
                        { SJmp3gui.mrLipstikLightGray.setSelected(true); LipstikLookAndFeel.setMyCurrentTheme(new LightGrayTheme());}
                    }  break;                    
                }            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SJmp3gui.class.getName()).log(Level.ERROR, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SJmp3gui.class.getName()).log(Level.ERROR, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SJmp3gui.class.getName()).log(Level.ERROR, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SJmp3gui.class.getName()).log(Level.ERROR, null, ex);
        } catch (NullPointerException e) { }           
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mPopup = new javax.swing.JPopupMenu();
        mpPlay = new javax.swing.JMenuItem();
        mpPause = new javax.swing.JMenuItem();
        mpStop = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mpFastForward = new javax.swing.JMenuItem();
        mpFastBackward = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mpNext = new javax.swing.JMenuItem();
        mpPrevious = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mpSongUp = new javax.swing.JMenuItem();
        mpSongDown = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        mpCopyPathClipboard = new javax.swing.JMenuItem();
        mpPasteClipboard = new javax.swing.JMenuItem();
        mpShowClipboard = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        mp3info = new javax.swing.JMenuItem();
        mpRepeat = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        mpRemoveSong = new javax.swing.JMenuItem();
        mpDeleteFromFS = new javax.swing.JMenuItem();
        tbNiz = new javax.swing.JToolBar();
        tbUtils = new javax.swing.JToolBar();
        bFlac2Wav = new javax.swing.JButton();
        bConverter = new javax.swing.JButton();
        bSplitters = new javax.swing.JButton();
        bTagEditor = new javax.swing.JButton();
        tbTrackActions = new javax.swing.JToolBar();
        bPrevious = new javax.swing.JButton();
        bFastBackward = new javax.swing.JButton();
        bPause = new javax.swing.JButton();
        bPlay = new javax.swing.JButton();
        bStop = new javax.swing.JButton();
        bFastForward = new javax.swing.JButton();
        bNext = new javax.swing.JButton();
        tbOptions = new javax.swing.JToolBar();
        bSaveConfig = new javax.swing.JButton();
        bChangeSkin = new javax.swing.JButton();
        bMode = new javax.swing.JButton();
        bSoundMixer = new javax.swing.JButton();
        tbVerh = new javax.swing.JToolBar();
        tbOpen = new javax.swing.JToolBar();
        bOpenURL = new javax.swing.JButton();
        bOpenFile = new javax.swing.JButton();
        bOpenFolder = new javax.swing.JButton();
        bAddList = new javax.swing.JButton();
        tbListEditor = new javax.swing.JToolBar();
        bSongUP = new javax.swing.JButton();
        bCopyToClipboard = new javax.swing.JButton();
        bPasteFromClipboard = new javax.swing.JButton();
        bSavePL = new javax.swing.JButton();
        bRemoveSong = new javax.swing.JButton();
        bRemoveList = new javax.swing.JButton();
        bSongDown = new javax.swing.JButton();
        tbInfo = new javax.swing.JToolBar();
        bShowClipboard = new javax.swing.JButton();
        bid3Info = new javax.swing.JButton();
        bDonate = new javax.swing.JButton();
        bAbout = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        LST1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        LST2 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        LST3 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        LST4 = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        LST5 = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        LST6 = new javax.swing.JList();
        jScrollPane7 = new javax.swing.JScrollPane();
        LST7 = new javax.swing.JList();
        SongSlider = new javax.swing.JSlider();
        TimeTFend = new javax.swing.JTextField();
        TimeTFcur = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        File = new javax.swing.JMenu();
        mFileOpen = new javax.swing.JMenuItem();
        mFolderOpen = new javax.swing.JMenuItem();
        mListAdd = new javax.swing.JMenuItem();
        mOpenURL = new javax.swing.JMenuItem();
        mQuit = new javax.swing.JMenuItem();
        Action = new javax.swing.JMenu();
        mPlay = new javax.swing.JMenuItem();
        mPause = new javax.swing.JMenuItem();
        mStop = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mNext = new javax.swing.JMenuItem();
        mPrevious = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mFastForward = new javax.swing.JMenuItem();
        mFastBackward = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mid3info1 = new javax.swing.JMenuItem();
        mRepeatTrack = new javax.swing.JMenuItem();
        mListEdit = new javax.swing.JMenu();
        mSongUp = new javax.swing.JMenuItem();
        mSongDown = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mCopyPathClipboard = new javax.swing.JMenuItem();
        mPasteClipboard = new javax.swing.JMenuItem();
        mShowClipboard = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mRemoveSong = new javax.swing.JMenuItem();
        mDeleteFromFS = new javax.swing.JMenuItem();
        mRemoveAll = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mSavePlayList = new javax.swing.JMenuItem();
        mOptions = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        mClassic = new javax.swing.JRadioButtonMenuItem();
        mLoop = new javax.swing.JRadioButtonMenuItem();
        mShuffle = new javax.swing.JRadioButtonMenuItem();
        mRepeat = new javax.swing.JRadioButtonMenuItem();
        mProxyCfg = new javax.swing.JMenuItem();
        mSoundVolume = new javax.swing.JMenuItem();
        mChangeLF = new javax.swing.JMenuItem();
        mSaveCfg = new javax.swing.JMenuItem();
        mUtils = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        mSplitterTime = new javax.swing.JMenuItem();
        mSplitterSize = new javax.swing.JMenuItem();
        mConverter = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        mTagEditor = new javax.swing.JMenuItem();
        mVideoPlayer = new javax.swing.JMenuItem();
        mSkin = new javax.swing.JMenu();
        mrMetalOcean = new javax.swing.JRadioButtonMenuItem();
        mrSquareness = new javax.swing.JRadioButtonMenuItem();
        mrLiquid = new javax.swing.JRadioButtonMenuItem();
        mSubstance = new javax.swing.JMenu();
        mcbEffects = new javax.swing.JCheckBoxMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        mrRaven = new javax.swing.JRadioButtonMenuItem();
        mrSahara = new javax.swing.JRadioButtonMenuItem();
        mrGemini = new javax.swing.JRadioButtonMenuItem();
        mrMariner = new javax.swing.JRadioButtonMenuItem();
        mrAutumn = new javax.swing.JRadioButtonMenuItem();
        mrTwilight = new javax.swing.JRadioButtonMenuItem();
        mrCerulean = new javax.swing.JRadioButtonMenuItem();
        mrMagellan = new javax.swing.JRadioButtonMenuItem();
        mrModerate = new javax.swing.JRadioButtonMenuItem();
        mrEmeraldDusk = new javax.swing.JRadioButtonMenuItem();
        mrChallengerDeep = new javax.swing.JRadioButtonMenuItem();
        mMistSkins = new javax.swing.JMenu();
        mrMistAqua = new javax.swing.JRadioButtonMenuItem();
        mrMistSilver = new javax.swing.JRadioButtonMenuItem();
        mDustSkins = new javax.swing.JMenu();
        mrDust = new javax.swing.JRadioButtonMenuItem();
        mrDustCoffee = new javax.swing.JRadioButtonMenuItem();
        mOfficeSkins = new javax.swing.JMenu();
        mrOfficeBlue2007 = new javax.swing.JRadioButtonMenuItem();
        mrOfficeBlack2007 = new javax.swing.JRadioButtonMenuItem();
        mrOfficeSilver2007 = new javax.swing.JRadioButtonMenuItem();
        mCremeSkins = new javax.swing.JMenu();
        mrCreme = new javax.swing.JRadioButtonMenuItem();
        mrCremeCoffee = new javax.swing.JRadioButtonMenuItem();
        mNebulaSkins = new javax.swing.JMenu();
        mrNebula = new javax.swing.JRadioButtonMenuItem();
        mrNebulaBrickWall = new javax.swing.JRadioButtonMenuItem();
        mGraphiteSkins = new javax.swing.JMenu();
        mrGraphite = new javax.swing.JRadioButtonMenuItem();
        mrGraphiteAqua = new javax.swing.JRadioButtonMenuItem();
        mrGraphiteGlass = new javax.swing.JRadioButtonMenuItem();
        mBusinessSkins = new javax.swing.JMenu();
        mrBusiness = new javax.swing.JRadioButtonMenuItem();
        mrBusinessBlue = new javax.swing.JRadioButtonMenuItem();
        mrBusinessBlack = new javax.swing.JRadioButtonMenuItem();
        mTiny = new javax.swing.JMenu();
        mrTinyDefault = new javax.swing.JRadioButtonMenuItem();
        mrTinyForest = new javax.swing.JRadioButtonMenuItem();
        mrTinyGolden = new javax.swing.JRadioButtonMenuItem();
        mrTinyPlastic = new javax.swing.JRadioButtonMenuItem();
        mrTinySilver = new javax.swing.JRadioButtonMenuItem();
        mrTinyCyan = new javax.swing.JRadioButtonMenuItem();
        mrTinyYellow = new javax.swing.JRadioButtonMenuItem();
        mrTinyAquaMarine = new javax.swing.JRadioButtonMenuItem();
        mrTinyMagenta = new javax.swing.JRadioButtonMenuItem();
        mrTinyGreen = new javax.swing.JRadioButtonMenuItem();
        mLipstik = new javax.swing.JMenu();
        mrLipstikDefault = new javax.swing.JRadioButtonMenuItem();
        mrLipstikKlearlooks = new javax.swing.JRadioButtonMenuItem();
        mrLipstikLightGray = new javax.swing.JRadioButtonMenuItem();
        Help = new javax.swing.JMenu();
        mid3info = new javax.swing.JMenuItem();
        mDonate = new javax.swing.JMenuItem();
        mAbout = new javax.swing.JMenuItem();

        mpPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/play_green-1.png"))); // NOI18N
        mpPlay.setText("Play ");
        mpPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpPlayActionPerformed(evt);
            }
        });
        mPopup.add(mpPlay);

        mpPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/control_pause.png"))); // NOI18N
        mpPause.setText("Pause");
        mpPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpPauseActionPerformed(evt);
            }
        });
        mPopup.add(mpPause);

        mpStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/stop_red.png"))); // NOI18N
        mpStop.setText("Stop");
        mpStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpStopActionPerformed(evt);
            }
        });
        mPopup.add(mpStop);
        mPopup.add(jSeparator7);

        mpFastForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/fast-forward.png"))); // NOI18N
        mpFastForward.setText("Fast Forward");
        mpFastForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpFastForwardActionPerformed(evt);
            }
        });
        mPopup.add(mpFastForward);

        mpFastBackward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/fast-backward.png"))); // NOI18N
        mpFastBackward.setText("Fast Backward");
        mpFastBackward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpFastBackwardActionPerformed(evt);
            }
        });
        mPopup.add(mpFastBackward);
        mPopup.add(jSeparator8);

        mpNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/go-next.png"))); // NOI18N
        mpNext.setText("Next Track");
        mpNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpNextActionPerformed(evt);
            }
        });
        mPopup.add(mpNext);

        mpPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/go-previous.png"))); // NOI18N
        mpPrevious.setText("Previous Track");
        mpPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpPreviousActionPerformed(evt);
            }
        });
        mPopup.add(mpPrevious);
        mPopup.add(jSeparator9);

        mpSongUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/up-cyan-16.png"))); // NOI18N
        mpSongUp.setText("Move Track Up");
        mpSongUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpSongUpActionPerformed(evt);
            }
        });
        mPopup.add(mpSongUp);

        mpSongDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/down-cyan-16.png"))); // NOI18N
        mpSongDown.setText("Move Track Down");
        mpSongDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpSongDownActionPerformed(evt);
            }
        });
        mPopup.add(mpSongDown);
        mPopup.add(jSeparator10);

        mpCopyPathClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/clipboard_plus.png"))); // NOI18N
        mpCopyPathClipboard.setText("Copy Path/URL to Clipboard");
        mpCopyPathClipboard.setToolTipText("Copy selected Path/URL from List to Clipboard");
        mpCopyPathClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpCopyPathClipboardActionPerformed(evt);
            }
        });
        mPopup.add(mpCopyPathClipboard);

        mpPasteClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/clipboard_paste.png"))); // NOI18N
        mpPasteClipboard.setText("Paste Path/URL from Clipboard");
        mpPasteClipboard.setToolTipText("Paste Path/URL from Clipboard to List");
        mpPasteClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpPasteClipboardActionPerformed(evt);
            }
        });
        mPopup.add(mpPasteClipboard);

        mpShowClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/clipboard_lupa-16.png"))); // NOI18N
        mpShowClipboard.setText("Show Clipboard");
        mpShowClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpShowClipboardActionPerformed(evt);
            }
        });
        mPopup.add(mpShowClipboard);
        mPopup.add(jSeparator11);

        mp3info.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/info-cyan-16.png"))); // NOI18N
        mp3info.setText("File/Stream Info");
        mp3info.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mp3infoActionPerformed(evt);
            }
        });
        mPopup.add(mp3info);

        mpRepeat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/repeat_track.png"))); // NOI18N
        mpRepeat.setText("Repeat Track Mode");
        mpRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpRepeatActionPerformed(evt);
            }
        });
        mPopup.add(mpRepeat);
        mPopup.add(jSeparator12);

        mpRemoveSong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/list_remove-16.png"))); // NOI18N
        mpRemoveSong.setText("Remove Track from List");
        mpRemoveSong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpRemoveSongActionPerformed(evt);
            }
        });
        mPopup.add(mpRemoveSong);

        mpDeleteFromFS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/delete.png"))); // NOI18N
        mpDeleteFromFS.setText("Delete Track from FileSystem");
        mpDeleteFromFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mpDeleteFromFSActionPerformed(evt);
            }
        });
        mPopup.add(mpDeleteFromFS);

        mPopup.getAccessibleContext().setAccessibleName("");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SJmp3_03-10-15");
        setLocation(new java.awt.Point(100, 100));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(504, 381));

        tbNiz.setFloatable(false);

        tbUtils.setBorder(javax.swing.BorderFactory.createTitledBorder("Utils"));
        tbUtils.setFloatable(false);
        tbUtils.setRollover(true);

        bFlac2Wav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/converter-orange-24.png"))); // NOI18N
        bFlac2Wav.setToolTipText("Flac to Wav and Wav to Flac Converters");
        bFlac2Wav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFlac2WavActionPerformed(evt);
            }
        });
        tbUtils.add(bFlac2Wav);

        bConverter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/mix_audio(1).png"))); // NOI18N
        bConverter.setToolTipText("Converter WAV<->MP3");
        bConverter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConverterActionPerformed(evt);
            }
        });
        tbUtils.add(bConverter);

        bSplitters.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/edit-scissor-cut-icon.png"))); // NOI18N
        bSplitters.setToolTipText("Mp3 Splitters by Time/Size");
        bSplitters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSplittersActionPerformed(evt);
            }
        });
        tbUtils.add(bSplitters);

        bTagEditor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/edit_blue-24.png"))); // NOI18N
        bTagEditor.setToolTipText("Mp3 tag Editor");
        bTagEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTagEditorActionPerformed(evt);
            }
        });
        tbUtils.add(bTagEditor);

        tbNiz.add(tbUtils);

        tbTrackActions.setBorder(javax.swing.BorderFactory.createTitledBorder("Track Actions"));
        tbTrackActions.setFloatable(false);
        tbTrackActions.setRollover(true);

        bPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/old_go_previous.png"))); // NOI18N
        bPrevious.setToolTipText("Previous Track");
        bPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPreviousActionPerformed(evt);
            }
        });
        tbTrackActions.add(bPrevious);

        bFastBackward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/Actions-arrow-left-double-icon.png"))); // NOI18N
        bFastBackward.setToolTipText("Fast Backword");
        bFastBackward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFastBackwardActionPerformed(evt);
            }
        });
        tbTrackActions.add(bFastBackward);

        bPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/pause-orange.png"))); // NOI18N
        bPause.setToolTipText("Pause");
        bPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPauseActionPerformed(evt);
            }
        });
        tbTrackActions.add(bPause);

        bPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/play1hot.png"))); // NOI18N
        bPlay.setToolTipText("Play");
        bPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPlayActionPerformed(evt);
            }
        });
        tbTrackActions.add(bPlay);

        bStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/stop-red.png"))); // NOI18N
        bStop.setToolTipText("Stop");
        bStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStopActionPerformed(evt);
            }
        });
        tbTrackActions.add(bStop);

        bFastForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/Actions-arrow-right-double-icon.png"))); // NOI18N
        bFastForward.setToolTipText("Fast Forward");
        bFastForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFastForwardActionPerformed(evt);
            }
        });
        tbTrackActions.add(bFastForward);

        bNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/old_go_next.png"))); // NOI18N
        bNext.setToolTipText("Next Track");
        bNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNextActionPerformed(evt);
            }
        });
        tbTrackActions.add(bNext);

        tbNiz.add(tbTrackActions);

        tbOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        tbOptions.setFloatable(false);
        tbOptions.setRollover(true);
        tbOptions.setToolTipText("Options");

        bSaveConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/Save-icon.png"))); // NOI18N
        bSaveConfig.setToolTipText("Save Options");
        bSaveConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveConfigActionPerformed(evt);
            }
        });
        tbOptions.add(bSaveConfig);

        bChangeSkin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/skin_color_chooser-24.png"))); // NOI18N
        bChangeSkin.setToolTipText("Change Skin");
        bChangeSkin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bChangeSkinActionPerformed(evt);
            }
        });
        tbOptions.add(bChangeSkin);

        bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/next-forward.png"))); // NOI18N
        bMode.setToolTipText("Mode");
        bMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModeActionPerformed(evt);
            }
        });
        tbOptions.add(bMode);

        bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/audio_volume_high.png"))); // NOI18N
        bSoundMixer.setToolTipText("Sound Volume");
        bSoundMixer.setFocusable(false);
        bSoundMixer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSoundMixer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSoundMixer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSoundMixerActionPerformed(evt);
            }
        });
        tbOptions.add(bSoundMixer);

        tbNiz.add(tbOptions);

        tbVerh.setFloatable(false);

        tbOpen.setBorder(javax.swing.BorderFactory.createTitledBorder("Open"));
        tbOpen.setFloatable(false);
        tbOpen.setRollover(true);

        bOpenURL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/url-internet.png"))); // NOI18N
        bOpenURL.setToolTipText("Open URL/RadioStation");
        bOpenURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOpenURLActionPerformed(evt);
            }
        });
        tbOpen.add(bOpenURL);

        bOpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/Actions-document-open-icon.png"))); // NOI18N
        bOpenFile.setToolTipText("Open File");
        bOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOpenFileActionPerformed(evt);
            }
        });
        tbOpen.add(bOpenFile);

        bOpenFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/Folder-Generic-Green-24.png"))); // NOI18N
        bOpenFolder.setToolTipText("Open Folder");
        bOpenFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOpenFolderActionPerformed(evt);
            }
        });
        tbOpen.add(bOpenFolder);

        bAddList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/audio_x_generic.png"))); // NOI18N
        bAddList.setToolTipText("Load PlayList");
        bAddList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddListActionPerformed(evt);
            }
        });
        tbOpen.add(bAddList);

        tbVerh.add(tbOpen);

        tbListEditor.setBorder(javax.swing.BorderFactory.createTitledBorder("List Editor"));
        tbListEditor.setFloatable(false);
        tbListEditor.setRollover(true);

        bSongUP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/up-cyan-24.png"))); // NOI18N
        bSongUP.setToolTipText("Move Track Up");
        bSongUP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSongUPActionPerformed(evt);
            }
        });
        tbListEditor.add(bSongUP);

        bCopyToClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/copy-blue-24.png"))); // NOI18N
        bCopyToClipboard.setToolTipText("Copy selected Path/URL from List to Clipboard");
        bCopyToClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCopyToClipboardActionPerformed(evt);
            }
        });
        tbListEditor.add(bCopyToClipboard);

        bPasteFromClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/paste-24-1.png"))); // NOI18N
        bPasteFromClipboard.setToolTipText("Paste Path/URL from Clipboard to List");
        bPasteFromClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPasteFromClipboardActionPerformed(evt);
            }
        });
        tbListEditor.add(bPasteFromClipboard);

        bSavePL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/playlist-save.png"))); // NOI18N
        bSavePL.setToolTipText("Save PlayList");
        bSavePL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSavePLActionPerformed(evt);
            }
        });
        tbListEditor.add(bSavePL);

        bRemoveSong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/list_remove-24.png"))); // NOI18N
        bRemoveSong.setToolTipText("Remove Track from List");
        bRemoveSong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRemoveSongActionPerformed(evt);
            }
        });
        tbListEditor.add(bRemoveSong);

        bRemoveList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/edit_clear.png"))); // NOI18N
        bRemoveList.setToolTipText("Remove All from List");
        bRemoveList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRemoveListActionPerformed(evt);
            }
        });
        tbListEditor.add(bRemoveList);

        bSongDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/down-cyan-24.png"))); // NOI18N
        bSongDown.setToolTipText("Move Track Down");
        bSongDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSongDownActionPerformed(evt);
            }
        });
        tbListEditor.add(bSongDown);

        tbVerh.add(tbListEditor);

        tbInfo.setBorder(javax.swing.BorderFactory.createTitledBorder("Info"));
        tbInfo.setFloatable(false);
        tbInfo.setRollover(true);
        tbInfo.setToolTipText("Info");

        bShowClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/clipboard_lupa-24.png"))); // NOI18N
        bShowClipboard.setToolTipText("Show Clipboard");
        bShowClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bShowClipboardActionPerformed(evt);
            }
        });
        tbInfo.add(bShowClipboard);

        bid3Info.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/info-cyan-24.png"))); // NOI18N
        bid3Info.setToolTipText("File/Stream Info");
        bid3Info.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bid3InfoActionPerformed(evt);
            }
        });
        tbInfo.add(bid3Info);

        bDonate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/coins-add-icon.png"))); // NOI18N
        bDonate.setToolTipText("Donate");
        bDonate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDonateActionPerformed(evt);
            }
        });
        tbInfo.add(bDonate);

        bAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/help-cyan-24.png"))); // NOI18N
        bAbout.setToolTipText("About");
        bAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAboutActionPerformed(evt);
            }
        });
        tbInfo.add(bAbout);

        tbVerh.add(tbInfo);

        tabbedPane.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tabbedPane.setMaximumSize(new java.awt.Dimension(409, 209));
        tabbedPane.setMinimumSize(new java.awt.Dimension(409, 209));
        tabbedPane.setPreferredSize(new java.awt.Dimension(475, 217));
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        LST1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LST1.setComponentPopupMenu(mPopup);
        LST1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LST1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LST1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(LST1);

        tabbedPane.addTab("Player-1", jScrollPane1);

        LST2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LST2.setComponentPopupMenu(mPopup);
        LST2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LST2MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LST2MouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(LST2);

        tabbedPane.addTab("Player-2", jScrollPane2);

        LST3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LST3.setComponentPopupMenu(mPopup);
        LST3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LST3MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LST3MouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(LST3);

        tabbedPane.addTab("Player-3", jScrollPane3);

        LST4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LST4.setComponentPopupMenu(mPopup);
        LST4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LST4MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LST4MouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(LST4);

        tabbedPane.addTab("Player-4", jScrollPane4);

        LST5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LST5.setComponentPopupMenu(mPopup);
        LST5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LST5MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LST5MouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(LST5);

        tabbedPane.addTab("Player-5", jScrollPane5);

        LST6.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LST6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LST6MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(LST6);

        tabbedPane.addTab("Player-6", jScrollPane6);

        LST7.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LST7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LST7MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(LST7);

        tabbedPane.addTab("Player-7", jScrollPane7);

        SongSlider.setMaximum(1000);
        SongSlider.setValue(0);
        SongSlider.setMaximumSize(new java.awt.Dimension(365, 19));
        SongSlider.setMinimumSize(new java.awt.Dimension(365, 19));
        SongSlider.setPreferredSize(new java.awt.Dimension(365, 19));
        SongSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                SongSliderMouseDragged(evt);
            }
        });
        SongSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SongSliderMouseClicked(evt);
            }
        });

        TimeTFend.setEditable(false);
        TimeTFend.setMaximumSize(new java.awt.Dimension(50, 19));
        TimeTFend.setMinimumSize(new java.awt.Dimension(50, 19));
        TimeTFend.setPreferredSize(new java.awt.Dimension(50, 19));

        TimeTFcur.setEditable(false);
        TimeTFcur.setMaximumSize(new java.awt.Dimension(50, 19));
        TimeTFcur.setMinimumSize(new java.awt.Dimension(50, 19));
        TimeTFcur.setPreferredSize(new java.awt.Dimension(50, 19));

        jMenuBar1.setToolTipText("");

        File.setText("File");

        mFileOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/Actions-document-open-16.png"))); // NOI18N
        mFileOpen.setText("Open File");
        mFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mFileOpenActionPerformed(evt);
            }
        });
        File.add(mFileOpen);

        mFolderOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/Folder-Generic-Green-16.png"))); // NOI18N
        mFolderOpen.setText("Open Folder");
        mFolderOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mFolderOpenActionPerformed(evt);
            }
        });
        File.add(mFolderOpen);

        mListAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/folder_open_document_music_playlist.png"))); // NOI18N
        mListAdd.setText("Load PlayList");
        mListAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mListAddActionPerformed(evt);
            }
        });
        File.add(mListAdd);

        mOpenURL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/world_connect-16.png"))); // NOI18N
        mOpenURL.setText("Open URL/RadioStation");
        mOpenURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mOpenURLActionPerformed(evt);
            }
        });
        File.add(mOpenURL);

        mQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/quit.png"))); // NOI18N
        mQuit.setText("Exit");
        mQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mQuitActionPerformed(evt);
            }
        });
        File.add(mQuit);

        jMenuBar1.add(File);

        Action.setText("Track Actions");

        mPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/play_green-1.png"))); // NOI18N
        mPlay.setText("Play");
        mPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPlayActionPerformed(evt);
            }
        });
        Action.add(mPlay);

        mPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/control_pause.png"))); // NOI18N
        mPause.setText("Pause");
        mPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPauseActionPerformed(evt);
            }
        });
        Action.add(mPause);

        mStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/stop_red.png"))); // NOI18N
        mStop.setText("Stop");
        mStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mStopActionPerformed(evt);
            }
        });
        Action.add(mStop);
        Action.add(jSeparator4);

        mNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/go-next.png"))); // NOI18N
        mNext.setText("Next Track");
        mNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNextActionPerformed(evt);
            }
        });
        Action.add(mNext);

        mPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/go-previous.png"))); // NOI18N
        mPrevious.setText("Previous Track");
        mPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPreviousActionPerformed(evt);
            }
        });
        Action.add(mPrevious);
        Action.add(jSeparator5);

        mFastForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/fast-forward.png"))); // NOI18N
        mFastForward.setText("Fast Forward");
        mFastForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mFastForwardActionPerformed(evt);
            }
        });
        Action.add(mFastForward);

        mFastBackward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/fast-backward.png"))); // NOI18N
        mFastBackward.setText("Fast Backward");
        mFastBackward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mFastBackwardActionPerformed(evt);
            }
        });
        Action.add(mFastBackward);
        Action.add(jSeparator6);

        mid3info1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/info-cyan-16.png"))); // NOI18N
        mid3info1.setText("File/Stream Info");
        mid3info1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mid3info1ActionPerformed(evt);
            }
        });
        Action.add(mid3info1);

        mRepeatTrack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/repeat_track.png"))); // NOI18N
        mRepeatTrack.setText("Repeat Track Mode");
        mRepeatTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mRepeatTrackActionPerformed(evt);
            }
        });
        Action.add(mRepeatTrack);

        jMenuBar1.add(Action);

        mListEdit.setText("List Editor");

        mSongUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/up-cyan-16.png"))); // NOI18N
        mSongUp.setText("Move Track Up");
        mSongUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSongUpActionPerformed(evt);
            }
        });
        mListEdit.add(mSongUp);

        mSongDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/down-cyan-16.png"))); // NOI18N
        mSongDown.setText("Move Track Down");
        mSongDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSongDownActionPerformed(evt);
            }
        });
        mListEdit.add(mSongDown);
        mListEdit.add(jSeparator1);

        mCopyPathClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/clipboard_plus.png"))); // NOI18N
        mCopyPathClipboard.setText("Copy Path/URL to Clipboard");
        mCopyPathClipboard.setToolTipText("Copy selected Path/URL from List to Clipboard");
        mCopyPathClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mCopyPathClipboardActionPerformed(evt);
            }
        });
        mListEdit.add(mCopyPathClipboard);

        mPasteClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/clipboard_paste.png"))); // NOI18N
        mPasteClipboard.setText("Paste Path/URL from Clipboard");
        mPasteClipboard.setToolTipText("Paste Path/URL from Clipboard to List");
        mPasteClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mPasteClipboardActionPerformed(evt);
            }
        });
        mListEdit.add(mPasteClipboard);

        mShowClipboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/clipboard_lupa-16.png"))); // NOI18N
        mShowClipboard.setText("Show Clipboard");
        mShowClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mShowClipboardActionPerformed(evt);
            }
        });
        mListEdit.add(mShowClipboard);
        mListEdit.add(jSeparator2);

        mRemoveSong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/list_remove-16.png"))); // NOI18N
        mRemoveSong.setText("Remove Track from List");
        mRemoveSong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mRemoveSongActionPerformed(evt);
            }
        });
        mListEdit.add(mRemoveSong);

        mDeleteFromFS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/delete.png"))); // NOI18N
        mDeleteFromFS.setText("Delete Track from FileSystem");
        mDeleteFromFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDeleteFromFSActionPerformed(evt);
            }
        });
        mListEdit.add(mDeleteFromFS);

        mRemoveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/recycler.png"))); // NOI18N
        mRemoveAll.setText("Remove All from List");
        mRemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mRemoveAllActionPerformed(evt);
            }
        });
        mListEdit.add(mRemoveAll);
        mListEdit.add(jSeparator3);

        mSavePlayList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/save.png"))); // NOI18N
        mSavePlayList.setText("Save PlayList");
        mSavePlayList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSavePlayListActionPerformed(evt);
            }
        });
        mListEdit.add(mSavePlayList);

        jMenuBar1.add(mListEdit);

        mOptions.setText("Options");

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/select-by-color-icon.png"))); // NOI18N
        jMenu6.setText("Mode Select");

        mClassic.setText("No Repeat");
        mClassic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/next-forward-16.png"))); // NOI18N
        mClassic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mClassicActionPerformed(evt);
            }
        });
        jMenu6.add(mClassic);

        mLoop.setText("Loop ");
        mLoop.setToolTipText("Repeat List");
        mLoop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/media-playlist-repeat_all.png"))); // NOI18N
        mLoop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mLoopActionPerformed(evt);
            }
        });
        jMenu6.add(mLoop);

        mShuffle.setText("Shuffle");
        mShuffle.setToolTipText("Random Mode");
        mShuffle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/shuffle-16.png"))); // NOI18N
        mShuffle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mShuffleActionPerformed(evt);
            }
        });
        jMenu6.add(mShuffle);

        mRepeat.setText("Repeat Track");
        mRepeat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/repeat_track.png"))); // NOI18N
        mRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mRepeatActionPerformed(evt);
            }
        });
        jMenu6.add(mRepeat);

        mOptions.add(jMenu6);

        mProxyCfg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/proxy-server-icon.png"))); // NOI18N
        mProxyCfg.setText("Proxy Settings");
        mProxyCfg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mProxyCfgActionPerformed(evt);
            }
        });
        mOptions.add(mProxyCfg);

        mSoundVolume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/volume_loud.png"))); // NOI18N
        mSoundVolume.setText("Sound Volume");
        mSoundVolume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSoundVolumeActionPerformed(evt);
            }
        });
        mOptions.add(mSoundVolume);

        mChangeLF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/color_swatches.png"))); // NOI18N
        mChangeLF.setText("Change LF");
        mChangeLF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mChangeLFActionPerformed(evt);
            }
        });
        mOptions.add(mChangeLF);

        mSaveCfg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/save.png"))); // NOI18N
        mSaveCfg.setText("Save Options");
        mSaveCfg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSaveCfgActionPerformed(evt);
            }
        });
        mOptions.add(mSaveCfg);

        jMenuBar1.add(mOptions);

        mUtils.setText("Utils");

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/MP3-blue-16.png"))); // NOI18N
        jMenu1.setText("Mp3 Splitters");

        mSplitterTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/split_090.png"))); // NOI18N
        mSplitterTime.setText("Mp3 Splitter by Time");
        mSplitterTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSplitterTimeActionPerformed(evt);
            }
        });
        jMenu1.add(mSplitterTime);

        mSplitterSize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/split_270.png"))); // NOI18N
        mSplitterSize.setText("Mp3 Splitter by Size");
        mSplitterSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSplitterSizeActionPerformed(evt);
            }
        });
        jMenu1.add(mSplitterSize);

        mUtils.add(jMenu1);

        mConverter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/mix_audio-16.png"))); // NOI18N
        mConverter.setText("Wav <-> Mp3 Converter");
        mConverter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mConverterActionPerformed(evt);
            }
        });
        mUtils.add(mConverter);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/converter-orange-16.png"))); // NOI18N
        jMenuItem1.setText("Wav <-> Flac Converter");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mUtils.add(jMenuItem1);

        mTagEditor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/document_edit.png"))); // NOI18N
        mTagEditor.setText("Mp3 tag Editor");
        mTagEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTagEditorActionPerformed(evt);
            }
        });
        mUtils.add(mTagEditor);

        mVideoPlayer.setText("Video Player");
        mVideoPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mVideoPlayerActionPerformed(evt);
            }
        });
        mUtils.add(mVideoPlayer);

        jMenuBar1.add(mUtils);

        mSkin.setText("Skin");

        mrMetalOcean.setText("Metal (default)");
        mrMetalOcean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrMetalOceanActionPerformed(evt);
            }
        });
        mSkin.add(mrMetalOcean);

        mrSquareness.setText("Squareness");
        mrSquareness.setToolTipText("http://squareness.sourceforge.net/ or http://squareness.beeger.net/");
        mrSquareness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrSquarenessActionPerformed(evt);
            }
        });
        mSkin.add(mrSquareness);

        mrLiquid.setText("Liquid");
        mrLiquid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrLiquidActionPerformed(evt);
            }
        });
        mSkin.add(mrLiquid);

        mSubstance.setText("Substance");
        mSubstance.setToolTipText("http://shemnon.com/speling/2011/07/insubstantial-63-release.html");

        mcbEffects.setText("Animation Effects");
        mcbEffects.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/Adobe-After-Effects-CS-3-icon.png"))); // NOI18N
        mcbEffects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mcbEffectsActionPerformed(evt);
            }
        });
        mSubstance.add(mcbEffects);
        mSubstance.add(jSeparator13);

        mrRaven.setText("Raven");
        mrRaven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrRavenActionPerformed(evt);
            }
        });
        mSubstance.add(mrRaven);

        mrSahara.setText("Sahara");
        mrSahara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrSaharaActionPerformed(evt);
            }
        });
        mSubstance.add(mrSahara);

        mrGemini.setText("Gemini");
        mrGemini.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrGeminiActionPerformed(evt);
            }
        });
        mSubstance.add(mrGemini);

        mrMariner.setText("Mariner");
        mrMariner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrMarinerActionPerformed(evt);
            }
        });
        mSubstance.add(mrMariner);

        mrAutumn.setText("Autumn");
        mrAutumn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrAutumnActionPerformed(evt);
            }
        });
        mSubstance.add(mrAutumn);

        mrTwilight.setText("Twilight");
        mrTwilight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTwilightActionPerformed(evt);
            }
        });
        mSubstance.add(mrTwilight);

        mrCerulean.setText("Cerulean");
        mrCerulean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrCeruleanActionPerformed(evt);
            }
        });
        mSubstance.add(mrCerulean);

        mrMagellan.setText("Magellan");
        mrMagellan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrMagellanActionPerformed(evt);
            }
        });
        mSubstance.add(mrMagellan);

        mrModerate.setText("Moderate");
        mrModerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrModerateActionPerformed(evt);
            }
        });
        mSubstance.add(mrModerate);

        mrEmeraldDusk.setText("EmeraldDusk");
        mrEmeraldDusk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrEmeraldDuskActionPerformed(evt);
            }
        });
        mSubstance.add(mrEmeraldDusk);

        mrChallengerDeep.setText("ChallengerDeep");
        mrChallengerDeep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrChallengerDeepActionPerformed(evt);
            }
        });
        mSubstance.add(mrChallengerDeep);

        mMistSkins.setText("Mist Skins");

        mrMistAqua.setText("MistAqua");
        mrMistAqua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrMistAquaActionPerformed(evt);
            }
        });
        mMistSkins.add(mrMistAqua);

        mrMistSilver.setText("MistSilver");
        mrMistSilver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrMistSilverActionPerformed(evt);
            }
        });
        mMistSkins.add(mrMistSilver);

        mSubstance.add(mMistSkins);

        mDustSkins.setText("Dust Skins");

        mrDust.setText("Dust");
        mrDust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrDustActionPerformed(evt);
            }
        });
        mDustSkins.add(mrDust);

        mrDustCoffee.setText("DustCoffee");
        mrDustCoffee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrDustCoffeeActionPerformed(evt);
            }
        });
        mDustSkins.add(mrDustCoffee);

        mSubstance.add(mDustSkins);

        mOfficeSkins.setText("Office Skins");

        mrOfficeBlue2007.setText("OfficeBlue2007");
        mrOfficeBlue2007.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrOfficeBlue2007ActionPerformed(evt);
            }
        });
        mOfficeSkins.add(mrOfficeBlue2007);

        mrOfficeBlack2007.setText("OfficeBlack2007");
        mrOfficeBlack2007.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrOfficeBlack2007ActionPerformed(evt);
            }
        });
        mOfficeSkins.add(mrOfficeBlack2007);

        mrOfficeSilver2007.setText("OfficeSilver2007");
        mrOfficeSilver2007.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrOfficeSilver2007ActionPerformed(evt);
            }
        });
        mOfficeSkins.add(mrOfficeSilver2007);

        mSubstance.add(mOfficeSkins);

        mCremeSkins.setText("Creme Skins");

        mrCreme.setText("Creme");
        mrCreme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrCremeActionPerformed(evt);
            }
        });
        mCremeSkins.add(mrCreme);

        mrCremeCoffee.setText("CremeCoffee");
        mrCremeCoffee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrCremeCoffeeActionPerformed(evt);
            }
        });
        mCremeSkins.add(mrCremeCoffee);

        mSubstance.add(mCremeSkins);

        mNebulaSkins.setText("Nebula Skins");

        mrNebula.setText("Nebula");
        mrNebula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrNebulaActionPerformed(evt);
            }
        });
        mNebulaSkins.add(mrNebula);

        mrNebulaBrickWall.setText("NebulaBrick");
        mrNebulaBrickWall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrNebulaBrickWallActionPerformed(evt);
            }
        });
        mNebulaSkins.add(mrNebulaBrickWall);

        mSubstance.add(mNebulaSkins);

        mGraphiteSkins.setText("Graphite Skins");

        mrGraphite.setText("Graphite");
        mrGraphite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrGraphiteActionPerformed(evt);
            }
        });
        mGraphiteSkins.add(mrGraphite);

        mrGraphiteAqua.setText("GraphiteAqua");
        mrGraphiteAqua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrGraphiteAquaActionPerformed(evt);
            }
        });
        mGraphiteSkins.add(mrGraphiteAqua);

        mrGraphiteGlass.setText("GraphiteGlass");
        mrGraphiteGlass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrGraphiteGlassActionPerformed(evt);
            }
        });
        mGraphiteSkins.add(mrGraphiteGlass);

        mSubstance.add(mGraphiteSkins);

        mBusinessSkins.setText("Business Skins");

        mrBusiness.setText("BusinessGrey");
        mrBusiness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrBusinessActionPerformed(evt);
            }
        });
        mBusinessSkins.add(mrBusiness);

        mrBusinessBlue.setText("BusinessBlue");
        mrBusinessBlue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrBusinessBlueActionPerformed(evt);
            }
        });
        mBusinessSkins.add(mrBusinessBlue);

        mrBusinessBlack.setText("BusinessBlack");
        mrBusinessBlack.setToolTipText("");
        mrBusinessBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrBusinessBlackActionPerformed(evt);
            }
        });
        mBusinessSkins.add(mrBusinessBlack);

        mSubstance.add(mBusinessSkins);

        mSkin.add(mSubstance);

        mTiny.setText("Tiny");
        mTiny.setToolTipText("<html><a href=\"http://www.muntjak.de/hans/java/tinylaf/index.html\">http://www.muntjak.de/hans/java/tinylaf/index.html</a></html>");

        mrTinyDefault.setText("Default");
        mrTinyDefault.setToolTipText("");
        mrTinyDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinyDefaultActionPerformed(evt);
            }
        });
        mTiny.add(mrTinyDefault);

        mrTinyForest.setText("Forest");
        mrTinyForest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinyForestActionPerformed(evt);
            }
        });
        mTiny.add(mrTinyForest);

        mrTinyGolden.setText("Golden");
        mrTinyGolden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinyGoldenActionPerformed(evt);
            }
        });
        mTiny.add(mrTinyGolden);

        mrTinyPlastic.setText("Plastic");
        mrTinyPlastic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinyPlasticActionPerformed(evt);
            }
        });
        mTiny.add(mrTinyPlastic);

        mrTinySilver.setText("Silver");
        mrTinySilver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinySilverActionPerformed(evt);
            }
        });
        mTiny.add(mrTinySilver);

        mrTinyCyan.setText("Cyan");
        mrTinyCyan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinyCyanActionPerformed(evt);
            }
        });
        mTiny.add(mrTinyCyan);

        mrTinyYellow.setText("Yellow");
        mrTinyYellow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinyYellowActionPerformed(evt);
            }
        });
        mTiny.add(mrTinyYellow);

        mrTinyAquaMarine.setText("AquaMarine");
        mrTinyAquaMarine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinyAquaMarineActionPerformed(evt);
            }
        });
        mTiny.add(mrTinyAquaMarine);

        mrTinyMagenta.setText("Magenta");
        mrTinyMagenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinyMagentaActionPerformed(evt);
            }
        });
        mTiny.add(mrTinyMagenta);

        mrTinyGreen.setText("Green");
        mrTinyGreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrTinyGreenActionPerformed(evt);
            }
        });
        mTiny.add(mrTinyGreen);

        mSkin.add(mTiny);

        mLipstik.setText("Lipstik");
        mLipstik.setToolTipText("sourceforge.net/projects/lipstiklf/");

        mrLipstikDefault.setText("Default");
        mrLipstikDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrLipstikDefaultActionPerformed(evt);
            }
        });
        mLipstik.add(mrLipstikDefault);

        mrLipstikKlearlooks.setText("Klearlooks");
        mrLipstikKlearlooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrLipstikKlearlooksActionPerformed(evt);
            }
        });
        mLipstik.add(mrLipstikKlearlooks);

        mrLipstikLightGray.setText("LightGray");
        mrLipstikLightGray.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mrLipstikLightGrayActionPerformed(evt);
            }
        });
        mLipstik.add(mrLipstikLightGray);

        mSkin.add(mLipstik);

        jMenuBar1.add(mSkin);

        Help.setText("Info");

        mid3info.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/info-cyan-16.png"))); // NOI18N
        mid3info.setText("File/Stream Info");
        mid3info.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mid3infoActionPerformed(evt);
            }
        });
        Help.add(mid3info);

        mDonate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/donate-coins_add.png"))); // NOI18N
        mDonate.setText("Donate");
        mDonate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDonateActionPerformed(evt);
            }
        });
        Help.add(mDonate);

        mAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/help-cyan-16.png"))); // NOI18N
        mAbout.setText("About");
        mAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAboutActionPerformed(evt);
            }
        });
        Help.add(mAbout);

        jMenuBar1.add(Help);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tbNiz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tbVerh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TimeTFcur, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SongSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TimeTFend, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbVerh, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TimeTFcur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TimeTFend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SongSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tbNiz, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabbedPane.getAccessibleContext().setAccessibleName("TBpane");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void bOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOpenFileActionPerformed
        Actions.SelectFile ();
    }//GEN-LAST:event_bOpenFileActionPerformed
    private void bPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPlayActionPerformed
          Actions.GoPlay ();
    }//GEN-LAST:event_bPlayActionPerformed
    private void mAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAboutActionPerformed
        Actions act=new Actions();
        act.about();
    }//GEN-LAST:event_mAboutActionPerformed
    private void mFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mFileOpenActionPerformed
        Actions.SelectFile ();
    }//GEN-LAST:event_mFileOpenActionPerformed
    private void mPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPlayActionPerformed
        Actions.GoPlay ();
    }//GEN-LAST:event_mPlayActionPerformed
    private void mQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mQuitActionPerformed
    private void bStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStopActionPerformed
        Actions.stopp();
    }//GEN-LAST:event_bStopActionPerformed
    private void bOpenFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOpenFolderActionPerformed
        Actions.SelectFolder ();
    }//GEN-LAST:event_bOpenFolderActionPerformed
    private void bNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNextActionPerformed
        Actions.PlayNext ();
    }//GEN-LAST:event_bNextActionPerformed
    private void bPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPreviousActionPerformed
        Actions.PlayPrevious ();
    }//GEN-LAST:event_bPreviousActionPerformed
    private void mFolderOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mFolderOpenActionPerformed
        Actions.SelectFolder ();
    }//GEN-LAST:event_mFolderOpenActionPerformed
    private void mStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mStopActionPerformed
        Actions.stopp();
    }//GEN-LAST:event_mStopActionPerformed
    private void mPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPreviousActionPerformed
        Actions.PlayPrevious ();
    }//GEN-LAST:event_mPreviousActionPerformed
    private void mNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNextActionPerformed
        Actions.PlayNext ();
    }//GEN-LAST:event_mNextActionPerformed
    private void bRemoveListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRemoveListActionPerformed
        Actions.RemoveAll();
    }//GEN-LAST:event_bRemoveListActionPerformed
    private void bAddListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddListActionPerformed
        Actions.AddList();
    }//GEN-LAST:event_bAddListActionPerformed
    private void bid3InfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bid3InfoActionPerformed
        Actions act=new Actions();
        act.infoMpgaMp123 ();
    }//GEN-LAST:event_bid3InfoActionPerformed
    private void mListAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mListAddActionPerformed
        Actions.AddList();
    }//GEN-LAST:event_mListAddActionPerformed
    private void bPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPauseActionPerformed
        Actions.pause();
    }//GEN-LAST:event_bPauseActionPerformed
    private void mPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPauseActionPerformed
        Actions.pause();
    }//GEN-LAST:event_mPauseActionPerformed
    private void mid3infoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mid3infoActionPerformed
        Actions act=new Actions();
        act.infoMpgaMp123 ();
    }//GEN-LAST:event_mid3infoActionPerformed
    private void bSaveConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveConfigActionPerformed
        this.SaveConfig();
    }//GEN-LAST:event_bSaveConfigActionPerformed
    private void mrGraphiteAquaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrGraphiteAquaActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel");
    }//GEN-LAST:event_mrGraphiteAquaActionPerformed
    private void mrRavenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrRavenActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel");
    }//GEN-LAST:event_mrRavenActionPerformed
    private void mrDustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrDustActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel");
    }//GEN-LAST:event_mrDustActionPerformed
    private void mrGeminiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrGeminiActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel");
    }//GEN-LAST:event_mrGeminiActionPerformed
    private void mrNebulaBrickWallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrNebulaBrickWallActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel");
    }//GEN-LAST:event_mrNebulaBrickWallActionPerformed
    private void mrDustCoffeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrDustCoffeeActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel");
    }//GEN-LAST:event_mrDustCoffeeActionPerformed
    private void mrNebulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrNebulaActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel");
    }//GEN-LAST:event_mrNebulaActionPerformed
    private void mrOfficeBlue2007ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrOfficeBlue2007ActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel");
    }//GEN-LAST:event_mrOfficeBlue2007ActionPerformed
    private void mrBusinessBlueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrBusinessBlueActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel");
    }//GEN-LAST:event_mrBusinessBlueActionPerformed
    private void mrMistAquaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrMistAquaActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel");
    }//GEN-LAST:event_mrMistAquaActionPerformed
    private void mrMagellanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrMagellanActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel");
    }//GEN-LAST:event_mrMagellanActionPerformed
    private void mrModerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrModerateActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel");
    }//GEN-LAST:event_mrModerateActionPerformed
    private void mrCremeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrCremeActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel");
    }//GEN-LAST:event_mrCremeActionPerformed
    private void mrSaharaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrSaharaActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel");
    }//GEN-LAST:event_mrSaharaActionPerformed
    private void mrAutumnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrAutumnActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel");
    }//GEN-LAST:event_mrAutumnActionPerformed
    private void mrMetalOceanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrMetalOceanActionPerformed
        MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        this.setLF("javax.swing.plaf.metal.MetalLookAndFeel");
        //mrMetalOcean.setSelected(true);
    }//GEN-LAST:event_mrMetalOceanActionPerformed
    private void mSaveCfgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSaveCfgActionPerformed
        this.SaveConfig();        
    }//GEN-LAST:event_mSaveCfgActionPerformed
    private void bSavePLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSavePLActionPerformed
        SavePlayList.Save();
    }//GEN-LAST:event_bSavePLActionPerformed
    private void bFastForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFastForwardActionPerformed
        Actions.FastForward();
    }//GEN-LAST:event_bFastForwardActionPerformed
    private void bFastBackwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFastBackwardActionPerformed
        Actions.FastBackward();
    }//GEN-LAST:event_bFastBackwardActionPerformed
    private void mFastForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mFastForwardActionPerformed
        Actions.FastForward();        
    }//GEN-LAST:event_mFastForwardActionPerformed
    private void mFastBackwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mFastBackwardActionPerformed
        Actions.FastBackward();        
    }//GEN-LAST:event_mFastBackwardActionPerformed
    private void bSoundMixerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSoundMixerActionPerformed
        MixerSet();
    }//GEN-LAST:event_bSoundMixerActionPerformed
    private void mSoundVolumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSoundVolumeActionPerformed
        MixerSet();
    }//GEN-LAST:event_mSoundVolumeActionPerformed
    private void mrBusinessBlackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrBusinessBlackActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel");
    }//GEN-LAST:event_mrBusinessBlackActionPerformed
    private void mrBusinessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrBusinessActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel");
    }//GEN-LAST:event_mrBusinessActionPerformed
    private void mrCremeCoffeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrCremeCoffeeActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel");
    }//GEN-LAST:event_mrCremeCoffeeActionPerformed
    private void mrOfficeBlack2007ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrOfficeBlack2007ActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel");
    }//GEN-LAST:event_mrOfficeBlack2007ActionPerformed
    private void mrOfficeSilver2007ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrOfficeSilver2007ActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel");
    }//GEN-LAST:event_mrOfficeSilver2007ActionPerformed
    private void mrMarinerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrMarinerActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel");
        //jMenuBar1.setForeground(Color.yellow);
        //jMenuBar1.setBackground(Color.green);
    }//GEN-LAST:event_mrMarinerActionPerformed
    private void mrCeruleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrCeruleanActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceCeruleanLookAndFeel");
    }//GEN-LAST:event_mrCeruleanActionPerformed
    private void mp3infoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mp3infoActionPerformed
        Actions act=new Actions();
        act.infoMpgaMp123 ();
    }//GEN-LAST:event_mp3infoActionPerformed
    private void mpRemoveSongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpRemoveSongActionPerformed
        Actions.RemoveSong();
    }//GEN-LAST:event_mpRemoveSongActionPerformed
    private void mpPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpPlayActionPerformed
        Actions.GoPlay();
    }//GEN-LAST:event_mpPlayActionPerformed
    private void mpStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpStopActionPerformed
        Actions.stopp();
    }//GEN-LAST:event_mpStopActionPerformed
    private void mpPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpPauseActionPerformed
        Actions.pause();
    }//GEN-LAST:event_mpPauseActionPerformed
    private void mpFastForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpFastForwardActionPerformed
        Actions.FastForward();
    }//GEN-LAST:event_mpFastForwardActionPerformed
    private void mpFastBackwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpFastBackwardActionPerformed
        Actions.FastBackward();
    }//GEN-LAST:event_mpFastBackwardActionPerformed
    private void mSavePlayListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSavePlayListActionPerformed
        SavePlayList.Save();
    }//GEN-LAST:event_mSavePlayListActionPerformed
    private void mRemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mRemoveAllActionPerformed
        Actions.RemoveAll();
    }//GEN-LAST:event_mRemoveAllActionPerformed
    private void mRemoveSongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mRemoveSongActionPerformed
        Actions.RemoveSong();
    }//GEN-LAST:event_mRemoveSongActionPerformed
    private void bRemoveSongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRemoveSongActionPerformed
        Actions.RemoveSong();
    }//GEN-LAST:event_bRemoveSongActionPerformed
    private void bSongDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSongDownActionPerformed
        Actions.SongDown();
    }//GEN-LAST:event_bSongDownActionPerformed
    private void bSongUPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSongUPActionPerformed
        Actions.SongUp();
    }//GEN-LAST:event_bSongUPActionPerformed
    private void mSongUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSongUpActionPerformed
        Actions.SongUp();
    }//GEN-LAST:event_mSongUpActionPerformed
    private void mSongDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSongDownActionPerformed
        Actions.SongDown();
    }//GEN-LAST:event_mSongDownActionPerformed
    private void mpSongUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpSongUpActionPerformed
        Actions.SongUp();
    }//GEN-LAST:event_mpSongUpActionPerformed
    private void mpSongDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpSongDownActionPerformed
        Actions.SongDown();
    }//GEN-LAST:event_mpSongDownActionPerformed
    private void SongSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SongSliderMouseDragged
        Actions.SliderMouse();
    }//GEN-LAST:event_SongSliderMouseDragged
    private void SongSliderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SongSliderMouseClicked
        Actions.SliderMouse();
    }//GEN-LAST:event_SongSliderMouseClicked
    private void LST1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST1MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Actions.LSTmouseClick();
        }
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    System.out.println("Double Click");
                    Actions.GoPlay();
        }
    }//GEN-LAST:event_LST1MouseClicked
    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        //Actions.stopp();
        //try { currentLST.clearSelection(); } catch (NullPointerException nn) {}
        Actions.currentPlayer=tabbedPane.getSelectedIndex();
        switch (Actions.currentPlayer) {
            case 0:  
                currentLST=LST1; 
                Actions.currentList3m=Actions.list3m1; 
                Actions.currentBuferList=Actions.BuferList1; 
                Actions.currentM3u=Actions.M3uArray[0]; 
                System.out.println("LST="+tabbedPane.getSelectedIndex()); 
                break;
            case 1:  
                currentLST=LST2; 
                Actions.currentList3m=Actions.list3m2; 
                Actions.currentBuferList=Actions.BuferList2; 
                Actions.currentM3u=Actions.M3uArray[1]; 
                System.out.println("LST="+tabbedPane.getSelectedIndex()); 
                break;
            case 2:  
                currentLST=LST3; 
                Actions.currentList3m=Actions.list3m3; 
                Actions.currentBuferList=Actions.BuferList3; 
                Actions.currentM3u=Actions.M3uArray[2]; 
                System.out.println("LST="+tabbedPane.getSelectedIndex()); 
                break;  
            case 3:  
                currentLST=LST4; 
                Actions.currentList3m=Actions.list3m4; 
                Actions.currentBuferList=Actions.BuferList4; 
                Actions.currentM3u=Actions.M3uArray[3]; 
                System.out.println("LST="+tabbedPane.getSelectedIndex());  
                break; 
            case 4:  
                currentLST=LST5; 
                Actions.currentList3m=Actions.list3m5; 
                Actions.currentBuferList=Actions.BuferList5; 
                Actions.currentM3u=Actions.M3uArray[4]; 
                System.out.println("LST="+tabbedPane.getSelectedIndex()); 
                break;
            case 5:  
                currentLST=LST6; 
                Actions.currentList3m=Actions.list3m6; 
                Actions.currentBuferList=Actions.BuferList6; 
                Actions.currentM3u=Actions.M3uArray[5]; 
                System.out.println("LST="+tabbedPane.getSelectedIndex());  
                break; 
            case 6:  
                currentLST=LST7; 
                Actions.currentList3m=Actions.list3m7; 
                Actions.currentBuferList=Actions.BuferList7; 
                Actions.currentM3u=Actions.M3uArray[6]; 
                System.out.println("LST="+tabbedPane.getSelectedIndex()); 
                break;                
            default:  
                currentLST=LST1; 
                Actions.currentList3m=Actions.list3m1; 
                Actions.currentBuferList=Actions.BuferList1; 
                Actions.currentM3u=Actions.M3uArray[0];   
        }
    }//GEN-LAST:event_tabbedPaneStateChanged
    private void LST2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST2MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Actions.LSTmouseClick();
        }
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    System.out.println("Double Click");
                    Actions.GoPlay();
        }        
    }//GEN-LAST:event_LST2MouseClicked
    private void LST3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST3MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Actions.LSTmouseClick();
        }
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    System.out.println("Double Click");
                    Actions.GoPlay();
        }        
    }//GEN-LAST:event_LST3MouseClicked
    private void LST4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST4MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Actions.LSTmouseClick();
        }
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    System.out.println("Double Click");
                    Actions.GoPlay();
        }        
    }//GEN-LAST:event_LST4MouseClicked
    private void LST5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST5MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Actions.LSTmouseClick();
        }
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    System.out.println("Double Click");
                    Actions.GoPlay();
        }        
    }//GEN-LAST:event_LST5MouseClicked
    private void LST1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST1MouseReleased
        checkPopup(evt);
    }//GEN-LAST:event_LST1MouseReleased
    private void LST2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST2MouseReleased
        checkPopup(evt);
    }//GEN-LAST:event_LST2MouseReleased
    private void LST3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST3MouseReleased
        checkPopup(evt);
    }//GEN-LAST:event_LST3MouseReleased
    private void LST4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST4MouseReleased
        checkPopup(evt);
    }//GEN-LAST:event_LST4MouseReleased
    private void LST5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST5MouseReleased
        checkPopup(evt);
    }//GEN-LAST:event_LST5MouseReleased
    private void mClassicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mClassicActionPerformed
        mLoop.setSelected(false);
        mShuffle.setSelected(false);
        mRepeat.setSelected(false);
        mClassic.setSelected(true);                        
        bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/next-forward.png")));
        bMode.setToolTipText("Current Mode is No Repeat");        
        Actions.currentMode="classic";
    }//GEN-LAST:event_mClassicActionPerformed
    private void mLoopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mLoopActionPerformed
        ModeSelectLoop();
    }//GEN-LAST:event_mLoopActionPerformed
    private void mShuffleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mShuffleActionPerformed
        ModeSelectShuffle();
    }//GEN-LAST:event_mShuffleActionPerformed
    private void mRepeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mRepeatActionPerformed
        ModeSelectRepeat ();
    }//GEN-LAST:event_mRepeatActionPerformed
    private void mpRepeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpRepeatActionPerformed
        ModeSelectRepeat ();
    }//GEN-LAST:event_mpRepeatActionPerformed
    private void mrMistSilverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrMistSilverActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
    }//GEN-LAST:event_mrMistSilverActionPerformed
    private void mrTwilightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTwilightActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel");
    }//GEN-LAST:event_mrTwilightActionPerformed
    private void mrGraphiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrGraphiteActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
    }//GEN-LAST:event_mrGraphiteActionPerformed
    private void mrGraphiteGlassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrGraphiteGlassActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel");
    }//GEN-LAST:event_mrGraphiteGlassActionPerformed
    private void mrEmeraldDuskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrEmeraldDuskActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel");
    }//GEN-LAST:event_mrEmeraldDuskActionPerformed
    private void mrChallengerDeepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrChallengerDeepActionPerformed
        this.setLF("org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel");
    }//GEN-LAST:event_mrChallengerDeepActionPerformed
    private void mOpenURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mOpenURLActionPerformed
        LoadURL lu=new LoadURL(frame,true);
        lu.setVisible(true);
    }//GEN-LAST:event_mOpenURLActionPerformed
    private void mProxyCfgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mProxyCfgActionPerformed
        ProxyCfg pc=new ProxyCfg(frame,true);
        pc.setVisible(true);
    }//GEN-LAST:event_mProxyCfgActionPerformed
    private void mpCopyPathClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpCopyPathClipboardActionPerformed
        Actions.CopyPathURLtoClipboard();
    }//GEN-LAST:event_mpCopyPathClipboardActionPerformed
    private void mCopyPathClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mCopyPathClipboardActionPerformed
        Actions.CopyPathURLtoClipboard();
    }//GEN-LAST:event_mCopyPathClipboardActionPerformed
    private void mid3info1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mid3info1ActionPerformed
        Actions act=new Actions();
        act.infoMpgaMp123();
    }//GEN-LAST:event_mid3info1ActionPerformed
    private void mRepeatTrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mRepeatTrackActionPerformed
        ModeSelectRepeat ();
    }//GEN-LAST:event_mRepeatTrackActionPerformed
    private void mpPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpPreviousActionPerformed
        Actions.PlayPrevious();
    }//GEN-LAST:event_mpPreviousActionPerformed
    private void mpNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpNextActionPerformed
        Actions.PlayNext();
    }//GEN-LAST:event_mpNextActionPerformed
    private void bOpenURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOpenURLActionPerformed
        LoadURL lu=new LoadURL(frame,true);
        lu.setVisible(true);
    }//GEN-LAST:event_bOpenURLActionPerformed
    private void mPasteClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mPasteClipboardActionPerformed
        Actions.PastePathURLfromClipboard();
    }//GEN-LAST:event_mPasteClipboardActionPerformed
    private void mpPasteClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpPasteClipboardActionPerformed
        Actions.PastePathURLfromClipboard();
    }//GEN-LAST:event_mpPasteClipboardActionPerformed
    private void bCopyToClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCopyToClipboardActionPerformed
        Actions.CopyPathURLtoClipboard();
    }//GEN-LAST:event_bCopyToClipboardActionPerformed
    private void bPasteFromClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPasteFromClipboardActionPerformed
        Actions.PastePathURLfromClipboard();
    }//GEN-LAST:event_bPasteFromClipboardActionPerformed
    private void mShowClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mShowClipboardActionPerformed
        Actions.showClipboard();    
    }//GEN-LAST:event_mShowClipboardActionPerformed
    private void mpShowClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpShowClipboardActionPerformed
        Actions.showClipboard();
    }//GEN-LAST:event_mpShowClipboardActionPerformed
    private void bModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModeActionPerformed
        ModeSelect();
    }//GEN-LAST:event_bModeActionPerformed
    private void mrTinyDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinyDefaultActionPerformed
        Actions.currentTheme="lib/themes/Default.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
    }//GEN-LAST:event_mrTinyDefaultActionPerformed
    private void mrTinyForestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinyForestActionPerformed
        Actions.currentTheme="lib/themes/Forest.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
    }//GEN-LAST:event_mrTinyForestActionPerformed
    private void mrTinyGoldenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinyGoldenActionPerformed
        Actions.currentTheme="lib/themes/Golden.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
    }//GEN-LAST:event_mrTinyGoldenActionPerformed
    private void mrTinyPlasticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinyPlasticActionPerformed
        Actions.currentTheme="lib/themes/Plastic.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
    }//GEN-LAST:event_mrTinyPlasticActionPerformed
    private void mrTinySilverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinySilverActionPerformed
        Actions.currentTheme="lib/themes/Silver.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
    }//GEN-LAST:event_mrTinySilverActionPerformed
    private void mrSquarenessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrSquarenessActionPerformed
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
        Actions.currentTheme="";
        this.setLF("net.beeger.squareness.SquarenessLookAndFeel");
    }//GEN-LAST:event_mrSquarenessActionPerformed
    private void mrLipstikDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrLipstikDefaultActionPerformed
        LipstikLookAndFeel.setMyCurrentTheme(new DefaultTheme());
        Actions.currentTheme="LipstikDefault";
        this.setLF("com.lipstikLF.LipstikLookAndFeel");
    }//GEN-LAST:event_mrLipstikDefaultActionPerformed
    private void mrLipstikKlearlooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrLipstikKlearlooksActionPerformed
        LipstikLookAndFeel.setMyCurrentTheme(new KlearlooksTheme());
        Actions.currentTheme="LipstikKlearlooks";
        this.setLF("com.lipstikLF.LipstikLookAndFeel");
    }//GEN-LAST:event_mrLipstikKlearlooksActionPerformed
    private void mrLipstikLightGrayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrLipstikLightGrayActionPerformed
        LipstikLookAndFeel.setMyCurrentTheme(new LightGrayTheme());
        Actions.currentTheme="LipstikLightGray";
        this.setLF("com.lipstikLF.LipstikLookAndFeel");
    }//GEN-LAST:event_mrLipstikLightGrayActionPerformed
    private void mrTinyCyanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinyCyanActionPerformed
        Actions.currentTheme="lib/themes/My_Cyan.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
    }//GEN-LAST:event_mrTinyCyanActionPerformed
    private void mrTinyYellowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinyYellowActionPerformed
        Actions.currentTheme="lib/themes/My_Yellow.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");        
    }//GEN-LAST:event_mrTinyYellowActionPerformed
    private void mrTinyAquaMarineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinyAquaMarineActionPerformed
        Actions.currentTheme="lib/themes/My_AquaMarine.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
    }//GEN-LAST:event_mrTinyAquaMarineActionPerformed
    private void mrTinyMagentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinyMagentaActionPerformed
        Actions.currentTheme="lib/themes/My_Magenta.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
    }//GEN-LAST:event_mrTinyMagentaActionPerformed
    private void mrTinyGreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrTinyGreenActionPerformed
        Actions.currentTheme="lib/themes/My_Green.theme";
        this.setLF("de.muntjak.tinylookandfeel.TinyLookAndFeel");
    }//GEN-LAST:event_mrTinyGreenActionPerformed
    private void mConverterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mConverterActionPerformed
        try { new WavToMp3_Thread(); } catch (NullPointerException ne) {}
    }//GEN-LAST:event_mConverterActionPerformed
    private void mChangeLFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mChangeLFActionPerformed
        changeLF();
    }//GEN-LAST:event_mChangeLFActionPerformed
    private void mSplitterTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSplitterTimeActionPerformed
        new Splitter_Thread();
    }//GEN-LAST:event_mSplitterTimeActionPerformed
    private void mTagEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTagEditorActionPerformed
        new TagEdit_Thread();
    }//GEN-LAST:event_mTagEditorActionPerformed
    private void bConverterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConverterActionPerformed
        new WavToMp3_Thread();
    }//GEN-LAST:event_bConverterActionPerformed
    private void bTagEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTagEditorActionPerformed
        new TagEdit_Thread();
    }//GEN-LAST:event_bTagEditorActionPerformed
    private void bSplittersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSplittersActionPerformed
        SplitterSelect ss=new SplitterSelect(frame,false);
        ss.setLocationRelativeTo(frame);
        ss.setVisible(true);        
    }//GEN-LAST:event_bSplittersActionPerformed
    private void bShowClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bShowClipboardActionPerformed
        Actions.showClipboard();
    }//GEN-LAST:event_bShowClipboardActionPerformed
    private void mrLiquidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mrLiquidActionPerformed
        Actions.currentTheme="";
        this.setLF("com.birosoft.liquid.LiquidLookAndFeel");
    }//GEN-LAST:event_mrLiquidActionPerformed
    private void mSplitterSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSplitterSizeActionPerformed
        splitGui splitter=new splitGui();
        splitter.setVisible(true);
    }//GEN-LAST:event_mSplitterSizeActionPerformed
    private void bFlac2WavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFlac2WavActionPerformed
        WavAndFlac waf=new WavAndFlac(frame,false);
        waf.setLocationRelativeTo(frame);
        waf.setVisible(true); 
    }//GEN-LAST:event_bFlac2WavActionPerformed
    private void bAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAboutActionPerformed
        Actions act=new Actions();
        act.about();
    }//GEN-LAST:event_bAboutActionPerformed
    private void bChangeSkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bChangeSkinActionPerformed
        changeLF();
    }//GEN-LAST:event_bChangeSkinActionPerformed
    private void LST6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST6MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Actions.LSTmouseClick();
        }
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    System.out.println("Double Click");
                    Actions.GoPlay();
        }        
    }//GEN-LAST:event_LST6MouseClicked
    private void LST7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LST7MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Actions.LSTmouseClick();
        }
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    System.out.println("Double Click");
                    Actions.GoPlay();
        }        
    }//GEN-LAST:event_LST7MouseClicked

    private void bDonateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDonateActionPerformed
        Donate dd=new Donate(frame,true);
        dd.setVisible(true);
    }//GEN-LAST:event_bDonateActionPerformed

    private void mDonateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDonateActionPerformed
        Donate dd=new Donate(frame,true);
        dd.setVisible(true);  
    }//GEN-LAST:event_mDonateActionPerformed

    private void mDeleteFromFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDeleteFromFSActionPerformed
        Actions.deleteTrackFromFS(LoadFileMp3.putf);
    }//GEN-LAST:event_mDeleteFromFSActionPerformed

    private void mpDeleteFromFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mpDeleteFromFSActionPerformed
        Actions.deleteTrackFromFS(LoadFileMp3.putf);
    }//GEN-LAST:event_mpDeleteFromFSActionPerformed

    private void mcbEffectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mcbEffectsActionPerformed
        if (Actions.useEffects.equals("false"))
        {
            Actions.useEffects="true";
            Actions.enableEffects();
            mcbEffects.setSelected(true);
        }
        else
        {
            Actions.useEffects="false";
            Actions.disableEffects();
            mcbEffects.setSelected(false);
        }
    }//GEN-LAST:event_mcbEffectsActionPerformed

    private void mVideoPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mVideoPlayerActionPerformed
        try {
            new jv_player(null);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SJmp3gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mVideoPlayerActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        WavAndFlac waf=new WavAndFlac(frame,false);
        waf.setLocationRelativeTo(frame);
        waf.setVisible(true); 
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                 javax.swing.UIManager.setLookAndFeel(info.getClassName()); 
                 break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SJmp3gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SJmp3gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SJmp3gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SJmp3gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame=new SJmp3gui();
                //frame.setShape(new RoundRectangle2D.Double(0,0,frame.getWidth()+15,frame.getHeight()+15,15,15));
                frame.setVisible(true);
                frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
                    //Toolkit.getDefaultToolkit().setDynamicLayout(true);
                    //System.setProperty("sun.awt.noerasebackground", "true");                
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
  //              JMenu.setDefaultLookAndFeelDecorated(true);                
                //UIManager.put(SubstanceLookAndFeel.SHOW_EXTRA_WIDGETS, Boolean.TRUE);
                AppCfgXerces.Load();
                //AppCfgJDOM.loadCFG();
                frame.MixerInit();
                Actions.NP = new Next_Thread();
                frame.setLF(Actions.currentLAF);
                Actions.InstallLF();
                //System.out.println(ControlSound.getMixers());
                //System.out.println(ControlSound.getHierarchyInfo());
                //LunaLookAndFeel.setTheme("LunaDefaultTheme");
                //BernsteinLookAndFeel.setTheme(new BernsteinDefaultTheme());
                //SeaGlassLookAndFeel.
                //OfficeXPLookAndFeel.THEME_BLUE;
                //frame.setLF("org.fife.plaf.OfficeXP.OfficeXPLookAndFeel");
                //PlasticLookAndFeel.setPlasticTheme(new SkyYellow());                
                //frame.setLF("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
                //SquarenessLookAndFeel.setCurrentSquarenessTheme(new DefaultSquarenessTheme());
                //frame.setLF("net.beeger.squareness.SquarenessLookAndFeel");
                //frame.setLF("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
                //frame.setLF("net.sourceforge.napkinlaf.NapkinLookAndFeel");
                ////////////File propertiesFile = new File("cfg/log4j.properties");
                ////////////PropertyConfigurator.configure(propertiesFile.toString());
                //PropertyConfigurator.configure("cfg/log4j.properties");
                DOMConfigurator.configure("cfg/log4j.xml");
                //j4log.log(Level.INFO,"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");                
                //j4log.log(Level.INFO,ControlSound.getMixers());
                //j4log.info(ControlSound.getHierarchyInfo());
                //j4log.warn("log4j - warn");
                switch (Actions.currentMode) {
                    case "loop": 
                        mLoop.setSelected(true);
                        mShuffle.setSelected(false);
                        mRepeat.setSelected(false);
                        mClassic.setSelected(false);                        
                        bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/loop-orange.png")));
                        bMode.setToolTipText("Current Mode is Loop (Repeat List)");
                        break;
                    case "shuffle": 
                        mLoop.setSelected(false);
                        mShuffle.setSelected(true);
                        mRepeat.setSelected(false);
                        mClassic.setSelected(false);                        
                        bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/media_playlist_shuffle.png")));
                        bMode.setToolTipText("Current Mode is Shuffle (Random select)");
                        break;
                    case "repeat": 
                        mLoop.setSelected(false);
                        mShuffle.setSelected(false);
                        mRepeat.setSelected(true);
                        mClassic.setSelected(false);                        
                        bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/One-24.png")));
                        bMode.setToolTipText("Current Mode is Repeat Track");
                        break;
                    case "classic": 
                        mLoop.setSelected(false);
                        mShuffle.setSelected(false);
                        mRepeat.setSelected(false);
                        mClassic.setSelected(true);                        
                        bMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/next-forward.png")));
                        bMode.setToolTipText("Current Mode is No Repeat");
                        break;                        
                }
                if (Actions.useEffects.equals("false"))
                    { Actions.disableEffects(); mcbEffects.setSelected(false); }
                else
                    { Actions.enableEffects();  mcbEffects.setSelected(true);  }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Action;
    private javax.swing.JMenu File;
    private javax.swing.JMenu Help;
    public static javax.swing.JList LST1;
    public static javax.swing.JList LST2;
    public static javax.swing.JList LST3;
    public static javax.swing.JList LST4;
    public static javax.swing.JList LST5;
    public static javax.swing.JList LST6;
    public static javax.swing.JList LST7;
    public static javax.swing.JSlider SongSlider;
    public static javax.swing.JTextField TimeTFcur;
    public static javax.swing.JTextField TimeTFend;
    private javax.swing.JButton bAbout;
    private javax.swing.JButton bAddList;
    private javax.swing.JButton bChangeSkin;
    private javax.swing.JButton bConverter;
    private javax.swing.JButton bCopyToClipboard;
    private javax.swing.JButton bDonate;
    public static javax.swing.JButton bFastBackward;
    public static javax.swing.JButton bFastForward;
    private javax.swing.JButton bFlac2Wav;
    public static javax.swing.JButton bMode;
    private javax.swing.JButton bNext;
    private javax.swing.JButton bOpenFile;
    private javax.swing.JButton bOpenFolder;
    private javax.swing.JButton bOpenURL;
    private javax.swing.JButton bPasteFromClipboard;
    public static javax.swing.JButton bPause;
    private javax.swing.JButton bPlay;
    private javax.swing.JButton bPrevious;
    private javax.swing.JButton bRemoveList;
    private javax.swing.JButton bRemoveSong;
    private javax.swing.JButton bSaveConfig;
    private javax.swing.JButton bSavePL;
    private javax.swing.JButton bShowClipboard;
    private javax.swing.JButton bSongDown;
    private javax.swing.JButton bSongUP;
    public static javax.swing.JButton bSoundMixer;
    private javax.swing.JButton bSplitters;
    private javax.swing.JButton bStop;
    private javax.swing.JButton bTagEditor;
    public static javax.swing.JButton bid3Info;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JMenuItem mAbout;
    public static javax.swing.JMenu mBusinessSkins;
    private javax.swing.JMenuItem mChangeLF;
    public static javax.swing.JRadioButtonMenuItem mClassic;
    private javax.swing.JMenuItem mConverter;
    private javax.swing.JMenuItem mCopyPathClipboard;
    public static javax.swing.JMenu mCremeSkins;
    private javax.swing.JMenuItem mDeleteFromFS;
    private javax.swing.JMenuItem mDonate;
    public static javax.swing.JMenu mDustSkins;
    public static javax.swing.JMenuItem mFastBackward;
    public static javax.swing.JMenuItem mFastForward;
    private javax.swing.JMenuItem mFileOpen;
    private javax.swing.JMenuItem mFolderOpen;
    public static javax.swing.JMenu mGraphiteSkins;
    public static javax.swing.JMenu mLipstik;
    private javax.swing.JMenuItem mListAdd;
    private javax.swing.JMenu mListEdit;
    public static javax.swing.JRadioButtonMenuItem mLoop;
    public static javax.swing.JMenu mMistSkins;
    public static javax.swing.JMenu mNebulaSkins;
    private javax.swing.JMenuItem mNext;
    public static javax.swing.JMenu mOfficeSkins;
    private javax.swing.JMenuItem mOpenURL;
    private javax.swing.JMenu mOptions;
    private javax.swing.JMenuItem mPasteClipboard;
    public static javax.swing.JMenuItem mPause;
    private javax.swing.JMenuItem mPlay;
    public static javax.swing.JPopupMenu mPopup;
    private javax.swing.JMenuItem mPrevious;
    private javax.swing.JMenuItem mProxyCfg;
    private javax.swing.JMenuItem mQuit;
    private javax.swing.JMenuItem mRemoveAll;
    private javax.swing.JMenuItem mRemoveSong;
    public static javax.swing.JRadioButtonMenuItem mRepeat;
    private javax.swing.JMenuItem mRepeatTrack;
    private javax.swing.JMenuItem mSaveCfg;
    private javax.swing.JMenuItem mSavePlayList;
    private javax.swing.JMenuItem mShowClipboard;
    public static javax.swing.JRadioButtonMenuItem mShuffle;
    public static javax.swing.JMenu mSkin;
    private javax.swing.JMenuItem mSongDown;
    private javax.swing.JMenuItem mSongUp;
    public static javax.swing.JMenuItem mSoundVolume;
    private javax.swing.JMenuItem mSplitterSize;
    private javax.swing.JMenuItem mSplitterTime;
    private javax.swing.JMenuItem mStop;
    private javax.swing.JMenu mSubstance;
    private javax.swing.JMenuItem mTagEditor;
    private javax.swing.JMenu mTiny;
    private javax.swing.JMenu mUtils;
    public static javax.swing.JMenuItem mVideoPlayer;
    public static javax.swing.JCheckBoxMenuItem mcbEffects;
    private javax.swing.JMenuItem mid3info;
    private javax.swing.JMenuItem mid3info1;
    private javax.swing.JMenuItem mp3info;
    private javax.swing.JMenuItem mpCopyPathClipboard;
    private javax.swing.JMenuItem mpDeleteFromFS;
    public static javax.swing.JMenuItem mpFastBackward;
    public static javax.swing.JMenuItem mpFastForward;
    private javax.swing.JMenuItem mpNext;
    private javax.swing.JMenuItem mpPasteClipboard;
    public static javax.swing.JMenuItem mpPause;
    private javax.swing.JMenuItem mpPlay;
    private javax.swing.JMenuItem mpPrevious;
    private javax.swing.JMenuItem mpRemoveSong;
    private javax.swing.JMenuItem mpRepeat;
    private javax.swing.JMenuItem mpShowClipboard;
    private javax.swing.JMenuItem mpSongDown;
    private javax.swing.JMenuItem mpSongUp;
    private javax.swing.JMenuItem mpStop;
    public static javax.swing.JRadioButtonMenuItem mrAutumn;
    public static javax.swing.JRadioButtonMenuItem mrBusiness;
    public static javax.swing.JRadioButtonMenuItem mrBusinessBlack;
    public static javax.swing.JRadioButtonMenuItem mrBusinessBlue;
    public static javax.swing.JRadioButtonMenuItem mrCerulean;
    public static javax.swing.JRadioButtonMenuItem mrChallengerDeep;
    public static javax.swing.JRadioButtonMenuItem mrCreme;
    public static javax.swing.JRadioButtonMenuItem mrCremeCoffee;
    public static javax.swing.JRadioButtonMenuItem mrDust;
    public static javax.swing.JRadioButtonMenuItem mrDustCoffee;
    public static javax.swing.JRadioButtonMenuItem mrEmeraldDusk;
    public static javax.swing.JRadioButtonMenuItem mrGemini;
    public static javax.swing.JRadioButtonMenuItem mrGraphite;
    public static javax.swing.JRadioButtonMenuItem mrGraphiteAqua;
    public static javax.swing.JRadioButtonMenuItem mrGraphiteGlass;
    public static javax.swing.JRadioButtonMenuItem mrLipstikDefault;
    public static javax.swing.JRadioButtonMenuItem mrLipstikKlearlooks;
    public static javax.swing.JRadioButtonMenuItem mrLipstikLightGray;
    public static javax.swing.JRadioButtonMenuItem mrLiquid;
    public static javax.swing.JRadioButtonMenuItem mrMagellan;
    public static javax.swing.JRadioButtonMenuItem mrMariner;
    public static javax.swing.JRadioButtonMenuItem mrMetalOcean;
    public static javax.swing.JRadioButtonMenuItem mrMistAqua;
    public static javax.swing.JRadioButtonMenuItem mrMistSilver;
    public static javax.swing.JRadioButtonMenuItem mrModerate;
    public static javax.swing.JRadioButtonMenuItem mrNebula;
    public static javax.swing.JRadioButtonMenuItem mrNebulaBrickWall;
    public static javax.swing.JRadioButtonMenuItem mrOfficeBlack2007;
    public static javax.swing.JRadioButtonMenuItem mrOfficeBlue2007;
    public static javax.swing.JRadioButtonMenuItem mrOfficeSilver2007;
    public static javax.swing.JRadioButtonMenuItem mrRaven;
    public static javax.swing.JRadioButtonMenuItem mrSahara;
    public static javax.swing.JRadioButtonMenuItem mrSquareness;
    public static javax.swing.JRadioButtonMenuItem mrTinyAquaMarine;
    public static javax.swing.JRadioButtonMenuItem mrTinyCyan;
    public static javax.swing.JRadioButtonMenuItem mrTinyDefault;
    public static javax.swing.JRadioButtonMenuItem mrTinyForest;
    public static javax.swing.JRadioButtonMenuItem mrTinyGolden;
    public static javax.swing.JRadioButtonMenuItem mrTinyGreen;
    public static javax.swing.JRadioButtonMenuItem mrTinyMagenta;
    public static javax.swing.JRadioButtonMenuItem mrTinyPlastic;
    public static javax.swing.JRadioButtonMenuItem mrTinySilver;
    public static javax.swing.JRadioButtonMenuItem mrTinyYellow;
    public static javax.swing.JRadioButtonMenuItem mrTwilight;
    public static javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JToolBar tbInfo;
    private javax.swing.JToolBar tbListEditor;
    private javax.swing.JToolBar tbNiz;
    private javax.swing.JToolBar tbOpen;
    private javax.swing.JToolBar tbOptions;
    private javax.swing.JToolBar tbTrackActions;
    private javax.swing.JToolBar tbUtils;
    private javax.swing.JToolBar tbVerh;
    // End of variables declaration//GEN-END:variables
}

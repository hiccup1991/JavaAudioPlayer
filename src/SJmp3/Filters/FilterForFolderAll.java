package SJmp3.Filters;

import java.io.File;
import java.io.FilenameFilter;
import javax.swing.filechooser.FileFilter;

public class FilterForFolderAll extends FileFilter implements FilenameFilter{

    /** Singleton audio file filter instance */
    public static final FilterForFolderAll INSTANCE = new FilterForFolderAll();
    public static final String[] AudioFileExt = {".mp1",".mp2",".mp3",".mpga",".wav",".au","aif",".aiff",".aifc",".snd",".flac",".mid"};

    public FilterForFolderAll() {    }

    @Override
    public boolean accept(File dir, String name) {
        File file = new File(dir, name);
        if (file.exists() && file.isDirectory()) {
            return true;
        }

        String s = name.toLowerCase();
        for (String ext : AudioFileExt) {
            if (s.endsWith(ext)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }

        String s = file.getName().toLowerCase();
        for (String ext : AudioFileExt) {
            if (s.endsWith(ext)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        StringBuilder description = new StringBuilder("Recursive Search for:");
        if (AudioFileExt.length > 0) {
            description.append(" (");
            for (String ext : AudioFileExt) {
                description.append(" *").append(ext);
            }
            description.append(" )");
        }
        return description.toString();
    }
}    


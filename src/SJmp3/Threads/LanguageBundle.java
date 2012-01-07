package SJmp3.Threads;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author Besmir Beqiri
 */
public class LanguageBundle {

    private static final String BASE_NAME = "xtrememp.resources.i18n.xtrememp_i18n";
    private static Locale languageLocale;
    private static ResourceBundle languageBundle;

    /**
     * Gets a string for the given key from the language resource bundle
     * containing a translated version specified by the selected language.
     *
     * @param key the key for the desired string
     * @throws NullPointerException if <code>key</code> is <code>null</code>
     * @return the string for the given key
     */
    public static String getString(String key) {
        if (languageBundle != null) {
            String result;
            try {
                result = languageBundle.getString(key);
            } catch (MissingResourceException ex) {
                ex.printStackTrace();
                return key;
            }
            return result;
        }
        return key;
    }

    /**
     * Sets the language locale.
     * 
     * @param locale the locale
     */
    public static void setLanguage(Locale locale) {
        if (locale == null) {
            languageBundle = ResourceBundle.getBundle(BASE_NAME, Utilities.getSystemLocale());
        } else {
            languageBundle = ResourceBundle.getBundle(BASE_NAME, locale);
        }
        languageLocale = languageBundle.getLocale();
    }

    /**
     * Return the locale of the selected language.
     * 
     * @return the language locale
     */
    public static Locale getLanguageLocale() {
        return languageLocale;
    }
}

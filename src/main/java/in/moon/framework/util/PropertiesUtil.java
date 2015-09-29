package in.moon.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Utility to read properties file.
 *
 * Created by miaoever on 9/29/15.
 */
public class PropertiesUtil {

    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found.");
            }

            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            //TODO LOGGER
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    //TODO LOGGER
                }
            }
        }

        return props;
    }

    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    public static String getString(Properties props, String key, String defaultVal) {
            return props.getProperty(key, defaultVal);
    }

    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    public static int getInt(Properties props, String key, int defaultVal) {
        return ConvertUtil.convert(props.getProperty(key)).toIntByDefault(defaultVal);
    }

    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    public static boolean getBoolean(Properties props, String key, boolean defaultVal) {
        return ConvertUtil.convert(props.getProperty(key)).toBoolByDefault(defaultVal);
    }
}

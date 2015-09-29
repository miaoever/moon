package in.moon.framework.core;

import in.moon.framework.ConfigConstant;
import in.moon.framework.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
/** Load config from properties file.
 *
 * Created by miaoever on 9/29/15.
 */
public class PropertiesConfigLoader {

    private static final Properties CONFIG = PropertiesUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static HashMap<String, String> load() {
        Set<String> properties = CONFIG.stringPropertyNames();
        HashMap<String, String> kv = new HashMap<String, String>();

        if (properties == null) {
            return kv;
        }

        for(String key : properties) {
           kv.put(key, CONFIG.getProperty(key));
        }

        return kv;
    }

    public static String getJdbcDriver() {
        return PropertiesUtil.getString(CONFIG, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropertiesUtil.getString(CONFIG, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUrsername() {
        return PropertiesUtil.getString(CONFIG, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropertiesUtil.getString(CONFIG, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return PropertiesUtil.getString(CONFIG, ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath() {
        return PropertiesUtil.getString(CONFIG, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    public static String getAppAssetPath() {
        return PropertiesUtil.getString(CONFIG, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }
}

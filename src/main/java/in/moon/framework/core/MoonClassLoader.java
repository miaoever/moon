package in.moon.framework.core;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/** Load classed from package
 *
 * Created by miaoever on 9/27/15.
 */
public final class MoonClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoonClassLoader.class);

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className) {
        return loadClass(className, false);
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("Failed to load class: " + className, e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

    public static Set<Class<?>> loadClassFromFiles(String packageName, URL url) {
        String packagePath = url.getPath().replaceAll("%20", " ");
        return loadClassFromFiles(packageName, packagePath);
    }

    public static Set<Class<?>> loadClassFromFiles(String packageName, String packagePath) {
        Set<Class<?>> classes = new HashSet<Class<?>>();

        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class"))
                        || file.isDirectory();
            }
        });

        if (files == null) {
            return new HashSet<Class<?>>();
        }

        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                classes.add(loadClass(className));
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                classes.addAll(loadClassFromFiles(subPackageName, subPackagePath));
            }
        }

        return classes;
    }

    public static Set<Class<?>> loadClassFromJar(String packageName, URL url) {
        Set<Class<?>> classes = new HashSet<Class<?>>();

        try {
            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            if (jarURLConnection == null) {
                return new HashSet<Class<?>>();
            }

            JarFile jarFile = jarURLConnection.getJarFile();
            if (jarFile == null) {
                return new HashSet<Class<?>>();
            }

            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while ( (jarEntries.hasMoreElements())) {
                JarEntry jarEntry = jarEntries.nextElement();
                String jarEntryName = jarEntry.getName();
                if (jarEntryName.endsWith(".class")) {
                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                    classes.add(loadClass(className));
                }
            }

        } catch (Exception e) {
            LOGGER.error("Faild to load classed from jar: " + url.getPath(), e);
            throw new RuntimeException(e);
        }

        return classes;
    }

    public static Set<Class<?>> getClasses(String packageName) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url == null) {
                    continue;
                }

                if ("file".equals(url.getProtocol())) {
                    classes.addAll(loadClassFromFiles(packageName, url));
                } else  if ("jar".equals(url.getProtocol())) {
                    classes.addAll(loadClassFromJar(packageName, url));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed to get classes in Package: " + packageName, e);
            throw new RuntimeException(e);
        }
        return classes;
    }
}

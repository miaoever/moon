package in.moon.framework.core;

import junit.framework.TestCase;

import java.util.Set;

/**
 * Created by miaoever on 9/28/15.
 */
public class MoonClassLoaderTest extends TestCase {
    public void testClassLoader() throws Exception {
        Set<Class<?>> classes =  MoonClassLoader.getClasses("in.moon.framework");
        for (Class<?> clazz : classes) {
            System.out.println(clazz.getName());
        }

    }
}
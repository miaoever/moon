package in.moon.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by miaoever on 9/29/15.
 */
public class ReflectionUtil {

    public static Object newInstance(Class<?> clazz) {
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            //TODO LOGGER
            throw new RuntimeException(e);
        }

        return instance;
    }

    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object res;
        try {
            method.setAccessible(true);
            res = method.invoke(obj, args);
        } catch (Exception e) {
            //TODO LOGGER
            throw new RuntimeException(e);
        }

        return res;
    }

    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            //TODO LOGGER
            throw new RuntimeException(e);
        }
    }
}

package in.moon.framework.core;

import in.moon.framework.annotation.Controller;
import in.moon.framework.annotation.Service;

import java.util.HashSet;
import java.util.Set;

/** Load beans context
 *
 * Created by miaoever on 9/28/15.
 */
public class MoonContextLoader {

    private static final Set<Class<?>> CLASSES;

    static {
        String basePakcage = PropertiesConfigLoader.getAppBasePackage();
        CLASSES = MoonClassLoader.getClasses(basePakcage);
    }

    public static Set<Class<?>> getClasses() {
        return CLASSES;
    }

    public static Set<Class<?>> getServiceBeans() {
        Set<Class<?>> services = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASSES) {
            if (clazz.isAnnotationPresent(Service.class)) {
                services.add(clazz);
            }
        }

        return services;
    }

    public static Set<Class<?>> getControllerBeans() {
        Set<Class<?>> controllers = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASSES) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                controllers.add(clazz);
            }
        }

        return controllers;
    }

    public static Set<Class<?>> getBeans() {
        Set<Class<?>> beans = new HashSet<Class<?>>();
        beans.addAll(getServiceBeans());
        beans.addAll(getControllerBeans());

        return beans;
    }
}

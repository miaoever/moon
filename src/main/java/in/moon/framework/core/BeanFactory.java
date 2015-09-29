package in.moon.framework.core;

import in.moon.framework.annotation.Inject;
import in.moon.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** Bean factory class to load the beans and do IOC
 *
 * Created by miaoever on 9/29/15.
 */
public final class BeanFactory {

    private static final Map<Class<?>, Object> BEAN_REPO = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beans = MoonContextLoader.getBeans();
        for (Class<?> bean : beans) {
            Object obj = ReflectionUtil.newInstance(bean);
            BEAN_REPO.put(bean, obj);
        }

        for (Map.Entry<Class<?>, Object> beanEntry : BEAN_REPO.entrySet()) {
            Class<?> bean = beanEntry.getKey();
            Object beanInstance = beanEntry.getValue();

            Field[] beanFields = bean.getFields();

            for (Field field : beanFields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> fieldClass = field.getType();
                    Object fieldInstance = BEAN_REPO.get(fieldClass);

                    if (fieldInstance != null) {
                        ReflectionUtil.setField(beanInstance, field, fieldInstance);
                    }
                }
            }
        }
    }

    public static Map<Class<?>, Object> getBeanRepo() {
        return BEAN_REPO;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> bean) {
        if (!BEAN_REPO.containsKey(bean)) {
            throw new RuntimeException("Can not get the bean: " + bean);
        }

        return (T) BEAN_REPO.get(bean);
    }



}

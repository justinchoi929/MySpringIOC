package top.justinchoi.factory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public class BeanFactory {
    private static Properties properties;
    // 利用缓存思想实现单例
    private static HashMap<String, Object> cache = new HashMap<>();
    // 获取配置文件中的信息（静态代码块只执行一次）
    static {
        properties = new Properties();
        try {
            properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("factory.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getDao(String beanName){
        // 先判断缓存中是否存在bean
        if (!cache.containsKey(beanName)) {
            // 加锁双重检测保证多线程下的线程安全
            synchronized (BeanFactory.class) {
                if (!cache.containsKey(beanName)) {
                    // 不存在则将bean存入缓存
                    // 利用反射机制获取对象
                    try {
                        // 得到全类名
                        String value = properties.getProperty(beanName);
                        Class clazz = Class.forName(value);
                        Object object = clazz.getConstructor(null).newInstance(null);
                        cache.put(beanName, object);
                        return object;
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
        return cache.get(beanName);
    }
}

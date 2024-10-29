package top.justinchoi.factory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class BeanFactory {
    private static Properties properties;
    // 获取配置文件中的信息（静态代码块只执行一次）
    static {
        properties = new Properties();
        try {
            properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("factory.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getDao(){
        // 得到全类名
        String value = properties.getProperty("helloDao");
        // 利用反射机制获取对象
        try {
            Class clazz = Class.forName(value);
            Object object = clazz.getConstructor(null).newInstance(null);
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

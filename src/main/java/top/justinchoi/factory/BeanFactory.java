package top.justinchoi.factory;

import top.justinchoi.dao.HelloDao;
import top.justinchoi.dao.impl.HelloDaoImpl;

import java.io.IOException;
import java.util.Properties;

public class BeanFactory {
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("factory.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HelloDao getDao(){
        // 得到全类名
        String helloDao = properties.getProperty("HelloDao");

        // 利用反射机制获取对象
        return new HelloDaoImpl();
    }
}

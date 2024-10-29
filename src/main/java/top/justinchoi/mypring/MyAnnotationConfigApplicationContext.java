package top.justinchoi.mypring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyAnnotationConfigApplicationContext {

    // 创建ioc容器缓存
    private Map<String, Object> ioc = new HashMap<>();

    // 自定义一个MyAnnotationConfigApplicationContext，构造器中传入要扫描的包
    public MyAnnotationConfigApplicationContext(String pack) {
        // 遍历包，找到目标类（原材料）
        Set<BeanDefinition> beanDefinitions = findBeanDefinitions(pack);
        // 根据原材料创建bean
        creatObject(beanDefinitions);
        // 自动装载
        autowiredObject(beanDefinitions);
    }

    public void autowiredObject(Set<BeanDefinition> beanDefinitions){
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            Class<?> clazz = beanDefinition.getBeanClass();
            // 拿到成员方法
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                // 遍历这些成员方法，找到添加了autowired注解的类
                Autowired annotation = declaredField.getAnnotation(Autowired.class);
                if (annotation != null) {
                    Qualifier qualifier = declaredField.getAnnotation(Qualifier.class);
                    if (qualifier != null) {
                        // 使用name注入
                        String beanName = qualifier.value();
                        // Order的bean对象（需要注入的对象）
                        Object bean = getBean(beanName);
                        String fieldName = declaredField.getName();
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        try {
                            Method method = clazz.getMethod(methodName, declaredField.getType());
                            // Account的bean对象（将order注入到Account对象中）
                            Object object = getBean(beanDefinition.getBeanName());
                            method.invoke(object, bean);
                            System.out.println(bean+"---被注入到---"+object+"---中了---");
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        // 使用type注入
                        String fieldName = declaredField.getName();
                        Object bean = getBean(fieldName);
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        try {
                            Method method = clazz.getMethod(methodName, declaredField.getType());
                            Object object = getBean(beanDefinition.getBeanName());
                            method.invoke(object, bean);
                            System.out.println(bean+"---被注入到---"+object+"---中了---");
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) {
        return ioc.get(beanName);
    }

    public void creatObject(Set<BeanDefinition> beanDefinitions) {
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            Class<?> clazz = beanDefinition.getBeanClass();
            String beanName = beanDefinition.getBeanName();
            try {
                // 创建对象
                Object object = clazz.getConstructor().newInstance();
                // 完成属性赋值
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    Value valueAnnotation = declaredField.getAnnotation(Value.class);
                    if (valueAnnotation != null) {
                        // 获取注解的值
                        String value = valueAnnotation.value();
                        // 获取变量名
                        String fieldName = declaredField.getName();
                        // 获取set方法名
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        // 获取方法
                        Method method = clazz.getMethod(methodName, declaredField.getType());
                        // 完成数据类型转换
                        Object val = null;
                        switch (declaredField.getType().getName()) {
                            case "java.lang.Integer":
                                val = Integer.parseInt(value);
                                break;
                            case "java.lang.Long":
                                val = Long.parseLong(value);
                                break;
                            case "java.lang.Double":
                                val = Double.parseDouble(value);
                                break;
                            case "java.lang.Float":
                                val = Float.parseFloat(value);
                                break;
                            case "java.lang.Boolean":
                                val = Boolean.parseBoolean(value);
                                break;
                            default:
                                val = value;
                                break;
                        }
                        method.invoke(object, val);
                    }
                }
                // 存入缓存
                ioc.put(beanName, object);
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

    public Set<BeanDefinition> findBeanDefinitions(String pack) {
        // 获取包下的所有类
        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        Set<Class<?>> classes = MyTools.getClasses(pack);
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()) {
            // 遍历这些类，找到加了注解的类
            Class<?> clazz = iterator.next();
            Component componentAnnotation = clazz.getAnnotation(Component.class);
            if (componentAnnotation != null) {
                // 获取注解的值
                String beanName = componentAnnotation.value();
                if (beanName.isEmpty()) {
                    // 获取类名
                    String className = clazz.getName()
                            .replaceAll(clazz.getPackage().getName() + ".", "");
                    // 将首字母变小写
                    beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
                }
                // 将这些类封装成BeanDefinition，装载到集合中
                beanDefinitions.add(new BeanDefinition(beanName, clazz));
            }
        }
        return beanDefinitions;
    }
}

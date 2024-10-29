package top.justinchoi.spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("top.justinchoi.spring.entity");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.println(applicationContext.getBeanDefinitionCount());
        for(String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
            System.out.println(applicationContext.getBean(beanDefinitionName));
        }
    }
}

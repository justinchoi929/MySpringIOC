package top.justinchoi.mypring.test;
import top.justinchoi.mypring.MyAnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        MyAnnotationConfigApplicationContext myAnnotationConfigApplicationContext =
                new MyAnnotationConfigApplicationContext("top.justinchoi.mypring");
        System.out.println(myAnnotationConfigApplicationContext.getBean("account"));
        System.out.println(myAnnotationConfigApplicationContext.getBean("order"));
    }
}

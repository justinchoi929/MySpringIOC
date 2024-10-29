package top.justinchoi.spring.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Account {
    @Value("1")
    private Integer id;
    @Value("zhangsan")
    private String name;
    @Value("22")
    private String age;
    // Autowire的注解时候后会从IOC容器中寻找Order并自动装载
    @Autowired
    private Order order;
}

package top.justinchoi.spring.entity;

import lombok.Data;
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
}

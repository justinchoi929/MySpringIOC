package top.justinchoi.mypring.entity;

import lombok.Data;
import top.justinchoi.mypring.Autowired;
import top.justinchoi.mypring.Component;
import top.justinchoi.mypring.Qualifier;
import top.justinchoi.mypring.Value;
@Data
@Component
public class Account {
    @Value("1")
    private Integer id;
    @Value("zhangsan")
    private String name;
    @Value("22")
    private String age;

    @Autowired
    @Qualifier("myOrder")
    private Order order;
}

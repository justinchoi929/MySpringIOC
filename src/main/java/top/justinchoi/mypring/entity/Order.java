package top.justinchoi.mypring.entity;
import lombok.Data;
import top.justinchoi.mypring.Component;
import top.justinchoi.mypring.Value;

@Data
@Component
public class Order {
    @Value("xxx123")
    private String orderId;
    @Value("1000.0")
    private Float price;
}

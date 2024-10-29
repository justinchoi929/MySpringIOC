package top.justinchoi.mypring;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BeanDefinition {
    private String beanName;
    private Class beanClass;
}

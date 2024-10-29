package top.justinchoi.service.impl;

import top.justinchoi.dao.HelloDao;
import top.justinchoi.factory.BeanFactory;
import top.justinchoi.service.HelloService;

import java.util.List;

public class HelloServiceImpl implements HelloService {
    public HelloServiceImpl() {
        for (int i = 0; i < 10; i++) {
            System.out.println(BeanFactory.getDao("helloDao"));
        }
    }
    private HelloDao helloDao = (HelloDao) BeanFactory.getDao("helloDao");
    @Override
    public List<String> findAll() {
        return this.helloDao.findAll();
    }
}

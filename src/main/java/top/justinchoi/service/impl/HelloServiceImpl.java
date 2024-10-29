package top.justinchoi.service.impl;

import top.justinchoi.dao.HelloDao;
import top.justinchoi.factory.BeanFactory;
import top.justinchoi.service.HelloService;

import java.util.List;

public class HelloServiceImpl implements HelloService {
    private HelloDao helloDao = (HelloDao) BeanFactory.getDao();
    @Override
    public List<String> findAll() {
        return this.helloDao.findAll();
    }
}

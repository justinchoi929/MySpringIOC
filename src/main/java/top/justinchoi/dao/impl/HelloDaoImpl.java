package top.justinchoi.dao.impl;

import top.justinchoi.dao.HelloDao;

import java.util.Arrays;
import java.util.List;

public class HelloDaoImpl implements HelloDao {
    @Override
    public List<String> findAll() {
        return Arrays.asList("1", "2", "3");
    }
}

package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        //准备返回客户端的数据
        Map<String, Object> maps = new HashMap<>();

        //当前页号
        maps.put("page", page);
        Integer totalCount = userDao.queryCount();
        //总条数
        maps.put("records", totalCount);

        //总页数
        Integer pageCount = 0;
        //总页数
        if (totalCount % rows != 0) {
            pageCount = totalCount / rows + 1;
        } else {
            pageCount = totalCount / rows;
        }
        maps.put("total", pageCount);

        List<User> list = userDao.queryall(page, rows);

        maps.put("rows", list);
        return maps;
    }

    @Override
    public void changeStatus(User user) {
        userDao.changeStatus(user);
    }

    @Override
    public List<User> queryall() {
        List<User> list = userDao.queryalluser();
        return list;
    }

    @Override
    public List<Integer> querySevenDay() {
        List<Integer> list = userDao.querySevenDay();

        return list;
    }

    @Override
    public List<Date> querySeven() {
        List<Date> dates = userDao.querySeven();
        return dates;
    }

    @Override
    public List<Integer> queryUser() {
        List<Integer> list = userDao.queryUser();
        return list;
    }

    @Override
    public List<String> queryProvince() {
        List<String> strings = userDao.queryProvince();
        return strings;

    }
}

package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String, Object> queryAll(Integer page, Integer rows);
    public void changeStatus(User user);
    public List<User> queryall();
    public List<Integer> querySevenDay();
    public List<Date> querySeven();
    public List<Integer> queryUser();
    public List<String> queryProvince();


}

package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserDao {
    int insert(User record);

    int insertSelective(User record);

    public List<User> queryall(@Param("page") Integer page, @Param("rows") Integer rows);

    public Integer queryCount();

    public void changeStatus(User user);
    public List<User> queryalluser();

    public List<Integer> querySevenDay();
    public List<Date> querySeven();

    public List<Integer> queryUser();

    public List<String> queryProvince();

}
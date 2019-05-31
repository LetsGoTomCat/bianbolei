package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AdminDao {
    public Admin QueryAdmin(Admin admin);

    public Admin queryAdminByName(Admin admin);

    public List<Admin> queryAll();
}

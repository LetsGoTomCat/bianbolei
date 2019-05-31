package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    public Admin QueryAdmin(Admin admin);

    public Admin queryAdminByName(Admin admin);

    public Map<String, String> login(String username, String password, HttpSession session, String enCode);
}

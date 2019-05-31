package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminSeviceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Admin QueryAdmin(Admin admin) {
        Admin admin1 = adminDao.QueryAdmin(admin);
        return admin1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Admin queryAdminByName(Admin admin) {
        Admin admin1 = adminDao.queryAdminByName(admin);
        return admin1;
    }

    @Override
    public Map<String, String> login(String username, String password, HttpSession session, String enCode) {
        Admin admin = new Admin();
        admin.setName(username);
        admin.setPassword(password);

        Map<String, String> map = new HashMap<String, String>();

        Admin admin1 = this.queryAdminByName(admin);
        if (admin1 == null) {
            map.put("message", "账号错误");
            return map;
        } else {
            Admin admin2 = this.QueryAdmin(admin);
            if (admin2 == null) {
                map.put("message", "密码错误");
                return map;
            } else {
                String securityCode = (String) session.getAttribute("securityCode");
                if (securityCode.equals(enCode)) {
                    map.put("message", "验证成功");
                    session.setAttribute("login", username);
                    //登陆成功了都，当然要销毁session减轻服务器压力
                    session.removeAttribute(securityCode);
                    return map;
                } else {
                    map.put("message", "验证码错误");
                    return map;
                }
            }

        }
    }

}

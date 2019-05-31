package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/queryAdmin")
    public String queryAdmin(Model model) throws Exception {
        return "index";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, String> login(String username, String password, String enCode, HttpSession session) throws Exception {
        Map<String, String> map = adminService.login(username, password, session, enCode);
        return map;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        session.removeAttribute("login");
        return "redirect:/login/login.jsp";
    }
}

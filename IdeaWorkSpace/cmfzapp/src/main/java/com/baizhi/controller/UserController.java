package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONArray;
import com.baizhi.entity.MapDto;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/queryall")
    Map<String, Object> queryAll(Integer page, Integer rows) throws Exception {
        Map<String, Object> map = userService.queryAll(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Map<String,Object> edit(User user,String oper) throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        if("edit".equals(oper)){
            userService.changeStatus(user);
            map.put("update","修改成功");
        }
        return map;
    }
    @RequestMapping("/easyPoiOut")
    public void easyPoiOut(HttpSession session, HttpServletResponse response) throws Exception{
        List<User> list = userService.queryall();
        for (User user : list) {
            System.out.println(user.getHeadPic());
        }
        String realPath = session.getServletContext().getRealPath("/img/");
        for (User user : list) {
            user.setHeadPic(realPath+user.getHeadPic());

        }
        for (User user : list) {
            System.out.println(user);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("",""),
                User.class, list);

        String encode = URLEncoder.encode("user.xls", "UTF-8");
        response.setHeader("content-disposition","attachment;filename="+encode);
        workbook.write(response.getOutputStream());
    }

    @RequestMapping("/getSevenDay")
    @ResponseBody
    public Map<String,Map> getSevenDay(){
        List<Integer> integers = userService.querySevenDay();

        Map<String,Map> map=new HashMap<>();
        Map<String, Integer> map1=new HashMap<>();
        Map<String, String> map2=new HashMap<>();

        List<Integer> list = userService.querySevenDay();
        map1.put("g",list.get(0));
        map1.put("f",list.get(1));
        map1.put("e",list.get(2));
        map1.put("d",list.get(3));
        map1.put("c",list.get(4));
        map1.put("b",list.get(5));
        map1.put("a",list.get(6));
        map.put("s",map1);

        List<Date> dates = userService.querySeven();
        List<String> list1=new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
        for (Date date : dates) {
            String format = sdf.format(date);
            System.out.println(format);
            list1.add(format);
        }
        map2.put("g",list1.get(0));
        map2.put("f",list1.get(1));
        map2.put("e",list1.get(2));
        map2.put("d",list1.get(3));
        map2.put("c",list1.get(4));
        map2.put("b",list1.get(5));
        map2.put("a",list1.get(6));
        map.put("x",map2);

        return map;
    }

    @ResponseBody
    @RequestMapping("/getUser")
    public List<MapDto> getUser(){
        List<String> strings = userService.queryProvince();
        List<Integer> counts = userService.queryUser();
        List<MapDto> list=new ArrayList<>();
        for(int i=0;i<counts.size();i++){
            MapDto md=new MapDto();
            md.setName(strings.get(i));
            md.setValue(counts.get(i));
            list.add(md);
        }
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.addAll(list);
//        String jsonString = jsonArray.toJSONString();
//
//        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-748b758122044aaa9c8f14c5ff9a8a5e");
//        goEasy.publish("test_channel", jsonString);
          return list;
    }


}

package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("/queryall")

    public @ResponseBody
    Map<String, Object> queryAll(Integer page, Integer rows) throws Exception {
        Map<String, Object> map = bannerService.queryAll(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Map<String, Object> edit(Banner banner, String oper) {
        Map<String, Object> map = new HashMap<String, Object>();
        if ("add".equals(oper)) {
            bannerService.insert(banner);
            map.put("add", "添加成功");
            map.put("banner", banner);
            return map;
        }
        if ("del".equals(oper)) {
            String[] ids = banner.getId().split(",");
            bannerService.delete(Arrays.asList(ids));
            map.put("delete", "删除成功");
            return map;
        }
        if ("edit".equals(oper)) {
            bannerService.updateall(banner);
            map.put("edit", "修改成功");
            map.put("banner", banner);
            return map;
        }
        return null;

    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile imgPic, String bannerId, HttpSession session) throws Exception {
        //创建文件夹
        String originalFilename = imgPic.getOriginalFilename();
        if (!"".equals(originalFilename)) {
            String realPath = session.getServletContext().getRealPath("/img");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdir();
            }
            //创建新名字，添加时间戳避免覆盖
            String str = new Date().getTime() + "_" + originalFilename;
            bannerService.update(str, bannerId);
            try {
                imgPic.transferTo(new File(realPath, str));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}

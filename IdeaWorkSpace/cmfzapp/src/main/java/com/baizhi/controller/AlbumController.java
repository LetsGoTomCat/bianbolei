package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("/queryall")
    public @ResponseBody
    Map<String, Object> queryAll(Integer page, Integer rows) throws Exception {
        Map<String, Object> map = albumService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> insert(Album album, String oper) {

        Map<String, Object> map = new HashMap<String, Object>();

        if ("add".equals(oper)) {
            albumService.insert(album);
            map.put("album", album);
            map.put("add", "专辑添加成功");
        }
        if ("edit".equals(oper)) {
            albumService.updateAll(album);
            map.put("edit", "修改成功");
            map.put("album", album);
        }
        if ("del".equals(oper)) {
            String[] ids = album.getId().split(",");
            albumService.delete(Arrays.asList(ids));
            map.put("delete", "删除专辑成功");
        }

        return map;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String  upload(MultipartFile coverPic, String albumId, HttpSession session) throws Exception {
        //创建文件夹
        String originalFilename = coverPic.getOriginalFilename();
        if (!"".equals(originalFilename)) {
            String realPath = session.getServletContext().getRealPath("/img");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdir();
            }
            //创建新名字，添加时间戳避免覆盖
            String str = new Date().getTime() + "_" + originalFilename;
            System.out.println(str);
            albumService.updatePath(str, albumId);
            try {
                coverPic.transferTo(new File(realPath, str));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}

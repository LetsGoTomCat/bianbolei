package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/queryall")
    @ResponseBody
    public Map<String, Object> queryAll(String albumid, Integer page, Integer rows) throws Exception {
        System.out.println(rows);
        Map<String, Object> map = chapterService.queryAll(albumid, page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Map<String, Object> edit(String oper, Chapter chapter) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if ("add".equals(oper)) {
            chapterService.insert(chapter);
            map.put("chapter", chapter);
            map.put("add", "添加成功");
        }
        if ("edit".equals(oper)) {
            chapterService.updateTitle(chapter);
            map.put("chapter", chapter);
            map.put("edit", "修改成功");
        }
        if ("del".equals(oper)) {
            chapterService.delete(chapter);
            map.put("delete", "删除成功");
        }
        return map;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile audio, String chapterId, HttpSession session) throws Exception {
        chapterService.update(audio, session, chapterId);
        return null;
    }

    @RequestMapping("downLoadAudio")
    public void downLoadAudio(String audioName, HttpServletResponse response, HttpSession session) {
        chapterService.downLoadAudio(audioName, response, session);
    }

}

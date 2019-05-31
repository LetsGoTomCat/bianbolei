package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.VolatileImage;
import java.net.PortUnreachableException;
import java.util.Map;

public interface ChapterService {
    public Map<String, Object> queryAll(String albumid, Integer page, Integer rows);

    //添加
    public void insert(Chapter chapter);

    //添加图片
    public void update(MultipartFile audio, HttpSession session, String chapterId);

    public void updateTitle(Chapter chapter);

    public void delete(Chapter chapter);

    public void downLoadAudio(String audioName, HttpServletResponse response, HttpSession session);

}

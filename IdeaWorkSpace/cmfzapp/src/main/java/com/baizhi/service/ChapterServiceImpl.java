package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, Object> queryAll(String albumid, Integer page, Integer rows) {

        //准备返回客户端的数据
        Map<String, Object> maps = new HashMap<>();

        //当前页号
        maps.put("page", page);
        Integer totalCount = chapterDao.queryCount(albumid);
        System.out.println("***********************");
        System.out.println(totalCount);
        System.out.println("***********************");
        //总条数
        maps.put("records", totalCount);

        //总页数
        Integer pageCount = 0;
        //总页数
        if (totalCount % rows != 0) {
            pageCount = totalCount / rows + 1;
        } else {
            pageCount = totalCount / rows;
        }
        maps.put("total", pageCount);

        //当前数据内容
        List<Chapter> chapters = chapterDao.queryAll(albumid, page, rows);
        maps.put("rows", chapters);
        return maps;
    }

    @Override
    public void insert(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapterDao.insert(chapter);
    }

    @Override
    public void update(MultipartFile audio, HttpSession session, String chapterId) {
        //1.判断上传的文件夹是否存在
        String realPath = session.getServletContext().getRealPath("/upload/audio");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //2.获得文件真名
        String originalFilename = audio.getOriginalFilename();
        if (!"".equals(originalFilename)) {
            //3.为了防止同一个文件上传多次发生覆盖
            String name = new Date().getTime() + "_" + originalFilename;
            try {
                //完成上传
                audio.transferTo(new File(realPath, name));
                //获得时长
                //1.读取上传完成的音频
                AudioFile read = AudioFileIO.read(new File(realPath, name));
                //获得时长
                AudioHeader audioHeader = read.getAudioHeader();
                int trackLength = audioHeader.getTrackLength();
                String seconds = trackLength % 60 + "秒";
                String minute = trackLength / 60 + "分";
                String s = minute + seconds;
                //获得文件大小
                long l = audio.getSize();
                String size = l / 1024 / 1024 + "MB";

                Chapter chapter = new Chapter();
                chapter.setAudio(name);
                chapter.setSize(size);
                chapter.setDuration(s);
                chapter.setId(chapterId);
                chapterDao.updateByPrimaryKeySelective(chapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void updateTitle(Chapter chapter) {
        chapterDao.updateTitle(chapter);
    }

    @Override
    public void delete(Chapter chapter) {
        String[] ids = chapter.getId().split(",");
        for (String id : ids) {
            chapterDao.deleteByPrimaryKey(id);
        }

    }

    @Override
    public void downLoadAudio(String audioName, HttpServletResponse response, HttpSession session) {
//1.获得音频的目录
        String realPath = session.getServletContext().getRealPath("/upload/audio");
        //2.获取音频的文件
        File file = new File(realPath, audioName);
        String s = audioName.split("_")[1];

        try {

            //3.设置响应头(以附件的形式下载)
            String encode = URLEncoder.encode(s, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            FileUtils.copyFile(file, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


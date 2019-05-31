package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.zip.DataFormatException;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        //准备返回客户端的数据
        Map<String, Object> maps = new HashMap<>();

        //当前页号
        maps.put("page", page);

        Integer totalCount = albumDao.queryCount();
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
        List<Album> banners = albumDao.queryAll(page, rows);
        maps.put("rows", banners);

        return maps;
    }

    @Override
    public void insert(Album album) {
        String s = UUID.randomUUID().toString();
        album.setId(s);
        Date date = new Date();
        album.setCreateDate(date);

        albumDao.insert(album);

    }

    @Override
    public void updatePath(String path, String id) {
        albumDao.updatePath(path, id);

    }

    @Override
    public void updateAll(Album album) {
        albumDao.updateAll(album);

    }

    @Override
    public void delete(List ids) {
        albumDao.delete(ids);
    }
}

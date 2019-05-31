package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.PrivateKey;
import java.util.List;
import java.util.Map;

public interface AlbumService {
    public Map<String, Object> queryAll(Integer page, Integer rows);

    public void insert(Album album);

    public void updatePath(String path, String id);

    public void updateAll(Album album);

    public void delete(List ids);
}

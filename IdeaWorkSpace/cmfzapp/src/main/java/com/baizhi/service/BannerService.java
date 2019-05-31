package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    public Map<String, Object> queryAll(Integer page, Integer rows);

    public void insert(Banner banner);

    public void update(String path, String id);

    public void delete(List ids);

    public void updateall(Banner banner);
}

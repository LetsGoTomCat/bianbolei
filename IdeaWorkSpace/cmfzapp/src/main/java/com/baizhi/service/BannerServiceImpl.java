package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        //准备返回客户端的数据
        Map<String, Object> maps = new HashMap<>();

        //当前页号
        maps.put("page", page);
        Integer totalCount = bannerDao.queryCount();
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
        List<Banner> banners = bannerDao.querySome(page, rows);
        maps.put("rows", banners);
        return maps;
    }

    @Override
    public void insert(Banner banner) {
        String s = UUID.randomUUID().toString();
        banner.setId(s);
        Date date = new Date();
        banner.setCreateDate(date);

        bannerDao.insert(banner);

    }

    @Override
    public void update(String path, String id) {
        bannerDao.update(path, id);
    }

    @Override
    public void delete(List ids) {
        bannerDao.delete(ids);
    }

    @Override
    public void updateall(Banner banner) {
        bannerDao.updateall(banner);
    }

}

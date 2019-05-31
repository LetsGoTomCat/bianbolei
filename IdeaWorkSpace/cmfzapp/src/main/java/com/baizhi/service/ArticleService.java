package com.baizhi.service;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ArticleService {
    public Map<String, Object> queryAll(Integer page, Integer rows);

    public void inser(Article article);

    public void delete(String id);

    public void update(Article article);
}

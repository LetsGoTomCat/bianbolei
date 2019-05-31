package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BannerDao {
    int insert(Banner record);

    int insertSelective(Banner record);

    public List<Banner> queryAll();

    //查询条数
    public Integer queryCount();

    //展示某一页
    public List<Banner> querySome(@Param("page") Integer page, @Param("rows") Integer rows);

    //修改图片
    public void update(@Param("path") String path, @Param("id") String id);

    //删除
    public void delete(@Param("ids") List ids);

    //更改
    public void updateall(Banner banner);
}
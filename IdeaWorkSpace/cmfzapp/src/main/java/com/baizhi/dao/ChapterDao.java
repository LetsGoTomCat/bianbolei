package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ChapterDao {
    int deleteByPrimaryKey(String id);

    int insert(Chapter record);

    //添加
    int insertSelective(Chapter chapter);

    Chapter selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);

    public List<Chapter> queryAll(@Param("albumid") String albumid, @Param("page") Integer page, @Param("rows") Integer rows);

    public Integer queryCount(@Param("albumid") String albumid);

    public void updateTitle(Chapter chapter);

}
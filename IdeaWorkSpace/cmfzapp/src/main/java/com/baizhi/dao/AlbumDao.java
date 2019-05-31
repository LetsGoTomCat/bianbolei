package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AlbumDao {
    int deleteByPrimaryKey(String id);

    int insert(Album record);

    int insertSelective(Album album);

    Album selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Album record);

    int updateByPrimaryKey(Album record);

    public List<Album> queryAll(@Param("page") Integer page, @Param("rows") Integer rows);

    public Integer queryCount();

    public void updatePath(@Param("path") String path, @Param("id") String id);

    public void updateAll(Album album);

    //删除
    public void delete(@Param("ids") List ids);
}
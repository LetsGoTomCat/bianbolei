package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.DocFlavor;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article implements Serializable {
    private String id;
    //标题
    private String title;
    //作者
    private String author;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    //内容
    private String content;
    //上师id
    private String guruId;
    //状态
    private String status;

    private String path;

}
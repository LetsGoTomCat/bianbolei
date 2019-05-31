package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/article")
@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ResponseBody
    @RequestMapping("/queryall")
    Map<String, Object> queryAll(Integer page, Integer rows) throws Exception {
        Map<String, Object> map = articleService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String addKindeditor(Article article) throws Exception {
        articleService.inser(article);
        return "success";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteKindeditor(String id) throws Exception {
        articleService.delete(id);
        return "success";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateKindeditor(Article article) throws Exception {
        System.out.println(article);
        articleService.update(article);
        return "success";
    }
}

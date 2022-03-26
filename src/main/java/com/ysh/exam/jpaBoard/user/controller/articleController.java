package com.ysh.exam.jpaBoard.user.controller;

import com.ysh.exam.jpaBoard.user.dao.articleRepository;
import com.ysh.exam.jpaBoard.user.domain.article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/usr/article")
public class articleController {

    @Autowired
    private articleRepository articleRepository;

    @RequestMapping("/list")
    @ResponseBody
    public List<article> articles(){
        return articleRepository.findAll();
    }

}

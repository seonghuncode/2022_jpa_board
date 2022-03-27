package com.ysh.exam.jpaBoard.article.controller;

import com.ysh.exam.jpaBoard.article.dao.ArticleRepository;
import com.ysh.exam.jpaBoard.article.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usr/article")
public class articleController {

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("list")
    @ResponseBody
    public List<Article> showList() {
        return articleRepository.findAll();

    }

    @RequestMapping("detail")
    @ResponseBody
    // EX : http://localhost:8082/usr/article/detail?id=2 => id 매개변수에 2L 이 들어옵니다.
                            //id는 integer형태로 들어오는데  L운 자바 리터럴로 long으로 버꾸어 준다?
    public Article showDetail(long id){
        Optional<Article> article = articleRepository.findById(id);  
        //findbyid의 경우 optional로 받기 때문에  앞에를 optional로 받아 주어야 한다
        return article.get();
    }
    //jpa데이터 한개 -> 구글링 참고


    @RequestMapping("delete")
    @ResponseBody
    public void delete(Article article){
        articleRepository.delete(article);
    }


}
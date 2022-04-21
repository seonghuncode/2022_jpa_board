package com.ysh.exam.jpaBoard.article.dao;

import com.ysh.exam.jpaBoard.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);



}                                         //->필수 변경 시킬 수 없다


                
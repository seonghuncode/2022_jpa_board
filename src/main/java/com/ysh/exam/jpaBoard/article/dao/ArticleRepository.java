package com.ysh.exam.jpaBoard.article.dao;

import com.ysh.exam.jpaBoard.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}                                         //->필수 변경 시킬 수 없다
                
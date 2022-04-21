package com.ysh.exam.jpaBoard.article.dao;


import com.ysh.exam.jpaBoard.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);

    Optional<Article> findByTitle(String keyword);


    boolean existsByTitle(String keyword);
}                                         //->필수 변경 시킬 수 없다


                
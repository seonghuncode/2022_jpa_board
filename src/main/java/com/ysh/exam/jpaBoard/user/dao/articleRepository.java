package com.ysh.exam.jpaBoard.user.dao;

import com.ysh.exam.jpaBoard.user.domain.article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface articleRepository extends JpaRepository<article, Long> {
}

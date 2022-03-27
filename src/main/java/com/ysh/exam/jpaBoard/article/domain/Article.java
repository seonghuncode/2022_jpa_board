package com.ysh.exam.jpaBoard.article.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //필수 mysql에 artiicle table과 똑같이 해야 한다

    private long id;
    private LocalDateTime reg_date ;
    private LocalDateTime update_date;
    private String title;
    private String body;
    private long user_id;

}

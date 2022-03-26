package com.ysh.exam.jpaBoard.user.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private LocalDateTime reg_date ;
    private LocalDateTime update_date;
    private String title;
    private String body;
    private long user_id;

}

package com.ysh.exam.jpaBoard.article.domain;

import com.ysh.exam.jpaBoard.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //필수 mysql에 artiicle table과 똑같이 해야 한다
    private long id;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private String title;
    private String body;

    //유저의 자세한 정보를 추가 하기 위한 방법
    @ManyToOne  //게시물은 많은데 유저는 한명인 관계
    private User user;

}

package com.ysh.exam.jpaBoard.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString //웹브라우저에 아래의 정보들이 사용자 편의성 있게 보여지는 역할(있고 없고 비교 하기)
    //영상에 수동으로 만드는 방법 숙지 하고 수작업이 아닌 어노테이션을 사용할 경우 이해
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private String name;
    private String email;

    @JsonIgnore
    private String password;


}

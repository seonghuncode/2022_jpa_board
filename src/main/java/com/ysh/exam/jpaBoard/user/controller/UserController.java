package com.ysh.exam.jpaBoard.user.controller;

import com.ysh.exam.jpaBoard.user.dao.UserRepository;
import com.ysh.exam.jpaBoard.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/usr/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("dojoin")
    @ResponseBody
    public String showJoin(String name, String email, String password){

        if(name == null  || name.trim().length() == 0){
            return "name을 입력해주세요";
        }

        name = name.trim();

        if(email == null || email.trim().length() == 0){
            return "email을 입력 해주세요";
        }

        email = email.trim();

        if(password == null || password.trim().length() == 0){
            return "password를 입력해 주세요";
        }

        password = password.trim();

        User user = new User();

        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);

        return "%d번 회원이 생성 되었습니다.".formatted(user.getId());



    }


}

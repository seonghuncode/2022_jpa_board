package com.ysh.exam.jpaBoard.user.controller;

import com.ysh.exam.jpaBoard.user.dao.UserRepository;
import com.ysh.exam.jpaBoard.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

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

        boolean existsByEmail = userRepository.existsByEmail(email);

        if(existsByEmail){
            return "이미 사영중인 email이라 %s는 사용 불가능 합니다.".formatted(email);
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


        @RequestMapping("doLogin")
        @ResponseBody
        public String showLogin(String email, String password, HttpServletRequest req, HttpServletResponse resp){

            if(email == null || email.trim().length() == 0){
                return "email을 입력해 주세요";
            }

            email = email.trim();

            if(password == null || password.trim().length() ==0){
                return "password가 입력되지 않았습니다. 입력해 주세요";
            }

            password = password.trim();

            // User user =  userRepository.findByEmail(email).orElse(null); //방법1 -> optuonal이 null이면 user에 null을 넣어라
            //요즘 추천하는 방법
            Optional<User> user = userRepository.findByEmail(email); // 방법2
            //optioal사용시 .get()을 무조건 사용해주어야 한다.

            if(user.isEmpty()){
                return "일치하는 회원이 없습니다";
            }

//            System.out.println("user.getPassword() : " + user.get().getPassword());
//            System.out.println("password : " + password);
            //위의 두줄은 디버깅 코드로 개발자가 확인을 위해 사용하고 확인이 끝나면 지워준다.

            if(user.get().getPassword().equals(password) == false){
                return "비밀번호가 일치 하지 않습니다.";
            }
            
            //세션을 사용
            HttpSession session = req.getSession();
            session.setAttribute("loginedUserId", user.get().getId());

            return "%s님 횐영합니다".formatted(user.get().getName());

        }

    //오류시 콘솔에서 어떤 커리가 실행되었는지 확인하고 오류를 찾는다.



    @RequestMapping("me")
    @ResponseBody
    public User showMe(HttpSession session){

        boolean isLogined = false;
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null) {
            isLogined = true;
            loginedUserId = (long) session.getAttribute("loginedUserId");
        }

        if ( isLogined == false ) {
            return null;
        }

        Optional<User> user = userRepository.findById(loginedUserId);


        if(user.isEmpty()){
            return null;
        }
        return user.get();
    }



}

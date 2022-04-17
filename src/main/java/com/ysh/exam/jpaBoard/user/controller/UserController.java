package com.ysh.exam.jpaBoard.user.controller;

import com.ysh.exam.jpaBoard.user.dao.UserRepository;
import com.ysh.exam.jpaBoard.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


    @RequestMapping("join")
    public String showJoin(HttpSession session, Model model){

        boolean isLogined = false;
        long loginedUserId = 0;

        if(session.getAttribute("loginedUserId") != null){
            isLogined = true;
            loginedUserId = (long) session.getAttribute("loginedUserId");
        }

        if(isLogined){
            model.addAttribute("msg", "이미 로그인이 되어 있습니다. 로그아웃후 이용해 주세요");
            model.addAttribute("historyBack", true);
            return "common/js";
        }


        return "usr/user/join";
    }


    @RequestMapping("dojoin")
    @ResponseBody
    public String showJoin(String name, String email, String password){

        if(name == null  || name.trim().length() == 0){
            return """
                    <script>
                    alert('이름을 입력해 주세요!!');
                    history.back();
                    </script>
                    """;
        }

        name = name.trim();

        if(email == null || email.trim().length() == 0){
            return """
                    <script>
                    alert('이메일을 입력해 주세요!!');
                    history.back();
                    </script>
                    """;
        }

        email = email.trim();

        if(password == null || password.trim().length() == 0){
            return """
                    <script>
                    alert('비밀번호를 입력해 주세요!!');
                    history.back();
                    </script>
                    """;
        }

        boolean existsByEmail = userRepository.existsByEmail(email);

        if(existsByEmail){
            return """
                    <script>
                    alert('%s는 이미 사용중인 이메일 이므로 사용 불가능 합니다.');
                    history.back();
                    </script>
                    """.formatted(email);
        }

            password = password.trim();

            User user = new User();

            user.setRegDate(LocalDateTime.now());
            user.setUpdateDate(LocalDateTime.now());
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            userRepository.save(user);

            return """
                    <script>
                    alert('%s 회원이 생성되었습니다. 축하합니다!!');
                    history.back();
                    </script>
                    """.formatted(user.getName());
        }



    @RequestMapping("login")
    public String showLogin(HttpSession session, Model model) {
        boolean isLogined = false;
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null) {
            isLogined = true;
            loginedUserId = (long) session.getAttribute("loginedUserId");
        }

        if (isLogined) {
            model.addAttribute("msg", "이미 로그인 되었습니다.");
            model.addAttribute("historyBack", true);
            return "common/js";
        }

        return "usr/user/login";
    }



        @RequestMapping("doLogin")
        @ResponseBody
        public String showLogin(String email, String password, HttpServletRequest req, HttpServletResponse resp){

            if(email == null || email.trim().length() == 0){
                return """
                        <script>
                        alert('이메일을 입력해 주세요!!')
                        history.back();
                        </script>
                        """;
            }

            email = email.trim();

            if(password == null || password.trim().length() ==0){
                return """
                        <script>
                        alert('비밀번호를 입력해 주세요.');
                        history.back();
                        </script>
                        """;
            }

            password = password.trim();

            // User user =  userRepository.findByEmail(email).orElse(null); //방법1 -> optuonal이 null이면 user에 null을 넣어라
            //요즘 추천하는 방법
            Optional<User> user = userRepository.findByEmail(email); // 방법2
            //optioal사용시 .get()을 무조건 사용해주어야 한다.

            if(user.isEmpty()){
                return """
                        <script>
                        alert('일치 하는 회원이 없습니다. 다시 확인해 주세요.');
                        history.back();
                        </script>
                        """;
            }

//            System.out.println("user.getPassword() : " + user.get().getPassword());
//            System.out.println("password : " + password);
            //위의 두줄은 디버깅 코드로 개발자가 확인을 위해 사용하고 확인이 끝나면 지워준다.

            if(user.get().getPassword().equals(password) == false){
                return """
                        <script>
                        alert('비밀번호가 일치 하지 않습니다.');
                        history.back();
                        </script>                      
                        """;
            }
            
            //세션을 사용
            HttpSession session = req.getSession();
            session.setAttribute("loginedUserId", user.get().getId());

            return """
                    <script>
                    alert('%s님 환영 합니다.');
                    history.back();
                    </script>
                    """.formatted(user.get().getName());

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


    @RequestMapping("doLogout")
    @ResponseBody
    public String showLogOut(HttpSession session){

        boolean isLogined = false;

        if (session.getAttribute("loginedUserId") != null) {
            isLogined = true;
        }

        if (isLogined == false) {
            return "이미 로그아웃 되었습니다.";
        }

        session.removeAttribute("loginedUserId");

        return """
                <script>
                alert('로그아웃 되었습니다.');
                history.back();
                </script>
                """;
    }

    @RequestMapping("modify")
    public String showModify(HttpSession session, Model model){

        boolean isLogined = false;
        long loginedUserId = 0;

        if(session.getAttribute("loginedUserId") != null){
            isLogined = true;
            loginedUserId = (long) session.getAttribute("loginedUserId");
        }
        if(isLogined == false){
            model.addAttribute("msg", "로그인 후 사용 가능 합니다. 로그인을 해주세요!!");
            model.addAttribute("historyBack", true);
            return "common/js";
        }

        return "usr/user/modify";
    }




}

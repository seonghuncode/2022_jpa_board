package com.ysh.exam.jpaBoard.article.controller;

import com.ysh.exam.jpaBoard.article.dao.ArticleRepository;
import com.ysh.exam.jpaBoard.article.domain.Article;
import com.ysh.exam.jpaBoard.user.dao.UserRepository;
import com.ysh.exam.jpaBoard.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usr/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("list")

    public String showList(Model model) {   //ui사용

        List<Article> articles = articleRepository.findAll();

        model.addAttribute("articles", articles);

        //@ResponseBody를 지우고 아래의 경로로 return을 시켜주게 되면 아래 경로의 html파일이 웹페이지에 보여진다.
        return "usr/article/list"; //여기있는 템플릿을 사용해서 만들겠다
    }

    @RequestMapping("detail")
    // EX : http://localhost:8082/usr/article/detail?id=2 => id 매개변수에 2L 이 들어옵니다.
                            //id는 integer형태로 들어오는데  L운 자바 리터럴로 long으로 버꾸어 준다?
    public String showDetail(long id, Model model){
        Optional<Article> article = articleRepository.findById(id);  
        //findbyid의 경우 optional로 받기 때문에  앞에를 optional로 받아 주어야 한다

        Article article2 = article.get();
        model.addAttribute("article", article2);

        return "usr/article/detail";
    }
    //jpa데이터 한개 -> 구글링 참고


    @RequestMapping("doDelete")
    @ResponseBody
    public String showdoDelete(long id) {

        if(articleRepository.existsById(id) == false){
            return """
                    <script>
                    alert('%d번 게시물은 이미 삭제되었거나 존재하지 않습니다.');
                    history.back();
                    </script>
                    """.formatted(id);
        }
        articleRepository.deleteById(id);
        return """
                <script>
                alert('%d번 게시물이 삭제되었습니다.');
                location.replace('list');
                </script>
                """
                .formatted(id);


    }



    @RequestMapping("doModify")
    @ResponseBody
    public String showModify(long id, String title, String body) {
        Article article = articleRepository.findById(id).get();

        if (title != null) {
            article.setTitle(title);
        }
        if(body != null){
            article.setBody(body);
        }

        article.setUpdateDate(LocalDateTime.now());


        articleRepository.save(article);
        return """
                <script>
                alert('%d번 게시물이 수정되었습니다.');
                location.replace('detail?id=%d');
                </script>
                """.formatted(article.getId(), article.getId());
    }

    @RequestMapping("modify")
    public String showModify(long id, Model model){

        Optional<Article> opArticle = articleRepository.findById(id);
        Article article = opArticle.get();

        model.addAttribute("article", article);

        return "usr/article/modify";

    }


    @RequestMapping("write")
    public String showWrite(HttpSession session, Model model){

        boolean isLogined = false;
        long loginedUserId = 0;

        if(session.getAttribute("loginedUserId") != null){
            isLogined = true;
            loginedUserId = (long)session.getAttribute("loginedUserId");
        }

        System.out.println("isLogined : " + isLogined);

        if(isLogined == false){
            model.addAttribute("msg", "로그인 후 사용해 주새요.");
            model.addAttribute("historyBack", true);
            return "common/js";
        }

        return "usr/article/write";
    }



    @RequestMapping("dowrite")
    @ResponseBody
    public String showWrite(String title, String body, HttpSession session){

        boolean isLogined = false;
        long loginedUserId = 0;

        //웹브라우저 에서 로그인 할때 세션을 주었기 때문에 그 로그인된 세션 값을 가지고 와서 loginedUserId여기에 저장해 준다
        if(session.getAttribute("loginedUserId") != null){
            isLogined = true;
            loginedUserId = (long)session.getAttribute("loginedUserId");
        }

        if(isLogined == false){

            return """
                <script>
                alert('로그인이 필요한 기능 입니다. 로그인 후 사용해 주세요!!')
                history.back();
                </script> 
               """;
        }

        if(title == null || title.trim().length() == 0){
            return "제목을 입력해 주세요";
        }

        title = title.trim();

        if(body == null || body.trim().length() == 0){
            return "내용을 입력해 주세요";
        }

        body = body.trim();

        Article article = new Article();

        article.setTitle(title);
        article.setBody(body);
        article.setUpdateDate(LocalDateTime.now());
        article.setRegDate(LocalDateTime.now());

        //DB에서 user를 가지고 오는 방법
        User user = userRepository.findById(loginedUserId).get();  //세션에서 받은 값을 넣어 user에 저장 해준다
        article.setUser(user);

        articleRepository.save(article);

        return """
                <script>
                alert('%d번 게시물이 작성 되었습니다.')
                location.replace('list');
                </script> 
               """.formatted(article.getId());
        //게시글 write에서 작성후 작성 완성 메세지 출력후 다시 write로 돌아간다. replace사용 이유는 처리 과정에 대한 기억을 없애 뒤로가기시 처리과정이 아닌 그 전으로 갈 수 있다.

    }





}

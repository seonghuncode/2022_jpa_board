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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usr/article")
public class articleController {

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


    @RequestMapping("delete")
    @ResponseBody
    public String showDelete(long id) {

        if(articleRepository.existsById(id) == false){
            return "%d번 게시물은 이미 삭제 되었거나 없는 게시물 입니다.".formatted(id);
        }
        articleRepository.deleteById(id);
        return "%d번 게시물이 삭제 되었습니다".formatted(id);


    }


    @RequestMapping("doModify")
    @ResponseBody
    public Article showModify(long id, String title, String body) {
        Article article = articleRepository.findById(id).get();

        if (title != null) {
            article.setTitle(title);
        }
        if(body != null){
            article.setBody(body);
        }

        article.setUpdateDate(LocalDateTime.now());


        articleRepository.save(article);
        return article;
    }


    @RequestMapping("write")
    public String showWrite(){
        return "usr/article/write";
    }



    @RequestMapping("dowrite")
    @ResponseBody
    public String showWrite(String title, String body){

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
        User user = userRepository.findById(1L).get();  //--> user_id는 1이 된다
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

package com.ysh.exam.jpaBoard.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/")
    public String showMain(){
        return "redirect:/usr/article/list";
        // "/"로 들어오면 -> usr/article/list로 자동 이동
    }
}

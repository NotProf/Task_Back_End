package com.test.taskback.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(){
        return "forward:/index.html";
    }
    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
    @RequestMapping(value = "/{[path:[^\\.]*}/{[id:[^\\.]*}")
    public String redirectWithParams(HttpServletRequest request) {
        return "forward:/";
    }
}

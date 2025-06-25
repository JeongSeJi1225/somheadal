package com.somhaedal.somhaedal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SomhaedalController {

    @GetMapping("/test")
    public String hello(Model model) {
        model.addAttribute("name", "나다니엘");
        return "test";
       
    }

    @GetMapping("/main")
    public String main(Model model) {
   
        return "main";
       
    }
}

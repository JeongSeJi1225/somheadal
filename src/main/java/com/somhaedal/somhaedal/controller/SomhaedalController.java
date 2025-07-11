package com.somhaedal.somhaedal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.somhaedal.somhaedal.dto.AdminDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
import com.somhaedal.somhaedal.service.SomheadalImpl;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class SomhaedalController {


    @Autowired
    private SomheadalImpl somheadalService;

    @GetMapping("/test")
    public String hello(Model model) {
        model.addAttribute("name", "나다니엘");
        return "test";
       
    }

    @GetMapping("/main")
    public String main(Model model) {
   
        return "main";
       
    }

    @GetMapping("/fnq")
    public String fnq(Model model) {
   
        return "fnq";
       
    }
    @GetMapping("/howwork")
    public String howork(Model model) {
   
        return "howwork";
       
    }
    @GetMapping("/product")
    public String product(Model model) {
   
        return "product";
       
    }

    @GetMapping("/workerLogin")
    public String workerLogin(Model model) {
        return "workerLogin";
    }

    @PostMapping("/adminLoginProcess")
    public String postMethodName(HttpSession session, AdminDto params) {
        AdminDto sessionAdmin = somheadalService.getAdminLoginInfo(params);
        session.setAttribute("sessionAdminInfo", sessionAdmin);
        if (sessionAdmin != null) {
            return "redirect:./workerMainPage";
        }else{
            return "redirect:./workLoginFail";
        }

        
    }

    @GetMapping("/workLoginSuccess")
    public String workLoginSuccess(Model model) {
        return "workLoginSuccess";
    }

    @GetMapping("/workerMainPage")
    public String workerMainPage(HttpSession session, Model model) {
    AdminDto sessionAdmin = (AdminDto) session.getAttribute("sessionAdminInfo"); //관리자 정보

    if (sessionAdmin == null) {
        return "redirect:./workerLogin";
    }
    //System.out.println("작업자 관련 정보 확인 ㄱㄱ"+model);
    model.addAttribute("adminName", sessionAdmin.getAdmin_name());  

    CustomerInfoDto customerInfoDto = new CustomerInfoDto();
    int todayApplyCount = somheadalService.getTodayApplyCount(customerInfoDto);
    model.addAttribute("TodayApplyCount", todayApplyCount);


    return "workerMainPage";
    }
 

    @GetMapping("/fabricManagerPage")
    public String fabricManagerPage(Model model) {
        return "fabricManagerPage";
    }

    
    
    



    
    
}

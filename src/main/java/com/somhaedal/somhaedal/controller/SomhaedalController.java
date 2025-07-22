package com.somhaedal.somhaedal.controller;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.somhaedal.somhaedal.dto.AdminDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
import com.somhaedal.somhaedal.dto.FabricCategoryDto;
import com.somhaedal.somhaedal.dto.FabricManagementDto;
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
    public String fabricManagerPage(Model model, HttpSession session) {
        AdminDto sessionAdmin = (AdminDto) session.getAttribute("sessionAdminInfo");
        model.addAttribute("adminName", sessionAdmin.getAdmin_name());
        model.addAttribute("readFabricAdds", somheadalService.getFabricAdds());
        return "fabricManagerPage";
    }

    @GetMapping("fabricAddPage")
    public String fabricAddPage(Model model, HttpSession session) {
        AdminDto sessionAdmin = (AdminDto) session.getAttribute("sessionAdminInfo"); //관리자 정보
        model.addAttribute("sessionAdminPk", sessionAdmin.getSeq_num());
        model.addAttribute("sessionAdmin", sessionAdmin.getAdmin_name());
        model.addAttribute("fabricCategoryName", somheadalService.getFabricCategoryInfo());

        // data checking. success
        //System.out.println("Fabric Category info's fuck..." + somheadalService.getFabricCategoryInfo());

        return "fabricAddPage";
    }

    @PostMapping("fabricAddProcess")
    public String getMethodName(Model model, HttpSession session, AdminDto adminDto,
                                FabricCategoryDto fabricCategoryDto,
                                FabricManagementDto fabricManagementDto,
                                MultipartFile imageFiles) {

        AdminDto sessionAdmin = (AdminDto) session.getAttribute("sessionAdminInfo");
        model.addAttribute("sessionAdminPk", sessionAdmin.getSeq_num());
        model.addAttribute("sessionAdmin", sessionAdmin.getAdmin_name());
        model.addAttribute("fabricCategoryName", somheadalService.getFabricCategoryInfo());

        // 대표 이미지 업로드
        if (imageFiles != null && !imageFiles.isEmpty()) {
            String rootPath = "C:/somUploadFiles/";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String todayPath = sdf.format(new Date());
            File folder = new File(rootPath + todayPath);
            if (!folder.exists()) folder.mkdirs();

            String originalFileName = imageFiles.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_" + currentTime + originalFileName.substring(originalFileName.lastIndexOf("."));
            String fullPath = rootPath + todayPath + fileName;

            try {
                imageFiles.transferTo(new File(fullPath));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 파일 경로를 DTO에 저장
            fabricManagementDto.setFb_swatch_img_path(todayPath + fileName);
        }

        // DTO에 이미지 경로가 세팅된 상태로 insert
        somheadalService.fabriAddInsert(fabricManagementDto);
        System.out.println("받은 컬러값: " + fabricManagementDto.getFb_color());

        return "redirect:./fabricManagerPage";
    }

    
    
    
    



    
    
}

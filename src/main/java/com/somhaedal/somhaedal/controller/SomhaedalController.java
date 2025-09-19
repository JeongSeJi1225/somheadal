package com.somhaedal.somhaedal.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.somhaedal.somhaedal.dto.CustomerImgDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
import com.somhaedal.somhaedal.dto.CustomerOption;
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
    public String fabricManagerPage(Model model, HttpSession session, @RequestParam(required = false) Integer type,
                                        FabricManagementDto fabricManagementDto) {
        AdminDto sessionAdmin = (AdminDto) session.getAttribute("sessionAdminInfo"); //관리자 정보
        model.addAttribute("adminName", sessionAdmin.getAdmin_name());  
        model.addAttribute("readFabricAdds", somheadalService.getFabricAdds());
        model.addAttribute("fabricCategoryName", somheadalService.getFabricCategoryInfo());
        model.addAttribute("selectedType", type);
        model.addAttribute("countFabrics", somheadalService.countFabrics(fabricManagementDto));
        model.addAttribute("countStocks", somheadalService.countStocks(fabricManagementDto));
        model.addAttribute("countStocksWait", somheadalService.countStocksWait(fabricManagementDto));
        model.addAttribute("sumFabrics", somheadalService.sumFabrics(fabricManagementDto));


            //data checking img path
       // System.out.println("원단정보확인 : " + somheadalService.getFabricAdds());


        return "fabricManagerPage";

        
    }

    @GetMapping("fabricAddPage")
    public String fabricAddPage(Model model, HttpSession session) {
        AdminDto sessionAdmin = (AdminDto) session.getAttribute("sessionAdminInfo"); //관리자 정보
        model.addAttribute("sessionAdminPk", sessionAdmin.getSeq_num());
        model.addAttribute("sessionAdmin", sessionAdmin.getAdmin_name());
        model.addAttribute("fabricCategoryName", somheadalService.getFabricCategoryInfo());

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
        //System.out.println("받은 컬러값: " + fabricManagementDto.getFb_color());

        return "redirect:./fabricManagerPage";
    }

@PostMapping("editFabricProcess")
public String editFabricProcess(
        FabricManagementDto fabricManagementDto,
        @RequestParam("fb_id_pk") int fbIdPk,
        @RequestParam(value = "imageFiles", required = false) MultipartFile imageFiles) throws IOException {

    // 1. 기존 원단 정보 불러오기
    Map<String, Object> existingFabric = somheadalService.readFabricOnlyOne(fbIdPk);
    String existingImagePath = (String) existingFabric.get("fb_swatch_img_path");

    fabricManagementDto.setFb_id_pk(fbIdPk); // 기본 PK 세팅

    // 2. 새 이미지가 업로드 되었는지 확인
    if (imageFiles != null && !imageFiles.isEmpty()) {
        // 새 이미지 업로드 및 저장 경로 생성
        String rootPath = "C:/somUploadFiles/";
        String todayPath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
        File folder = new File(rootPath + todayPath);
        if (!folder.exists()) folder.mkdirs();

        String originalFileName = imageFiles.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        long currentTime = System.currentTimeMillis();
        String fileName = uuid + "_" + currentTime + originalFileName.substring(originalFileName.lastIndexOf("."));
        String fullPath = rootPath + todayPath + fileName;

        imageFiles.transferTo(new File(fullPath));

        // 새 이미지 경로 저장
        fabricManagementDto.setFb_swatch_img_path(todayPath + fileName);
    } else {
        // 이미지가 없다면 기존 이미지 경로 유지
        fabricManagementDto.setFb_swatch_img_path(existingImagePath);
    }

    // 3. DB 업데이트
    somheadalService.updateFabrics(fabricManagementDto);

    return "redirect:./detailFabricPage?fb_id_pk=" + fbIdPk;
}


@GetMapping("detailFabricPage")
public String detailFabricPage(HttpSession session, Model model, @RequestParam("fb_id_pk") int fb_id_pk) {
    AdminDto sessionAdmin = (AdminDto) session.getAttribute("sessionAdminInfo");
    model.addAttribute("adminName", sessionAdmin.getAdmin_name());
    model.addAttribute("adminPk", sessionAdmin.getSeq_num());
    Map<String, Object> fabric = somheadalService.readFabricOnlyOne(fb_id_pk);
    model.addAttribute("fabricCategoryName", somheadalService.getFabricCategoryInfo());
    model.addAttribute("fabric", fabric);
    return "detailFabricPage";
}


    @GetMapping("updateStock")
    public String updateStock(HttpSession session, FabricManagementDto fabricManagementDto) {
        somheadalService.updateStock(fabricManagementDto);
        return "redirect:./detailFabricPage?fb_id_pk="+fabricManagementDto.getFb_id_pk();
    }

    @GetMapping("deleteFabricsProcess")
    public String deleteFabricsProcess(int fb_id_pk) {
        somheadalService.deleteFabrics(fb_id_pk);
        return "redirect:./fabricManagerPage";
    }
    
    @GetMapping("customerManagerPage")
    public String customerManagerPage(HttpSession session, Model model) {
        AdminDto sessionAdmin = (AdminDto) session.getAttribute("sessionAdminInfo");
        model.addAttribute("adminName", sessionAdmin.getAdmin_name());
        model.addAttribute("adminPk", sessionAdmin.getSeq_num());
        model.addAttribute("customerSamllList", somheadalService.readCustomerList());
        return "customerManagerPage";
    }
    
    @GetMapping("customerAddPage")
    public String customerAddPage(HttpSession session, Model model) {
        model.addAttribute("readProducts", somheadalService.readMainProduct());
        model.addAttribute("readOptions", somheadalService.readOptions());
        return "customerAddPage";
    }
    
    @PostMapping("customerAddProcess")
    public String postMethodName(MultipartFile imageFiles,
                             CustomerImgDto customerImgDto,
                             CustomerInfoDto customerInfoDto,
                             @RequestParam(value = "ct_option", required = false) List<Integer> ctOptions) {

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

        // 먼저 DTO에 세팅
        customerImgDto.setCi_img_url(todayPath + fileName);
    }

    // 이제 insert 호출
    somheadalService.insertCustomerAndOptions(customerInfoDto, ctOptions, customerImgDto);

    return "redirect:./customerManagerPage";
    }

    @GetMapping("customerDetailPage")
    public String customerDetailPage(HttpSession session, Model model, @RequestParam("ct_id_pk") int ct_id_pk,
                                        CustomerImgDto customerImgDto, CustomerInfoDto customerInfoDto,
                                        CustomerOption customerOption) {
        model.addAttribute("customerReadOptions",somheadalService.readCustomerOptions(ct_id_pk));
        model.addAttribute("customerReadApply", somheadalService.readCustomerAdds(ct_id_pk));

        System.out.println("ct _ id _ pk =" + somheadalService.readCustomerAdds(ct_id_pk));

        return "customerDetailPage";
    }
    
    
        @GetMapping("another")
    public String another(HttpSession session, Model model, @RequestParam("ct_id_pk") int ct_id_pk,
                                        CustomerImgDto customerImgDto, CustomerInfoDto customerInfoDto,
                                        CustomerOption customerOption) {
        model.addAttribute("customerReadOptions",somheadalService.readCustomerOptions(ct_id_pk));
        model.addAttribute("customerReadApply", somheadalService.readCustomerAdds(ct_id_pk));

        System.out.println("ct _ id _ pk =" + somheadalService.readCustomerAdds(ct_id_pk));


        return "another";
    }
    



    
    
}

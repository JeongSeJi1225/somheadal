package com.somhaedal.somhaedal.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
import com.somhaedal.somhaedal.dto.DeliveryInfo;
import com.somhaedal.somhaedal.dto.DeliveryOptionDto;
import com.somhaedal.somhaedal.dto.FabricCategoryDto;
import com.somhaedal.somhaedal.dto.FabricManagementDto;
import com.somhaedal.somhaedal.dto.ProductOptiontDto;
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
        model.addAttribute("deliverys", somheadalService.readDeliveryOptions());
        return "customerAddPage";
    }
    
//     @PostMapping("customerAddProcess")
//     public String postMethodName(@RequestParam("imageFiles") List<MultipartFile> imageFiles,
//                              CustomerImgDto customerImgDto,
//                              CustomerInfoDto customerInfoDto,
//                              @RequestParam(value = "ct_option", required = false) List<Integer> ctOptions) {

//     List<CustomerImgDto> imgList = new ArrayList<>();

//     if (imageFiles != null && !imageFiles.isEmpty()) {
//         String rootPath = "C:/somUploadFiles/";
//         SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
//         String todayPath = sdf.format(new Date());
//         File folder = new File(rootPath + todayPath);
//         if (!folder.exists()) folder.mkdirs();

//         for (MultipartFile file : imageFiles) {
//             if (file.isEmpty()) continue;

//             String originalFileName = file.getOriginalFilename();
//             String uuid = UUID.randomUUID().toString();
//             long currentTime = System.currentTimeMillis();
//             String fileName = uuid + "_" + currentTime 
//                                 + originalFileName.substring(originalFileName.lastIndexOf("."));
//             String fullPath = rootPath + todayPath + fileName;

//             try {
//                 file.transferTo(new File(fullPath));
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }

//             CustomerImgDto imgDto = new CustomerImgDto();
//             imgDto.setCi_img_url(todayPath + fileName);
//             imgList.add(imgDto);
//         }
//     }

//     // 여러 장 이미지도 서비스로 넘김
//     somheadalService.insertCustomerAndOptions(customerInfoDto, ctOptions, imgList);

//     return "redirect:./customerManagerPage";
// }


@PostMapping("customerAddProcess")
public String customerAddProcess(
        @RequestParam("imageFiles") List<MultipartFile> imageFiles,
        CustomerInfoDto customerInfoDto,
        @RequestParam(value = "ct_option", required = false) List<Integer> ctOptions
) throws IOException {

    // 이미지 업로드 처리
    List<CustomerImgDto> imgList = new ArrayList<>();
    if (imageFiles != null && !imageFiles.isEmpty()) {
        String rootPath = "C:/somUploadFiles/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String todayPath = sdf.format(new Date());
        File folder = new File(rootPath + todayPath);
        if (!folder.exists()) folder.mkdirs();

        for (MultipartFile file : imageFiles) {
            if (file.isEmpty()) continue;

            String originalFileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            String fileName = uuid + "_" + currentTime 
                    + originalFileName.substring(originalFileName.lastIndexOf("."));
            String fullPath = rootPath + todayPath + fileName;

            file.transferTo(new File(fullPath));

            CustomerImgDto imgDto = new CustomerImgDto();
            imgDto.setCi_img_url(todayPath + fileName);
            imgList.add(imgDto);
        }
    }

    // 서비스 호출 – 고객 정보 + 옵션 + 이미지 insert
    somheadalService.insertCustomerAndOptions(customerInfoDto, ctOptions, imgList);

    return "redirect:./customerManagerPage";
}


    // @PostMapping("customerEditProcess")
    // public String customerEditProcess(
    //         @RequestParam("ct_id_pk") int ctIdPk,
    //         CustomerInfoDto customerInfoDto,
    //         @RequestParam(value = "ct_option", required = false) List<Integer> ctOptions,
    //         @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles
    // ) throws IOException {

    //     // 1. 기존 고객 이미지 목록 불러오기
    //     List<CustomerImgDto> existingImgs = somheadalService.readCustomerImgData(ctIdPk);
    //     List<CustomerImgDto> imgList = new ArrayList<>();

    //     // 2. 새 이미지 업로드가 있는 경우
    //     if (imageFiles != null && !imageFiles.isEmpty() && imageFiles.stream().anyMatch(f -> !f.isEmpty())) {
    //         String rootPath = "C:/somUploadFiles/";
    //         String todayPath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
    //         File folder = new File(rootPath + todayPath);
    //         if (!folder.exists()) folder.mkdirs();

    //         for (MultipartFile file : imageFiles) {
    //             if (file.isEmpty()) continue;

    //             String originalFileName = file.getOriginalFilename();
    //             String uuid = UUID.randomUUID().toString();
    //             long currentTime = System.currentTimeMillis();
    //             String fileName = uuid + "_" + currentTime +
    //                     originalFileName.substring(originalFileName.lastIndexOf("."));
    //             String fullPath = rootPath + todayPath + fileName;

    //             file.transferTo(new File(fullPath));

    //             CustomerImgDto imgDto = new CustomerImgDto();
    //             imgDto.setCi_img_url(todayPath + fileName);
    //             imgList.add(imgDto);
    //         }

    //         // 새 이미지 업로드된 경우 → 기존 이미지 삭제 후 새로 insert
    //         somheadalService.deleteCustomerImgs(ctIdPk);

    //     } else {
    //         // 새 이미지 없으면 기존 이미지 유지
    //         imgList = existingImgs;
    //     }

    //     // 3. 고객 정보 업데이트 준비
    //     customerInfoDto.setCt_id_pk(ctIdPk);

    //     // 4. 서비스 호출 (고객 정보 + 옵션 + 이미지 일괄 처리)
    //     somheadalService.updateCustomerAndOptions(customerInfoDto, ctOptions, imgList);

    //     // 5. 수정 완료 후 리다이렉트
    //     return "redirect:./customerEditPage?ct_id_pk=" + ctIdPk;
    // }


    // @PostMapping("customerEditProcess")
    // public String customerEditProcess(
    //         @RequestParam("ct_id_pk") int ctIdPk,
    //         @RequestParam(value = "mainProduct", required = false) Integer mainProductId, // ✅ 추가
    //         CustomerInfoDto customerInfoDto,
    //         @RequestParam(value = "ct_option", required = false) List<Integer> ctOptions,
    //         @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles
    // ) throws IOException {

    //     // 기존 이미지 불러오기
    //     List<CustomerImgDto> existingImgs = somheadalService.readCustomerImgData(ctIdPk);
    //     List<CustomerImgDto> imgList = new ArrayList<>();

    //     // 이미지 처리
    //     if (imageFiles != null && !imageFiles.isEmpty() && imageFiles.stream().anyMatch(f -> !f.isEmpty())) {
    //         String rootPath = "C:/somUploadFiles/";
    //         String todayPath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
    //         File folder = new File(rootPath + todayPath);
    //         if (!folder.exists()) folder.mkdirs();

    //         for (MultipartFile file : imageFiles) {
    //             if (file.isEmpty()) continue;
    //             String originalFileName = file.getOriginalFilename();
    //             String uuid = UUID.randomUUID().toString();
    //             long currentTime = System.currentTimeMillis();
    //             String fileName = uuid + "_" + currentTime + 
    //                     originalFileName.substring(originalFileName.lastIndexOf("."));
    //             String fullPath = rootPath + todayPath + fileName;
    //             file.transferTo(new File(fullPath));

    //             CustomerImgDto imgDto = new CustomerImgDto();
    //             imgDto.setCi_img_url(todayPath + fileName);
    //             imgList.add(imgDto);
    //         }

    //         somheadalService.deleteCustomerImgs(ctIdPk);
    //     } else {
    //         imgList = existingImgs;
    //     }

    //     // 옵션 삭제 후 새로 insert
    //     somheadalService.deleteCustomerOptions(ctIdPk);
    //     List<CustomerOption> customerOptionList = new ArrayList<>();
    //     if (ctOptions != null && !ctOptions.isEmpty()) {
    //         for (Integer poId : ctOptions) {
    //             CustomerOption opt = new CustomerOption();
    //             opt.setCt_id_pk(ctIdPk);
    //             opt.setPo_id_pk(poId);
    //             opt.setMp_id_pk(mainProductId); // main_product 연결
    //             customerOptionList.add(opt);
    //         }
    //     }

    //     // 고객 정보 세팅
    //     customerInfoDto.setCt_id_pk(ctIdPk);
    //     customerInfoDto.setCt_type(mainProductId);

    //     // 업데이트
    //     somheadalService.updateCustomerAndOptions(customerInfoDto, customerOptionList, imgList);

    //     return "redirect:./customerEditPage?ct_id_pk=" + ctIdPk;
    // }

    @PostMapping("/customerEditProcess")
    public String customerEditProcess(
            @RequestParam("ct_id_pk") int ctIdPk,
            @RequestParam("ct_type") int mainProductId,
            CustomerInfoDto customerInfoDto,
            @RequestParam(value = "ct_option", required = false) List<Integer> ctOptions,
            @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles
    ) throws IOException {

        //  기존 고객 정보 / 이미지 / 옵션 불러오기
        List<CustomerImgDto> existingImgs = somheadalService.readCustomerImgData(ctIdPk);
        List<CustomerOption> existingOptions = somheadalService.readCustomerOptions(ctIdPk);

        // customerInfoDto.setCt_id_pk(ctIdPk);
        // if (mainProductId != null) {
        //     customerInfoDto.setCt_type(mainProductId);
        // }

        //  이미지 업로드 처리
        String rootPath = "C:/somUploadFiles/";
        String todayPath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
        File folder = new File(rootPath + todayPath);
        if (!folder.exists()) folder.mkdirs();

        List<CustomerImgDto> imgList = new ArrayList<>();

        if (imageFiles != null && !imageFiles.isEmpty() && imageFiles.stream().anyMatch(f -> !f.isEmpty())) {
            // 새 이미지가 있다면 기존 이미지 삭제 후 교체
            somheadalService.deleteCustomerImgs(ctIdPk);
            for (MultipartFile file : imageFiles) {
                String originalFileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                long currentTime = System.currentTimeMillis();
                String fileName = uuid + "_" + currentTime + originalFileName.substring(originalFileName.lastIndexOf("."));
                String fullPath = rootPath + todayPath + fileName;

                file.transferTo(new File(fullPath));

                CustomerImgDto imgDto = new CustomerImgDto();
                imgDto.setCt_id_pk(ctIdPk);
                imgDto.setCi_img_url(todayPath + fileName);
                imgList.add(imgDto);
            }
        } else {
            // 업로드 없으면 기존 이미지 유지
            imgList = existingImgs;
        }

        //  옵션 업데이트
        somheadalService.deleteCustomerOptions(ctIdPk); // 기존 옵션 초기화
        
        List<CustomerOption> optionList = new ArrayList<>();

        if (ctOptions != null && !ctOptions.isEmpty()) {
            for (int poId : ctOptions) {
                CustomerOption opt = new CustomerOption();
                opt.setCt_id_pk(ctIdPk);
                opt.setPo_id_pk(poId);
                opt.setMp_id_pk(mainProductId);
                optionList.add(opt);
            }
        } else {
            optionList = existingOptions; // 선택 없으면 기존 옵션 유지
        }

        //  업데이트 실행 (트랜잭션)
        somheadalService.updateCustomerAndOptions(customerInfoDto, optionList, imgList);

        // 리다이렉트
        return "redirect:./customerEditPage?ct_id_pk=" + ctIdPk;
    }




    

    @GetMapping("customerDetailPage")
    public String customerDetailPage(HttpSession session, Model model, @RequestParam("ct_id_pk") int ct_id_pk,
                                        CustomerImgDto customerImgDto, CustomerInfoDto customerInfoDto,
                                        CustomerOption customerOption, DeliveryInfo deliveryInfo) {

        model.addAttribute("customerReadOptions",somheadalService.readCustomerOptions(ct_id_pk));
        model.addAttribute("customerReadApply", somheadalService.readCustomerAdds(ct_id_pk));
        model.addAttribute("totalSums", somheadalService.readSumOptionsOne(ct_id_pk));
        model.addAttribute("readCustomerImgData", somheadalService.readCustomerImgData(ct_id_pk));
        model.addAttribute("readCustomerDeliNb", somheadalService.readCustomerDeliInfo(ct_id_pk));
        //System.out.println("ct _ id _ pk =" + somheadalService.readCustomerAdds(ct_id_pk));
        //System.out.println("options : " + somheadalService.readCustomerOptions(ct_id_pk));
        //System.out.println("이미지 데이터 = " + somheadalService.readCustomerImgData(ct_id_pk));
        return "customerDetailPage";
    }

    @GetMapping("customerEditPage")
    public String customerEditPage(HttpSession session, Model model, @RequestParam("ct_id_pk") int ct_id_pk,
                                        CustomerImgDto customerImgDto, CustomerInfoDto customerInfoDto,
                                        CustomerOption customerOption, DeliveryInfo deliveryInfo) {

                                            
        model.addAttribute("readProducts", somheadalService.readMainProduct());
        model.addAttribute("readOptions", somheadalService.readOptions());
        model.addAttribute("deliverys", somheadalService.readDeliveryOptions());
        
        model.addAttribute("customerReadOptions",somheadalService.readCustomerOptions(ct_id_pk));
        model.addAttribute("customerReadApply", somheadalService.readCustomerAdds(ct_id_pk));
        model.addAttribute("readCustomerImgData", somheadalService.readCustomerImgData(ct_id_pk));
        //System.out.println("ct _ id _ pk =" + somheadalService.readCustomerAdds(ct_id_pk));
        //System.out.println("options : " + somheadalService.readCustomerOptions(ct_id_pk));
        //System.out.println("이미지 데이터 = " + somheadalService.readCustomerImgData(ct_id_pk));
        return "customerEditPage";
    }




    
    
    
        @GetMapping("another")
    public String another(HttpSession session, Model model, @RequestParam("ct_id_pk") int ct_id_pk,
                                        CustomerImgDto customerImgDto, CustomerInfoDto customerInfoDto,
                                        CustomerOption customerOption,
                                        DeliveryOptionDto deliveryOptionDto) {
        model.addAttribute("customerReadOptions",somheadalService.readCustomerOptions(ct_id_pk));
        model.addAttribute("customerReadApply", somheadalService.readCustomerAdds(ct_id_pk));
        model.addAttribute("deliverys", somheadalService.readDeliveryOptions());
        System.out.println("ct _ id _ pk =" + somheadalService.readCustomerAdds(ct_id_pk));


        return "another";
    }

    @PostMapping("/insertCustomerDeliInfo")
    public String insertCustomerDeliInfo(DeliveryInfo deliveryInfo,
                                        @RequestParam("ct_id_pk") int ct_id_pk, Model model) {

        model.addAttribute("ct_id_pk", ct_id_pk);
        somheadalService.insertCustomerDeliInfo(deliveryInfo);
        return "redirect:/customerDetailPage?ct_id_pk=" + deliveryInfo.getCt_id_pk();

    }
        
    



    
    
}

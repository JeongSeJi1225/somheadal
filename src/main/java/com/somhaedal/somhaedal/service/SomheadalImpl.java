package com.somhaedal.somhaedal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.somhaedal.somhaedal.dto.AdminDto;
import com.somhaedal.somhaedal.dto.CustomerAddress;
import com.somhaedal.somhaedal.dto.CustomerImgDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
import com.somhaedal.somhaedal.dto.CustomerOption;
import com.somhaedal.somhaedal.dto.FabricCategoryDto;
import com.somhaedal.somhaedal.dto.FabricManagementDto;
import com.somhaedal.somhaedal.dto.ProducOptiontDto;
import com.somhaedal.somhaedal.dto.ProductDto;
import com.somhaedal.somhaedal.mapper.SomeheadalSqlMapper;

@Service
public class SomheadalImpl {
    
    @Autowired
    public SomeheadalSqlMapper someheadalSqlMapper;

    //작업자 정보
    public AdminDto getAdminLoginInfo(AdminDto adminDto){
        return someheadalSqlMapper.adminLoginInfo(adminDto);
    }

    //작업자 메인페이지 통계, 신청서 접수
    public int getTodayApplyCount(CustomerInfoDto customerInfoDto){
        return someheadalSqlMapper.forCountApply(customerInfoDto);
    }
    
    //원단 카테고리 불러오기
    public List<FabricCategoryDto> getFabricCategoryInfo(){
        return someheadalSqlMapper.readFabricCategory();
    }

    //원단 insert
    public void fabriAddInsert (FabricManagementDto fabricManagementDto){
        someheadalSqlMapper.fabriAddInsert(fabricManagementDto);
    }

    //원단 리스트 불러오기
    public List<FabricManagementDto> getFabricAdds(){
        return someheadalSqlMapper.readFabricAdds();
    }

    //원단 상세보기
    public Map<String, Object> readFabricOnlyOne(int fb_id_pk){

        return someheadalSqlMapper.readFabricOnlyOne(fb_id_pk);
    }

    //원단 입고대기 or 입고완료
    public void updateStock(FabricManagementDto fabricManagementDto){
        someheadalSqlMapper.updateStock(fabricManagementDto);
    }

    //원단 수정 update
    public void updateFabrics(FabricManagementDto fabricManagementDto){
        someheadalSqlMapper.updateFabrics(fabricManagementDto);
    }

    //fabric 관리 count
    public int countFabrics(FabricManagementDto fabricManagementDto){
        return someheadalSqlMapper.countFabrics(fabricManagementDto);
    }

    //입고완료 count
    public int countStocks(FabricManagementDto fabricManagementDto){
        return someheadalSqlMapper.countStocks(fabricManagementDto);
    }

    //입고 대기 count
    public int countStocksWait(FabricManagementDto fabricManagementDto){
        return someheadalSqlMapper.countStocksWait(fabricManagementDto);
    }

    //원단 sum
    public int sumFabrics(FabricManagementDto fabricManagementDto){
        return someheadalSqlMapper.sumFabrics(fabricManagementDto);
    }

    //원단 delete
    public void deleteFabrics(int fb_id_pk){

        someheadalSqlMapper.deleteFabrics(fb_id_pk);
    }

// @Transactional
// public void insertCustomerAndOptions(CustomerInfoDto customerInfoDto, CustomerOption customerOption) {
    
//     // 1️⃣ customer_info insert → PK를 customerInfoDto.ct_id_pk에 세팅
//     someheadalSqlMapper.insertCustomerInfo(customerInfoDto);

//     // 2️⃣ CustomerOption에 생성된 PK 세팅
//     customerOption.setCt_id_pk(customerInfoDto.getCt_id_pk());

//     // 3️⃣ customer_choice_option insert
//     someheadalSqlMapper.checkOptions(customerOption);
// }


 @Transactional
public void insertCustomerAndOptions(CustomerInfoDto customerInfoDto,
                                     List<Integer> ctOptions,
                                     CustomerImgDto customerImgDto) {

    // 1. 고객 기본 정보 insert
    someheadalSqlMapper.insertCustomerInfo(customerInfoDto);

    int customerId = customerInfoDto.getCt_id_pk();

    // 2. 옵션 insert
    if (ctOptions != null && !ctOptions.isEmpty()) {
        for (Integer poId : ctOptions) {
            CustomerOption choice = new CustomerOption();
            choice.setCt_id_pk(customerId);
            choice.setMp_id_pk(customerId); // 상품 타입 연결
            choice.setPo_id_pk(poId);
            someheadalSqlMapper.insertCustomerChoiceOption(choice);
        }
    }

    // 3. 이미지 insert
    if (customerImgDto != null && customerImgDto.getCi_img_url() != null) {
        customerImgDto.setCt_id_pk(customerId);
        someheadalSqlMapper.insertFaceDesign(customerImgDto);
    }

    //고객 배송정보 insert

}

    
    //상품리스트
    public List<ProductDto> readMainProduct(){
        return someheadalSqlMapper.readMainProduct();
    }

    //유료 옵션 리스트 고객 개인 거
    public List<ProducOptiontDto> readOptions(){
        return someheadalSqlMapper.readOptions();
    }

    
    public List<CustomerInfoDto> readCustomerList(){
        return someheadalSqlMapper.readCustomerList();
    }
 
}

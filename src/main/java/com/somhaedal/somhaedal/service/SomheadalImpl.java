package com.somhaedal.somhaedal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somhaedal.somhaedal.dto.AdminDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
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

    //신청서 insert
    public void insertCustomerInfo(CustomerInfoDto customerInfoDto){
        someheadalSqlMapper.insertCustomerInfo(customerInfoDto);
    }

    //상품리스트
    public List<ProductDto> readProducts(){
        return someheadalSqlMapper.readProducts();
    }

    //유료 옵션 리스트
    public List<ProducOptiontDto> readOptions(){
        return someheadalSqlMapper.readOptions();
    }
 
}

package com.somhaedal.somhaedal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somhaedal.somhaedal.dto.AdminDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
import com.somhaedal.somhaedal.dto.FabricCategoryDto;
import com.somhaedal.somhaedal.dto.FabricManagementDto;
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

 
}

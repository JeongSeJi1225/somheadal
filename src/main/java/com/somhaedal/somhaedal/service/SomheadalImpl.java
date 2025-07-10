package com.somhaedal.somhaedal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somhaedal.somhaedal.dto.AdminDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
import com.somhaedal.somhaedal.mapper.SomeheadalSqlMapper;

@Service
public class SomheadalImpl {
    
    @Autowired
    public SomeheadalSqlMapper SomeheadalSqlMapper;

    public AdminDto getAdminLoginInfo(AdminDto adminDto){
        return SomeheadalSqlMapper.adminLoginInfo(adminDto);
    }

    public int getTodayApplyCount(CustomerInfoDto customerInfoDto){
        return SomeheadalSqlMapper.forCountApply(customerInfoDto);
    }

 
}

package com.somhaedal.somhaedal.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.somhaedal.somhaedal.dto.AdminDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
import com.somhaedal.somhaedal.dto.FabricLogDto;
import com.somhaedal.somhaedal.dto.FabricManagementDto;
import com.somhaedal.somhaedal.dto.FabricSwatchDto;
import com.somhaedal.somhaedal.dto.OptionCategoryDto;
import com.somhaedal.somhaedal.dto.ProductionTaskDto;
import com.somhaedal.somhaedal.dto.ProductDto;
import com.somhaedal.somhaedal.dto.SubMaterialDto;
import com.somhaedal.somhaedal.dto.TaskLogDto;
import com.somhaedal.somhaedal.enums.FabricActionType;
import com.somhaedal.somhaedal.enums.NowStatus;

@Mapper
public interface SomeheadalSqlMapper {
    //작업자 로그인
    public AdminDto adminLoginInfo(AdminDto adminDto);

    //작업자 메인페이지 통계, 신청서 접수현황 (Today)
    public int forCountApply(CustomerInfoDto customerInfoDto);

}

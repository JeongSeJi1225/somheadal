package com.somhaedal.somhaedal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.somhaedal.somhaedal.dto.AdminDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
import com.somhaedal.somhaedal.dto.FabricCategoryDto;
import com.somhaedal.somhaedal.dto.FabricLogDto;
import com.somhaedal.somhaedal.dto.FabricManagementDto;
import com.somhaedal.somhaedal.dto.FabricSwatchDto;
import com.somhaedal.somhaedal.dto.OptionCategoryDto;
import com.somhaedal.somhaedal.dto.ProducOptiontDto;
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

    //원단추가페이지에서 카테고리 불러오기
    public List<FabricCategoryDto> readFabricCategory();

    //원단관리 insert
    public void fabriAddInsert(FabricManagementDto fabricManagementDto);

    //원단목록 불러오기
    public List<FabricManagementDto> readFabricAdds();

    //원단 상세보기
    public Map<String, Object> readFabricOnlyOne(int fb_id_pk);

    //입고현황 업데이트
    public void updateStock(FabricManagementDto fabricManagementDto);

    //원단 수정(update)
    public void updateFabrics(FabricManagementDto fabricManagementDto);
    
    //총 원단 종류 count
    public int countFabrics(FabricManagementDto fabricManagementDto);

    //입고완료 count
    public int countStocks(FabricManagementDto fabricManagementDto);
    
    //입고대기 count
    public int countStocksWait(FabricManagementDto fabricManagementDto);
    
    //전체 재고량 sum
    public int sumFabrics(FabricManagementDto fabricManagementDto);

    //원단 삭제
    public void deleteFabrics(int fb_id_pk);

    //고객 신청서 insert
    public void insertCustomerInfo(CustomerInfoDto customerInfoDto);

    //상품 list
    public List<ProductDto> readProducts();

    //상품 추가금 옵션
    public List<ProducOptiontDto> readOptions();
}

package com.somhaedal.somhaedal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.somhaedal.somhaedal.dto.AdminDto;
import com.somhaedal.somhaedal.dto.CustomerAddress;
import com.somhaedal.somhaedal.dto.CustomerImgDto;
import com.somhaedal.somhaedal.dto.CustomerInfoDto;
import com.somhaedal.somhaedal.dto.CustomerOption;
import com.somhaedal.somhaedal.dto.DeliveryInfo;
import com.somhaedal.somhaedal.dto.DeliveryOptionDto;
import com.somhaedal.somhaedal.dto.FabricCategoryDto;
import com.somhaedal.somhaedal.dto.FabricLogDto;
import com.somhaedal.somhaedal.dto.FabricManagementDto;
import com.somhaedal.somhaedal.dto.FabricSwatchDto;
import com.somhaedal.somhaedal.dto.ProductOptiontDto;
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



    //상품 list
    public List<ProductDto> readMainProduct();

    //상품 추가금 옵션
    public List<ProductOptiontDto> readOptions();

    // //옵션 insert
    // public CustomerOption checkOptions(CustomerOption customerOption);
    
    // //고객 신청서 insert
    // public void insertCustomerInfo(CustomerInfoDto customerInfoDto);

    void insertCustomerInfo(CustomerInfoDto customerInfoDto);

    void insertCustomerChoiceOption(CustomerOption customerOption);

    public void insertFaceDesign(CustomerImgDto customerImgDto);

    public List<CustomerInfoDto> readCustomerList();

    void insertCustomerAddress(CustomerAddress customerAddress);

    //고객정보 상세보기
    public List<CustomerInfoDto> readCustomerAdds(int ct_id_pk);
    public List<CustomerImgDto> readCustomerImgData(int ct_id_pk);

    //고객이 선택한 유료 옵션들
    public List<CustomerOption>  readCustomerOptions(int ct_id_pk);
    

    //도안컨펌 버튼
    public void updateFaceCf(CustomerInfoDto customerInfoDto);
    
    //면피컨펌 버튼
    public void updateDesignCf(CustomerInfoDto customerInfoDto);
    
    //배송완료 버튼
    public void updateDeliveryOk(CustomerInfoDto customerInfoDto);

    //배송옵션
    public List<DeliveryOptionDto> readDeliveryOp();

    public Map<String, Object> readSumOptionsOne(int ct_id_pk);
    
    //운송장번호 insert
    public void insertCustomerDeliInfo(DeliveryInfo deliveryInfo);

    
}

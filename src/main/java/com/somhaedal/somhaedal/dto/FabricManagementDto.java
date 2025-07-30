package com.somhaedal.somhaedal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FabricManagementDto {
    private int      fb_id_pk;	
    private int      seq_num;
    private String      fb_type;			
    private String   fb_name; 								
    private float    fb_width; 			
    private float    fb_height; 			
    private String   fb_color; 			
    private float    fb_thickness; 		
    private int      fb_price; 			
    private String   fb_location;
    private String   fb_swatch_img_path;
    private String   fb_source_supply;
    private String   fb_stock_situation;
    @DateTimeFormat(pattern = "yyyy-MM-dd")	
    private LocalDateTime    fb_created_at;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime    fb_last_inert_date_at;

}

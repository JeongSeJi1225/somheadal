package com.somhaedal.somhaedal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FabricManagementDto {
    private int      fb_id_pk;	
    private int      seq_num;			
    private String   fb_name; 			
    private String   fb_type; 			
    private String   fb_numbering; 		
    private String   fb_size; 			
    private float    fb_width; 			
    private float    fb_height; 			
    private String   fb_color; 			
    private float    fb_thickness; 		
    private BigDecimal   fb_price; 			
    private String   fb_location; 		
    private String   fb_source_supply; 	
    private float    fb_now_have; 	
    @DateTimeFormat(pattern = "yyyy-MM-dd")	
    private LocalDateTime    created_at;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime    fb_last_inert_date_at;

}

package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SubMaterialDto {
    private int sm_id_pk;
    private int seq_num;
    private String sm_name;
    private String sm_category;
    private int sm_quantity;
    private int safety_stock;
    private String sm_location;
    private String sm_memo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime sm_create_at;
}

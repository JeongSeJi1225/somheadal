package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FabricCategoryDto {
    private int fc_id_pk;
    private String fc_ct_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fc_created_at;
}

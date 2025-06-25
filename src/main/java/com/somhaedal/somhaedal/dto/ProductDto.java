package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProductDto {
    private int mp_id_pk;
    private String mp_name;
    private String mp_explain;
    private String mp_category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime mp_created_at;
}

package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RealFaceImg {
    private int rf_id_pk;
    private int ct_id_pk;
    private String rf_img_url;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime rf_created_at;
}

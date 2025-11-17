package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CompleteImgDto {
    private int ci_id_pk;
    private int ct_id_pk;
    private String ci_img_url;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime ci_created_at;
}

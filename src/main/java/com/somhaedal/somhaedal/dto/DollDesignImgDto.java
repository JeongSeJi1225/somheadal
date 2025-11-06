package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class DollDesignImgDto {
    private int dd_id_pk;
    private int ct_id_pk;
    private String dd_img_url;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dd_created_at;
}

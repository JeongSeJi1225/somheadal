package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CustomerInfoDto {
    private int ct_id_pk;
    private String ct_name;
    private String ct_phone;
    private String ct_address;
    private int ct_delivery_op;
    private int ct_type;
    private String ct_option;
    private String ct_memo;
    private String ct_char;
    private String ct_char_explain;
    private int ct_how_many;
    private String ct_face_cf;
    private String ct_complete_cf;
    private String ct_design_cf;
    private String ct_deleivery;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime ct_apply_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime ct_end_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime ct_created_at;
}

package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AdminDto {
    private int seq_num;
    private String admin_id;
    private String admin_passwd;
    private String admin_name;
    private String admin_satatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime amdin_last_login_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created_at;
}

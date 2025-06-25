package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProductionTaskDto {
    private int pt_id_pk;
    private int pr_id_pk;
    private int ns_id_pk;
    private int seq_num;
    private String pt_check_item;
    private String pt_memo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime pt_start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime pt_end_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime pt_created_at;
}

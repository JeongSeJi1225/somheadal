package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.somhaedal.somhaedal.enums.NowStatus;

import lombok.Data;

@Data
public class NowStatusDto {
    private int ns_id_pk;
    private NowStatus ns_status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime ns_created_at;
}

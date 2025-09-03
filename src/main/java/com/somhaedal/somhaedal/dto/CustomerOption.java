package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CustomerOption {
    private int oc_id_pk;
    private int ct_id_pk;
    private int mp_id_pk;
    private int po_id_pk;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime oc_created_at;
}

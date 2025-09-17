package com.somhaedal.somhaedal.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CustomerAddress {
    private int di_id_pk;
    private int ct_id_pk;
    private String di_ct_address;
    private String di_delivery_nb;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime di_created_at;

}

package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProductOptiontDto {
    private int po_id_pk;
    private String po_name;
    private int po_extra_price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime po_created_at;
}

package com.somhaedal.somhaedal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import com.somhaedal.somhaedal.enums.FabricActionType;

import lombok.Data;


@Data
public class DeliveryOptionDto {
    private int do_id_pk;
    private String do_name;
    private int do_price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime do_created_at;

}

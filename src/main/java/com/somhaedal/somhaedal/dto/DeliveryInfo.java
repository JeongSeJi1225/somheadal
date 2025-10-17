package com.somhaedal.somhaedal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import com.somhaedal.somhaedal.enums.FabricActionType;

import lombok.Data;


@Data
public class DeliveryInfo {
    private int di_id_pk;
    private int ct_id_pk;
    private String di_delivery_nb;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime di_created_at;

}

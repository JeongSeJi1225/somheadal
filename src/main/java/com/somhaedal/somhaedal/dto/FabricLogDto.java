package com.somhaedal.somhaedal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import com.somhaedal.somhaedal.enums.FabricActionType;

import lombok.Data;


@Data
public class FabricLogDto {
    private int fbl_id_pk;
    private int fb_id_pk;
    private FabricActionType fbl_action_type;
    private int fbl_quantity;
    private String fbl_unit;
    private String fbl_reason;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime chaged_at;

}

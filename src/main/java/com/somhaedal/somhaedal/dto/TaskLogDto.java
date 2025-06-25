package com.somhaedal.somhaedal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import com.somhaedal.somhaedal.enums.FabricActionType;

import lombok.Data;


@Data
public class TaskLogDto {
    private int tl_id_pk;
    private int pt_id_pk;
    private String tl_from_status;
    private String tl_status_now;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime tl_changed_at;

}

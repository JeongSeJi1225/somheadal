package com.somhaedal.somhaedal.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FabricSwatchDto {
    private int ns_id_pk;
    private int fb_id_pk;
    private String ns_url;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created_at;
}

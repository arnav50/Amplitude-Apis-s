package com.trantor.amplitude.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFieldDto {

    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty("secret_key")
    private String secretKey;

    @DateTimeFormat(pattern = "YYYYMMDD")
    @JsonProperty("start_date")
    private String startDate;
    @DateTimeFormat(pattern = "YYYYMMDD")
    @JsonProperty("end_date")
    private String endDate;


}

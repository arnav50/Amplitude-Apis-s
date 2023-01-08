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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("start_date")
    private String startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("end_date")
    private String endDate;


}

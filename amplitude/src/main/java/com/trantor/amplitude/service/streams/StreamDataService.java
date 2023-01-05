package com.trantor.amplitude.service.streams;

import com.trantor.amplitude.client.ExternalHttpClient;
import com.trantor.amplitude.constants.ApplicationConstants;
import com.trantor.amplitude.enums.SchemaObjectEnum;
import com.trantor.amplitude.utils.BearerTokenRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class StreamDataService {

    @Autowired
    ExternalHttpClient httpClient;

    @Value("${amplitude.auth.baseurl}")
    private String authBaseUrl;


    public String getStreamObjectPaylod(
            Map<String, String> requestHeaders,   String streamId) {
        String response = "";
        log.info("inside service :  requestHeaders {} , streamId {},", requestHeaders, streamId);
        String url = buildObjectUrl(streamId);
        response = getStreamData(url, requestHeaders);
        return response;
    }

    public String buildObjectUrl(
            String streamId) {

        String finalUrl = "";
        try {
            if(SchemaObjectEnum.Active_Users_Counts.getStreamObjectName().equals(streamId)) {
               return finalUrl = authBaseUrl + ApplicationConstants.User + ApplicationConstants.Start + BearerTokenRequest.startDate +ApplicationConstants.End + BearerTokenRequest.endDate;
            } else if (SchemaObjectEnum.Average_Session_Length.getStreamObjectName().equals(streamId)){
                return finalUrl = authBaseUrl + ApplicationConstants.Average_Session_Length + ApplicationConstants.Start + BearerTokenRequest.startDate + ApplicationConstants.End + BearerTokenRequest.endDate;
            } else if (SchemaObjectEnum.Events.getStreamObjectName().equals(streamId)){
                return finalUrl = authBaseUrl + ApplicationConstants.Events;
            } else if (SchemaObjectEnum.Annotations.getStreamObjectName().equals(streamId)){
                return finalUrl = authBaseUrl + ApplicationConstants.Annotations ;
            } else if (SchemaObjectEnum.Cohorts.getStreamObjectName().equals(streamId)){
                return finalUrl = authBaseUrl + ApplicationConstants.Cohorts;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalUrl;
    }


    public String getStreamData(
            String baseUrl,
            Map<String, String> requestHeaders) {
        ResponseEntity<String> responseDto = httpClient.sendGetRequest(baseUrl,  requestHeaders, MediaType.APPLICATION_JSON, String.class);
        log.info("response DTO : {}", responseDto );
        return extractStreamObjectFromJsonResponseForV3Object(
                responseDto);
    }


    private String extractStreamObjectFromJsonResponseForV3Object(
            ResponseEntity<String> response) {

        JSONObject payloads = new JSONObject();
        JSONArray dataArray = new JSONArray();
        JSONObject payloadData = new JSONObject(response.getBody());
        dataArray.put(payloadData);
        payloads.put("payload", dataArray);
        return payloads.toString();


    }
}

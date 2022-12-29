package com.trantor.amplitude.service.streams;

import com.trantor.amplitude.client.ExternalHttpClient;
import com.trantor.amplitude.enums.SchemaObjectEnum;
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
        response = getStreamData(url, requestHeaders, streamId);
        return response;
    }

    public String buildObjectUrl(
            String streamId) {
        String finalUrl = "";
        try {
            if(SchemaObjectEnum.Active_Users_Counts.getStreamObjectName().equals(streamId)) {
                return finalUrl = authBaseUrl + "2/users?start=20220401&end=20220402";
            } else if (SchemaObjectEnum.Average_Session_Length.getStreamObjectName().equals(streamId)){
                return finalUrl = authBaseUrl + "2/sessions/average?start=20210601&end=20210630";
            } else if (SchemaObjectEnum.Events.getStreamObjectName().equals(streamId)){
                return finalUrl = authBaseUrl + "2/events/list";
            } else if (SchemaObjectEnum.Annotations.getStreamObjectName().equals(streamId)){
                return finalUrl = authBaseUrl + "2/annotations" ;
            } else if (SchemaObjectEnum.Cohorts.getStreamObjectName().equals(streamId)){
                return finalUrl = authBaseUrl + "3/cohorts";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalUrl;
    }


    public String getStreamData(
            String baseUrl,
            Map<String, String> requestHeaders,
            String streamId) {
        ResponseEntity<String> responseDto = httpClient.sendGetRequest(baseUrl,  requestHeaders, MediaType.APPLICATION_JSON, String.class);
        log.info("response DTO : {}", responseDto );
        return extractStreamObjectFromJsonResponseForV3Object(
                responseDto, streamId, requestHeaders);
    }


    private String extractStreamObjectFromJsonResponseForV3Object(
            ResponseEntity<String> response,
            String streamId,
            Map<String, String> requestHeaders) {

        JSONObject payloads = new JSONObject();
        JSONArray dataArray = new JSONArray();
        JSONObject payloadData = new JSONObject(response.getBody());


        dataArray.put(payloadData);
        payloads.put("payload", dataArray);
        return payloads.toString();


    }
}

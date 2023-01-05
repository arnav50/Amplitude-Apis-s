package com.trantor.amplitude.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@Slf4j
public class ExternalHttpClient {

    public static final String API_RESPONSE_CODE = "API response code: {}";
    public static final String FINAL_URL = "Final url:  {}";
    @Autowired
    RestTemplate restTemplate;

    /**
     * Method to send the HTTP GET request.
     *
     * @param <T>
     * @param url
     * @param requestParams
     * @param mediaType
     * @return
     */
    public <T> ResponseEntity<T> sendGetRequest(String url, Map<String, String> requestHeaders, MediaType mediaType, Class<T> responseDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        log.info(FINAL_URL, builder.build(false).toUriString());

        HttpHeaders restHeaders = prepareHeaders(requestHeaders, mediaType);

        restHeaders.add("X-PrettyPrint", "1");

        MultiValueMap<String, String> mv2Map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> restRequest = new HttpEntity<>(mv2Map, restHeaders);

        ResponseEntity<T> response = restTemplate.exchange(builder.build(false).toUriString(), HttpMethod.GET, restRequest, responseDto);
        log.info(API_RESPONSE_CODE, response.getStatusCode().value());
        return response;
    }
    private HttpHeaders prepareHeaders(Map<String, String> requestHeaders, MediaType mediaType) {
        HttpHeaders restHeaders = new HttpHeaders();
        restHeaders.setContentType(mediaType);


        for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
            restHeaders.add(entry.getKey(), entry.getValue());
        }
        return restHeaders;
    }

}

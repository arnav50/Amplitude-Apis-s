package com.trantor.amplitude.controller.streams;

import com.trantor.amplitude.constants.ApplicationConstants;
import com.trantor.amplitude.controller.ResponseInterceptor;
import com.trantor.amplitude.service.streams.StreamDataService;
import com.trantor.amplitude.utils.BearerTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StreamDataController {

    @Autowired
    private StreamDataService streamDataService;

    @GetMapping("/stream/{streamId}")
    public ResponseEntity<String> getStreamDataByQuery(
            @RequestHeader(value = ApplicationConstants.USER_FIELDS_DATA, required = true) String userFieldsData,
            @PathVariable(value = ApplicationConstants.StreamID, required = true) String streamId) {
        Map<String, String> requestHeaders = new HashMap<>();
        String userFields = BearerTokenRequest.generateBase64AuthToken(userFieldsData);
        requestHeaders.put(ApplicationConstants.Authorization, userFields);
        return ResponseInterceptor.prepareResponseEntity(streamDataService.getStreamObjectPaylod(requestHeaders, streamId));
    }



}


package com.trantor.amplitude.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trantor.amplitude.constants.ApplicationConstants;
import com.trantor.amplitude.dto.UserFieldDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class BearerTokenRequest {


    public static String startDate = null;

    public static String endDate = null;

    public static String generateBase64AuthToken(String ecodedUserFieldsData) {
        return decodeUserFieldsData(ecodedUserFieldsData);
    }

    public static String decodeUserFieldsData(String ecodedUserFieldsData) {
        String userFields = extractUserFieldsObjectFromJsonInput(ecodedUserFieldsData);
        return userFields;
    }


    public static String extractUserFieldsObjectFromJsonInput(String encodedUserFieldsJson) {

        ObjectMapper objectMapper = new ObjectMapper();
        String auth = "";
        String decodedUserFieldsJson;
        UserFieldDto userFields = null;
        if (encodedUserFieldsJson != null && !(encodedUserFieldsJson.isEmpty())) {
            decodedUserFieldsJson = decode(encodedUserFieldsJson);
            log.debug("User Fields from request header found: {}", decodedUserFieldsJson);
            System.out.println("decodedUserFieldsJson : " + decodedUserFieldsJson);


            JSONArray userFieldsArray = new JSONArray(decodedUserFieldsJson);
            Map<String, String> userFieldsMap = new HashMap<>();
            userFieldsArray.iterator().forEachRemaining(userFieldObject -> {
                if (userFieldObject instanceof JSONObject) {
                    JSONObject userFieldJsonObject = ((JSONObject) userFieldObject);
                    String fieldName = userFieldJsonObject.getString(ApplicationConstants.USER_FIELD_NAME);
                    String fieldValue = userFieldJsonObject.getString(ApplicationConstants.USER_FIELD_VALUE);
                    userFieldsMap.put(fieldName, fieldValue);
                }
            });
            userFields = objectMapper.convertValue(userFieldsMap, UserFieldDto.class);
            startDate = userFields.getStartDate().toString();
            endDate = userFields.getEndDate().toString();
        }


        String authKey = userFields.getApiKey() + ":" + userFields.getSecretKey();
        byte[] encodedByte = Base64.getEncoder().encode(authKey.getBytes());
        auth = "Basic " + Base64.getEncoder().encodeToString(authKey.getBytes());
        String uri = auth;


        return uri;
    }


    public static String decode(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        decodedString = StringEscapeUtils.unescapeJava(decodedString);
        if (decodedString.startsWith("\"")) {
            decodedString = decodedString.substring(1, decodedString.length() - 1);
        }
        return decodedString;
    }

}



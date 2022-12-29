package com.trantor.amplitude.utils;


import lombok.extern.slf4j.Slf4j;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BearerTokenRequest {
    public static String generateBase64AuthToken(String ecodedUserFieldsData) {
        return decodeUserFieldsData(ecodedUserFieldsData);
    }

    public static String decodeUserFieldsData(String ecodedUserFieldsData) {
        String userFields = extractUserFieldsObjectFromJsonInput(ecodedUserFieldsData);
        return userFields;
    }

//    public static UserFieldsDto decodeUserFieldsData(String ecodedUserFieldsData) {
//        UserFieldsDto userFields = extractUserFieldsObjectFromJsonInput(ecodedUserFieldsData);
//        return userFields;
//    }
    public static String extractUserFieldsObjectFromJsonInput(String encodedUserFieldsJson) {

       // ObjectMapper objectMapper = new ObjectMapper();
        String auth="";
        String decodedUserFieldsJson;
        if (encodedUserFieldsJson != null && !(encodedUserFieldsJson.isEmpty())) {
            decodedUserFieldsJson = decode(encodedUserFieldsJson);
            log.debug("User Fields from request header found: {}", decodedUserFieldsJson);
            JSONArray jsonArray = new JSONArray(decodedUserFieldsJson);
//          String authKey = "Basic " + jsonArray.getJSONObject(0).get("userFieldValue") + ":" + jsonArray.getJSONObject(1).get("userFieldValue");
            String authKey = jsonArray.getJSONObject(1).get("userFieldValue") + ":" + jsonArray.getJSONObject(0).get("userFieldValue");
            System.out.println(authKey);
            //byte[] encodedByte = Base64.getEncoder().encode(authKey);
            auth = "Basic " + Base64.getEncoder().encodeToString(authKey.getBytes());
            System.out.println(auth);
        }


//           JSONArray userFieldsArray = new JSONArray(decodedUserFieldsJson);
//           Map<String, String> userFieldsMap = new HashMap<>();
//            userFieldsArray.iterator().forEachRemaining(userFieldObject -> {
//                if (userFieldObject instanceof JSONObject) {
//                    JSONObject userFieldJsonObject = ((JSONObject) userFieldObject);
//                    String fieldName = userFieldJsonObject.getString(ApplicationConstants.USER_FIELD_NAME);
//                    String fieldValue = userFieldJsonObject.getString(ApplicationConstants.USER_FIELD_VALUE);
//                    userFieldsMap.put(fieldName, fieldValue);
//                }
//            });
//            userFields = objectMapper.convertValue(userFieldsMap, UserFieldsDto.class);
//        }
        return auth;
//        return userFields.toString();
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



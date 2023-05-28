package com.gachon.springtermproject.common;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class DataParseBuilder {

    private final MessageSource messageSource;
    public JSONArray getResponse(String url) throws ParseException, HttpClientErrorException.NotFound {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", messageSource.getMessage("API_Key", null, null));
        headers.set("X-RapidAPI-Host", "sofasport.p.rapidapi.com");
        String requestBody = "";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (HttpClientErrorException.NotFound ex) {
            System.out.println("API 요청 중에 예외가 발생했습니다.");
            System.out.println(ex.getMessage());
            return null;
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
        JSONArray resultArray;
        if (url.contains("/tournaments/seasons")) {
            resultArray = (JSONArray) ((JSONObject) jsonObject.get("data")).get("seasons");
        } else if (url.contains("/seasons/standings")) {
            resultArray = (JSONArray) ((JSONObject) ((JSONObject) jsonObject.get("data")).get(0)).get("rows");
        } else if (url.contains("/teams/players")) {
            resultArray = (JSONArray) ((JSONObject) jsonObject.get("data")).get("players");
        } else
            resultArray = (JSONArray) jsonObject.get("data");

        return resultArray;
    }

    public Timestamp toTimeStamp(long num){
        return new Timestamp(num * 1000);
    }

    public String nameTrimmer(String name){
        String result1 = name.trim().replaceAll("-", "");
        int frontLength = result1.indexOf(' ');
        int backLength = result1.length() - result1.lastIndexOf(' ') - 1;

        if(frontLength < backLength)
            return result1;
        else {
            String[] parts = result1.split("\\s+");
            return parts[1] + " " +parts[0];
        }
    }
}

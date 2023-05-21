package com.gachon.springtermproject.controller;

import com.gachon.springtermproject.dto.SetScheduleRequestDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

@Controller
public class SportsController {

    private final String API_key = "e96f65b5a5msh1ecbf16e689e63fp1b323ejsn6ab74ced92fe";
    private final String API_value = "sportscore1.p.rapidapi.com";

    @PostMapping("/api/setschedule")
    @ResponseBody
    public String setSchedule(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";

        String apiUrl = "https://sportscore1.p.rapidapi.com/events/search?sport_id=1&date_end=2023-05-25&date_start=2023-05-01&home_team_id=139"; // SportScore API의 엔드포인트 URL

        final HttpHeaders headers = new HttpHeaders();

        headers.set("X-RapidAPI-Key", API_key);
        headers.set("X-RapidAPI-Host", API_value);

        String requestBody = "{\"sport_id\":\"1\", \"date_end\":\"2023-05-20\", \"date_start\":\"2023-05-14\", \"home_team_id\":\"139\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.
                exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String result = response.getBody();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        JSONObject dataResult = (JSONObject) jsonObject.get("data");
        System.out.println(dataResult.get("id"));
        System.out.println(dataResult.get("name"));
        System.out.println(dataResult.get("start_at"));
        return "Update Successful";
    }
}

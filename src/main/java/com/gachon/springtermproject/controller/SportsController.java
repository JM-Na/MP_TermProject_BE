package com.gachon.springtermproject.controller;

import com.gachon.springtermproject.dto.SetScheduleRequestDto;
import com.gachon.springtermproject.entity.Category;
import com.gachon.springtermproject.entity.Season;
import com.gachon.springtermproject.entity.Sports;
import com.gachon.springtermproject.entity.Tournament;
import com.gachon.springtermproject.repository.CategoryRepository;
import com.gachon.springtermproject.repository.SeasonRepository;
import com.gachon.springtermproject.repository.SportsRepository;
import com.gachon.springtermproject.repository.TournamentRepository;
import org.json.simple.JSONArray;
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

    private final SportsRepository sportsRepository;
    private final CategoryRepository categoryRepository;
    private final TournamentRepository tournamentRepository;
    private final SeasonRepository seasonRepository;
    public SportsController(SportsRepository sportsRepository, CategoryRepository categoryRepository, TournamentRepository tournamentRepository,SeasonRepository seasonRepository){
        this.sportsRepository = sportsRepository;
        this.categoryRepository = categoryRepository;
        this.tournamentRepository = tournamentRepository;
        this.seasonRepository = seasonRepository;
    }

    @PostMapping("/api/setdata")
    @ResponseBody
    public String setData(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";

        String sportUrl = "https://sofasport.p.rapidapi.com/v1/sports";
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "e96f65b5a5msh1ecbf16e689e63fp1b323ejsn6ab74ced92fe");
        headers.set("X-RapidAPI-Host", "sofasport.p.rapidapi.com");
        String requestBody = "";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.
                exchange(sportUrl, HttpMethod.GET, entity, String.class);
        String result = response.getBody();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        JSONArray resultArray = (JSONArray) jsonObject.get("data");
        int i = 0;
        while(resultArray.size()>i){
            JSONObject temp = (JSONObject) resultArray.get(i);
            Sports sports = new Sports((Long) temp.get("id"), (String) temp.get("name"));
            sportsRepository.save(sports);
            i++;
        }
        // category when sport_id = 1 / 1, 7, 30, 31, 32, 291
        String categoryUrl1 = "https://sofasport.p.rapidapi.com/v1/categories?sport_id=1";
        String result_category1 = restTemplate.exchange(categoryUrl1,
                HttpMethod.GET, entity, String.class).getBody();
        JSONParser jsonParser1 = new JSONParser();
        JSONObject jsonObject1 = (JSONObject) jsonParser1.parse(result_category1);
        JSONArray resultArray1 = (JSONArray) jsonObject1.get("data");
        i = 0;
        while(resultArray1.size()>i){
            JSONObject temp = (JSONObject) resultArray1.get(i);
            if((Long)temp.get("id")==1||(Long)temp.get("id")==7||(Long)temp.get("id")==30||
                    (Long)temp.get("id")==31||(Long)temp.get("id")==32||(Long)temp.get("id")==291){
                Category category = new Category((Long)temp.get("id"), (String)temp.get("name"), sportsRepository.findById(1L).get());
                categoryRepository.save(category);
            }
            i++;
        }

        // category when sport_id = 2 / 15, 375
        String categoryUrl2 = "https://sofasport.p.rapidapi.com/v1/categories?sport_id=2";
        String result_category2 = restTemplate.exchange(categoryUrl2,
                HttpMethod.GET, entity, String.class).getBody();
        JSONParser jsonParser2 = new JSONParser();
        JSONObject jsonObject2 = (JSONObject) jsonParser2.parse(result_category2);
        JSONArray resultArray2 = (JSONArray) jsonObject2.get("data");
        i = 0;
        while(resultArray2.size()>i){
            JSONObject temp = (JSONObject) resultArray2.get(i);
            if((Long)temp.get("id")==15||(Long)temp.get("id")==375){
                Category category = new Category((Long)temp.get("id"), (String)temp.get("name"), sportsRepository.findById(2L).get());
                categoryRepository.save(category);
            }
            i++;
        }

        // category when sport_id = 64 / 1374, 1385
        String categoryUrl64 = "https://sofasport.p.rapidapi.com/v1/categories?sport_id=64";
        String result_category64 = restTemplate.exchange(categoryUrl64,
                HttpMethod.GET, entity, String.class).getBody();
        JSONParser jsonParser64 = new JSONParser();
        JSONObject jsonObject64 = (JSONObject) jsonParser64.parse(result_category64);
        JSONArray resultArray64 = (JSONArray) jsonObject64.get("data");
        i = 0;
        while(resultArray64.size()>i){
            JSONObject temp = (JSONObject) resultArray64.get(i);
            if((Long)temp.get("id")==1374||(Long)temp.get("id")==1385){
                Category category = new Category((Long)temp.get("id"), (String)temp.get("name"), sportsRepository.findById(64L).get());
                categoryRepository.save(category);
            }
            i++;
        }

        return "Update Successful";
    }

    @PostMapping("/api/settournament")
    @ResponseBody
    public String setTournament(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";
        Long[] categories = new Long[]{1L, 7L, 30L, 31L, 32L, 291L, 15L, 375L, 1374L, 1385L};
        String sportUrl = "https://sofasport.p.rapidapi.com/v1/tournaments?category_id=";
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "e96f65b5a5msh1ecbf16e689e63fp1b323ejsn6ab74ced92fe");
        headers.set("X-RapidAPI-Host", "sofasport.p.rapidapi.com");
        String requestBody = "";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        for(int i=0;i<categories.length;i++){
            ResponseEntity<String> response = restTemplate.
                    exchange(sportUrl+categories[i], HttpMethod.GET, entity, String.class);
            String result = response.getBody();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONArray resultArray = (JSONArray) jsonObject.get("data");
            int j = 0;
            while(resultArray.size()>j){
                JSONObject temp = (JSONObject) resultArray.get(j);
                Tournament tournament = new Tournament((Long) temp.get("id"),
                        (Long) temp.get("uniqueId"), (String) temp.get("name"), categoryRepository.findById(categories[i]).get());

                tournamentRepository.save(tournament);
                j++;
            }
        }
        return "Update Successful";
    }
    @PostMapping("/api/setseason")
    @ResponseBody
    public String setSeason(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";

        Long[] tournaments = tournamentRepository.findAllIds();

        String sportUrl = "https://sofasport.p.rapidapi.com/v1/tournaments/seasons?tournament_id=";
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "e96f65b5a5msh1ecbf16e689e63fp1b323ejsn6ab74ced92fe");
        headers.set("X-RapidAPI-Host", "sofasport.p.rapidapi.com");
        String requestBody = "";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        for(int i=0;i<tournaments.length;i++){
            ResponseEntity<String> response = restTemplate.
                    exchange(sportUrl+tournaments[i], HttpMethod.GET, entity, String.class);
            String result = response.getBody();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONArray resultArray = (JSONArray) jsonObject.get("data");
            int j = 0;
            while(resultArray.size()>j){
                JSONObject temp = (JSONObject) resultArray.get(j);
                Season season = new Season((Long) temp.get("id"), (String) temp.get("name"),
                        tournamentRepository.findById(tournaments[i]).get());
                if(!seasonRepository.existsById(season.getId()))
                    seasonRepository.save(season);
                j++;
            }
        }



        return "Update Successful";
    }
}

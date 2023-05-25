package com.gachon.springtermproject.controller;

import com.gachon.springtermproject.dto.SetScheduleRequestDto;
import com.gachon.springtermproject.entity.*;
import com.gachon.springtermproject.repository.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SportsController {
    private final MessageSource messageSource;
    private final SportsRepository sportsRepository;
    private final CategoryRepository categoryRepository;
    private final TournamentRepository tournamentRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final SeasonTeamRepository seasonTeamRepository;
    private final EventRepository eventRepository;
    private final EventTeamRepository eventTeamRepository;
    private final PlayerRepository playerRepository;
    public SportsController(MessageSource messageSource, SportsRepository sportsRepository, CategoryRepository categoryRepository,
                            TournamentRepository tournamentRepository,SeasonRepository seasonRepository, TeamRepository teamRepository,
                            SeasonTeamRepository seasonTeamRepository, EventTeamRepository eventTeamRepository, PlayerRepository playerRepository,
                            EventRepository eventRepository){
        this.sportsRepository = sportsRepository;
        this.categoryRepository = categoryRepository;
        this.tournamentRepository = tournamentRepository;
        this.seasonRepository = seasonRepository;
        this.messageSource = messageSource;
        this.teamRepository = teamRepository;
        this.seasonTeamRepository = seasonTeamRepository;
        this.eventRepository = eventRepository;
        this.eventTeamRepository = eventTeamRepository;
        this.playerRepository = playerRepository;
    }
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
        if (url.contains("tournaments/seasons?tournament_id=")) {
            resultArray = (JSONArray) ((JSONObject) jsonObject.get("data")).get("seasons");
        } else if (url.contains("teams-statistics/result?seasons_statistics_type=overall&")) {
            resultArray = (JSONArray) ((JSONObject) jsonObject.get("data")).get("avgRating");
        } else if (url.contains("teams/players?team_id=")) {
            resultArray = (JSONArray) ((JSONObject) jsonObject.get("data")).get("players");
        } else
            resultArray = (JSONArray) jsonObject.get("data");

        return resultArray;
    }

    @PostMapping("/api/setdata")
    @ResponseBody
    public String setData(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";

        String sportUrl = "https://sofasport.p.rapidapi.com/v1/sports";

        JSONArray resultArray = getResponse(sportUrl);
        int i = 0;
        while(resultArray.size()>i){
            JSONObject temp = (JSONObject) resultArray.get(i);
            Sports sports = new Sports((Long) temp.get("id"), (String) temp.get("name"));
            sportsRepository.save(sports);
            i++;
        }
        Long [] sportsIds = new Long[]{1L, 2L, 64L};
        Long[] categories = new Long[]{1L, 7L, 30L, 31L, 32L, 291L, 15L, 375L, 1374L, 1385L};
        for(Long sportsId : sportsIds){
            String url = "https://sofasport.p.rapidapi.com/v1/categories?sport_id=";
            JSONArray resultArray1 = getResponse(url + sportsId);
            if(resultArray1!=null){
                int j = 0;
                while(resultArray1.size()>j){
                    JSONObject temp = (JSONObject) resultArray1.get(j);
                    if(Arrays.asList(categories).contains((Long)temp.get("id"))){
                        if(sportsRepository.findById(sportsId).isPresent()){
                            Category category = new Category((Long)temp.get("id"),
                                    (String)temp.get("name"), sportsRepository.findById(sportsId).get());
                            categoryRepository.save(category);
                        }
                    }
                    j++;
                }
            }
        }
        return "Update Successful";
    }

    @PostMapping("/api/settournament")
    @ResponseBody
    public String setTournament(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";
        String[] names = new String[]{"Premier League", "League One", "Ligue 1", "Serie A", "LaLiga", "Bundesliga",
                "NBA", "K League 1", "KBL", "KBO", "MLB"};
        List<String> name = new ArrayList<>(List.of(names));
        Long[] categories = new Long[]{1L, 7L, 30L, 31L, 32L, 291L, 15L, 375L, 1374L, 1385L};

        for (Long category : categories) {
            String url = "https://sofasport.p.rapidapi.com/v1/tournaments?category_id=";
            JSONArray resultArray = getResponse(url+category);
            if(resultArray!=null){
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    if(categoryRepository.findById(category).isPresent()&&name.contains((String) temp.get("name"))){
                        Tournament tournament = new Tournament((Long) temp.get("id"),
                                (Long) temp.get("uniqueId"), (String) temp.get("name"), categoryRepository.findById(category).get());
                        tournamentRepository.save(tournament);
                    }
                    count++;
                }
            }
        }
        return "Update Successful";
    }
    @PostMapping("/api/setseason")
    @ResponseBody
    public String setSeason(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";
        // save tournaments' ids in array
        List<Tournament> tournaments = tournamentRepository.findAll();
        // use tournaments' ids in sequence
        for (Tournament tournament : tournaments) {
            String url = "https://sofasport.p.rapidapi.com/v1/tournaments/seasons?tournament_id=";
            JSONArray resultArray = getResponse(url+tournament.getId());
            if(resultArray!=null){
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    if (tournamentRepository.findById(tournament.getId()).isPresent()) {
                        Season season = new Season((Long) temp.get("id"), (String) temp.get("name"),
                                tournamentRepository.findById(tournament.getId()).get());
                        // check if season data is already in repository
                        if (!seasonRepository.existsById(season.getId()))
                            seasonRepository.save(season);
                    }
                    count++;
                }
            }
        }
        return "Update Successful";
    }

    @PostMapping("/api/setteam")
    @ResponseBody
    public String setTeam(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";
        // save seasons' ids in array
        List<Season> seasons = seasonRepository.findAll();
        // use seasons' ids in sequence
        for (Season season : seasons) {
            Long uniqueId = season.getTournament().getUnique_id();
            Long seasonsId = season.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/seasons/teams-statistics/result?seasons_statistics_type=overall&" +
                    "unique_tournament_id=" + uniqueId + "&seasons_id=" + seasonsId;
            JSONArray resultArray = getResponse(url);
            if(resultArray!=null){
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    Team team = new Team((Long) temp.get("id"), (String) temp.get("name"));
                    SeasonTeam seasonTeam = new SeasonTeam(season, team);

                    // check if team data is already in repository
                    if (!teamRepository.existsById(team.getId()))
                        teamRepository.save(team);
                    // check if the same data as season Team exists
                    if (!seasonTeamRepository.existsBySeasonAndTeam(season, team)){
                        seasonTeamRepository.save(seasonTeam);
                        team.getSeasonTeams().add(seasonTeam);
                        season.getSeasonTeams().add(seasonTeam); // 추가 공부 필요
                    }
                    count++;
                }
            }
        }
        return "Update Successful";
    }

    @PostMapping("/api/setplayer")
    @ResponseBody
    public String setPlayer(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";
        // save seasons' ids in array
        List<Team> teams = teamRepository.findAll();
        // use seasons' ids in sequence
        for (Team team : teams) {
            Long teamId = team.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/teams/players?team_id=" + teamId;
            JSONArray resultArray = getResponse(url);
            if(resultArray!=null){
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    Player player = new Player((Long)temp.get("id"), (String) temp.get("name"), (Timestamp) temp.get("dateOfBirthTimestamp"), (Timestamp)temp.get("contractUntilTimestamp"),
                            (Integer) temp.get("height"), (Integer) temp.get("jerseyNumber"), (String) ((JSONObject)temp.get("country")).get("name"), (String) temp.get("position"), (Boolean) temp.get("retired"),team);
                    // check if player data is already in repository
                    if (!playerRepository.existsById(player.getId()))
                        playerRepository.save(player);
                    count++;
                }
            }
        }
        return "Update Successful";
    }
    @PostMapping("/api/setEvent")
    @ResponseBody
    public String setEventByDate(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";
        // save seasons' ids in array
        List<Category> categories = categoryRepository.findAll();
        String date = "20230525";
        // use seasons' ids in sequence
        for (Category category : categories) {
            Long categoryId = category.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/events/schedule/category?date="+date+"&category_id=" + categoryId;
            JSONArray resultArray = getResponse(url);
            if(resultArray!=null){
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    Team teamHome = teamRepository.findByName((String)temp.get("homeTeam"));
                    Team teamAway = teamRepository.findByName((String)temp.get("awayTeam"));
                    String status = (String) ((JSONObject)temp.get("status")).get("description");
                    String roundInfo = (String) ((JSONObject)temp.get("roundInfo")).get("round");
                    Tournament tournament = tournamentRepository.findByName((String)((JSONObject)temp.get("tournament")).get("name"));

                    Event event = new Event((Long)temp.get("id"), tournament, status, roundInfo, (Timestamp) temp.get("startTimestamp"));
                    EventTeam eventTeam = new EventTeam(event, teamHome, teamAway);
                    // check if player data is already in repository
                    if (!eventRepository.existsById(event.getId()))
                        eventRepository.save(event);
                    if (!eventTeamRepository.existsByEvent(eventTeam)){
                        eventTeamRepository.save(eventTeam);
                        teamHome.getEventTeams_home().add(eventTeam);
                        teamAway.getEventTeams_away().add(eventTeam);
                    }
                    count++;
                }
            }
        }
        return "Update Successful";
    }
    @PostMapping("/api/test")
    @ResponseBody
    public String test(@RequestBody SetScheduleRequestDto dto){
        if(!dto.isEnabled())
            return "Update failed";

        return "Update Successful";
    }
}

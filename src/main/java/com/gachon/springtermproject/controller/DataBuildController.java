package com.gachon.springtermproject.controller;

import com.gachon.springtermproject.service.DataBuildService;
import com.gachon.springtermproject.service.PeriodicBuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
@Slf4j
@Controller
@RequiredArgsConstructor
public class DataBuildController {
    private final DataBuildService dataBuildService;
    private final PeriodicBuildService periodicBuildService;
    @PostMapping("/build/data/initial")
    public void buildData() throws ParseException {
        log.info("info log = {}", dataBuildService.setSports());
        log.info("info log = {}", dataBuildService.setCategory());
        log.info("info log = {}", dataBuildService.setTournament());
    }
    @PostMapping("/build/data/annual")
    public void buildAnnualData() throws ParseException {
        log.info("info log = {}", periodicBuildService.setSeason());
        log.info("info log = {}", periodicBuildService.setTeam());
        log.info("info log = {}", periodicBuildService.setPlayer());
        log.info("info log = {}", periodicBuildService.setEventByDate());
    }

    /*@PostMapping("/api/test")
    @ResponseBody
    public String test(@RequestBody SetScheduleRequestDto dto) throws ParseException {
        if(!dto.isEnabled())
            return "Update failed";

        String url1 = "https://sofasport.p.rapidapi.com/v1/sports";
        JSONArray resultArray1 = dataParseBuilder.getResponse(url1);
        int i = 0;
        while(resultArray1.size()>i){
            JSONObject temp = (JSONObject) resultArray1.get(i);
            if((Long)temp.get("id") == 1L){
                Sports sports = new Sports((Long) temp.get("id"), (String) temp.get("name"));
                sportsRepository.save(sports);
            }
            i++;
        }

        String url2 = "https://sofasport.p.rapidapi.com/v1/categories?sport_id=1";
        JSONArray resultArray2 = dataParseBuilder.getResponse(url2);
        if(resultArray2!=null){
            int j = 0;
            while(resultArray2.size()>j){
                JSONObject temp = (JSONObject) resultArray2.get(j);
                if((Long)temp.get("id")==291L){
                    if(sportsRepository.findById(1L).isPresent()){
                        Category category = new Category((Long)temp.get("id"),
                                (String)temp.get("name"), sportsRepository.findById(1L).get());
                        categoryRepository.save(category);
                    }
                }
                j++;
            }
        }

        String url3 = "https://sofasport.p.rapidapi.com/v1/tournaments?category_id=291";
        JSONArray resultArray3 = dataParseBuilder.getResponse(url3);
        if(resultArray3!=null){
            int count = 0;
            while (resultArray3.size() > count) {
                JSONObject temp = (JSONObject) resultArray3.get(count);
                if(categoryRepository.findById(291L).isPresent()&&(Long) temp.get("id")==3284L){
                    Tournament tournament = new Tournament((Long) temp.get("id"),
                            (Long) temp.get("uniqueId"), (String) temp.get("name"), categoryRepository.findById(291L).get());
                    tournamentRepository.save(tournament);
                }
                count++;
            }
        }

        String url4 = "https://sofasport.p.rapidapi.com/v1/tournaments/seasons?tournament_id=3284";
        JSONArray resultArray4 = dataParseBuilder.getResponse(url4);
        if(resultArray4!=null){
            int count = 0;
            while (resultArray4.size() > count) {
                JSONObject temp = (JSONObject) resultArray4.get(count);
                if (tournamentRepository.findById(3284L).isPresent()&&(Long) temp.get("id")==48379L) {
                    Season season = new Season((Long) temp.get("id"), (String) temp.get("name"),
                            tournamentRepository.findById(3284L).get());
                    // check if season data is already in repository
                    if (!seasonRepository.existsById(season.getId()))
                        seasonRepository.save(season);
                }
                count++;
            }
        }

        String url5 = "https://sofasport.p.rapidapi.com/v1/seasons/standings?standing_type=total&seasons_id=48379" +
                "&unique_tournament_id=410";
        JSONArray resultArray5_1 = dataParseBuilder.getResponse(url5);
        JSONArray resultArray5 = (JSONArray) ((JSONObject)resultArray5_1.get(0)).get("rows");
        if(resultArray5!=null){
            int count = 0;
            while (resultArray5.size() > count) {
                JSONObject temp = (JSONObject) resultArray5.get(count);
                temp = (JSONObject) temp.get("team");
                Team team = new Team((Long) temp.get("id"), (String) temp.get("name"));
                Season season = seasonRepository.findById(48379L).get();
                SeasonTeam seasonTeam = new SeasonTeam(season, team);
                // check if team data is already in repository
                if (!teamRepository.existsById((Long) temp.get("id")))
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

        List<Team> teams = teamRepository.findAll();
        // use seasons' ids in sequence
        for (Team team : teams) {
            Long teamId = team.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/teams/players?team_id=" + teamId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    temp = (JSONObject) temp.get("player");
                    if(temp.get("dateOfBirthTimestamp")!=null){
                        String nation = (String) ((JSONObject)temp.get("country")).get("name");
                        String playerName;
                        if(nation!=null&&nation.equals("South Korea"))
                            playerName = nameTrimmer((String) temp.get("name"));
                        else
                            playerName = (String) temp.get("name");
                        Player player = new Player((Long)temp.get("id"), playerName, toTimeStamp((Long) temp.get("dateOfBirthTimestamp")),
                                (Long) temp.get("height"), (Long)temp.get("shirtNumber"), nation, (String) temp.get("position"), team);
                        if(!playerRepository.existsByName(playerName)&& !player.containsNull()){
                            playerRepository.save(player);
                        }
                    }
                    // check if player's name exists
                    count++;
                }
            }
        }

        String[] dates = new String[]{"2023-06-03", "2023-06-03", "2023-06-06","2023-06-07","2023-06-10","2023-06-11","2023-06-24","2023-06-25"};

        for(String date : dates){
            String url = "https://sofasport.p.rapidapi.com/v1/events/schedule/category?date="+date+"&category_id=" + 291;
            JSONArray resultArray7 = dataParseBuilder.getResponse(url);
            if(resultArray7!=null){
                int count = 0;
                while (resultArray7.size() > count) {
                    JSONObject temp = (JSONObject) resultArray7.get(count);
                    Team teamHome = teamRepository.findByName((String) ((JSONObject)temp.get("homeTeam")).get("name"));
                    Team teamAway = teamRepository.findByName((String) ((JSONObject)temp.get("awayTeam")).get("name"));
                    String status = (String) ((JSONObject) temp.get("status")).get("description");
                    Long roundInfo = (Long) ((JSONObject) temp.get("roundInfo")).get("round");
                    Tournament tournament = tournamentRepository.findByName((String) ((JSONObject) temp.get("tournament")).get("name"));
                    Long unique = (Long) ((JSONObject)((JSONObject) temp.get("tournament")).get("uniqueTournament")).get("id");
                    if(unique==410L){
                        Event event = new Event((Long) temp.get("id"), tournament, status, roundInfo, toTimeStamp((Long)temp.get("startTimestamp")) );
                        EventTeam eventTeam = new EventTeam(event, teamHome, teamAway);
                        // check if player data is already in repository
                        if (!eventRepository.existsById(event.getId()))
                            eventRepository.save(event);
                        if (!eventTeamRepository.existsByEvent(event)) {
                            eventTeamRepository.save(eventTeam);
                            teamHome.getEventTeams_home().add(eventTeam);
                            teamAway.getEventTeams_away().add(eventTeam);
                        }
                    }
                    count++;
                }
            }
        }
        return "Update Successful";
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
    }*/
}

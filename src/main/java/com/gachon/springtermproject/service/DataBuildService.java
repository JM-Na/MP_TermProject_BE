package com.gachon.springtermproject.service;

import com.gachon.springtermproject.common.DataParseBuilder;
import com.gachon.springtermproject.entity.Category;
import com.gachon.springtermproject.entity.Sports;
import com.gachon.springtermproject.entity.Tournament;
import com.gachon.springtermproject.repository.CategoryRepository;
import com.gachon.springtermproject.repository.SportsRepository;
import com.gachon.springtermproject.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBuildService {

    private final DataParseBuilder dataParseBuilder;
    private final SportsRepository sportsRepository;
    private final CategoryRepository categoryRepository;
    private final TournamentRepository tournamentRepository;

    public String setSports() throws ParseException {

        String sportUrl = "https://sofasport.p.rapidapi.com/v1/sports";

        JSONArray resultArray = dataParseBuilder.getResponse(sportUrl);
        int count = 0;
        while(resultArray.size()>count){
            JSONObject temp = (JSONObject) resultArray.get(count);
            Sports sports = new Sports((Long) temp.get("id"), (String) temp.get("name"));
            sportsRepository.save(sports);
            count++;
        }
        return "Sports data set complete";
    }

    public String setCategory() throws ParseException {
        List<Sports> sports = sportsRepository.findAll();
        // initially chosen data
        Long[] categories = new Long[]{1L, 7L, 30L, 31L, 32L, 291L, 15L, 375L, 1374L, 1385L};
        for(Sports sport : sports){
            Long sportsId = sport.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/categories?sport_id=" + sportsId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                int count = 0;
                while(resultArray.size()>count){
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    if(Arrays.asList(categories).contains((Long)temp.get("id"))){
                        if(sportsRepository.findById(sportsId).isPresent()){
                            Category category = new Category((Long)temp.get("id"),
                                    (String)temp.get("name"), sportsRepository.findById(sportsId).get());
                            categoryRepository.save(category);
                        }
                    }
                    count++;
                }
            }
        }
        return "Category data set complete";
    }

    public String setTournament() throws ParseException {

        String[] names = new String[]{"Premier League", "League One", "Ligue 1", "Serie A", "LaLiga", "Bundesliga",
                "NBA", "K League 1", "KBL", "KBO", "MLB"};
        List<String> name = new ArrayList<>(List.of(names));
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            Long categoryId = category.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/tournaments?category_id="+categoryId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    if(categoryRepository.findById(categoryId).isPresent()&&name.contains((String) temp.get("name"))){
                        Tournament tournament = new Tournament((Long) temp.get("id"),
                                (Long) temp.get("uniqueId"), (String) temp.get("name"), categoryRepository.findById(categoryId).get());
                        tournamentRepository.save(tournament);
                    }
                    count++;
                }
            }
        }
        return "Tournament data set complete";
    }
}

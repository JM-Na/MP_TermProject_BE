package com.gachon.springtermproject.service;

import com.gachon.springtermproject.common.DataParseBuilder;
import com.gachon.springtermproject.dto.EventByDateRequestDto;
import com.gachon.springtermproject.dto.MyTeamRequestDto;
import com.gachon.springtermproject.entity.Event;
import com.gachon.springtermproject.entity.EventTeam;
import com.gachon.springtermproject.entity.Team;
import com.gachon.springtermproject.repository.EventRepository;
import com.gachon.springtermproject.repository.EventTeamRepository;
import com.gachon.springtermproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyTeamService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventTeamRepository eventTeamRepository;
    private final DataParseBuilder dataParseBuilder;
    public String getMyTeam(MyTeamRequestDto dto){
        String email = dto.getEmail();
        Team team = userRepository.findByEmail(email).getFavoriteTeam();
        return userRepository.findByEmail(email).getNickname()+"/"+team.getName();
    }
    public String getEventByDate(EventByDateRequestDto dto){
        String email = dto.getEmail();

        Timestamp timestamp = dataParseBuilder.stringToTimeStamp(dto.getDate());
        Team team = userRepository.findByEmail(email).getFavoriteTeam();
        List<Event> events = eventRepository.findByDateTime(timestamp);
        for(Event event : events){
            EventTeam eventTeam = eventTeamRepository.findByEvent(event);
            System.out.println(eventTeam.getTeamAway().getName()+ eventTeam.getTeamHome().getName());
            if(eventTeam.getTeamAway()==team||eventTeam.getTeamHome()==team)
                return eventTeam.getTeamHome().getName()+"/"+eventTeam.getTeamAway().getName()+"/"+event.getRoundInfo();
        }
        return "No Events Today";
    }

}

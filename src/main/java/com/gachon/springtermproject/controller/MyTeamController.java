package com.gachon.springtermproject.controller;

import com.gachon.springtermproject.dto.EventByDateRequestDto;
import com.gachon.springtermproject.dto.MyTeamRequestDto;
import com.gachon.springtermproject.dto.UserSignInRequestDto;
import com.gachon.springtermproject.service.MyTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyTeamController {
    private final MyTeamService myTeamService;
    @PostMapping("/myteam")
    @ResponseBody
    public String myTeam(@RequestBody MyTeamRequestDto dto){
        String result = myTeamService.getMyTeam(dto);
        log.info("info log = {}", result);
        return result;
    }
    @PostMapping("/myteam/event")
    @ResponseBody
    public String eventByDate(@RequestBody EventByDateRequestDto dto){
        String result = myTeamService.getEventByDate(dto);
        log.info("info log = {}", result);
        return result;
    }
}

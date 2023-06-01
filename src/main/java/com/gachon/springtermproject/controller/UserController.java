package com.gachon.springtermproject.controller;

import com.gachon.springtermproject.dto.UserLogInRequestDto;
import com.gachon.springtermproject.dto.UserSelectTeamDto;
import com.gachon.springtermproject.dto.UserSignInRequestDto;
import com.gachon.springtermproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @PostMapping("/user/signin")
    @ResponseBody
    public String signIn(@RequestBody UserSignInRequestDto dto){
        String result =userService.signIn(dto);
        log.info("info log = {}", result);
        return result;
    }
    @PostMapping("/user/logIn")
    @ResponseBody
    public String logIn(@RequestBody UserLogInRequestDto dto){
        String result =userService.logIn(dto);
        log.info("info log = {}", result);
        return result;
    }
    @PostMapping("/user/selectteam")
    @ResponseBody
    public String logIn(@RequestBody UserSelectTeamDto dto){
        String result =userService.selectTeam(dto);
        log.info("info log = {}", result);
        return result;
    }
}

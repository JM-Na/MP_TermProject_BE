package com.gachon.springtermproject.controller;

import com.gachon.springtermproject.dto.UserLogInRequestDto;
import com.gachon.springtermproject.dto.UserSignInRequestDto;
import com.gachon.springtermproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    UserService userService;
    @PostMapping("/user/signin")
    public void signIn(@RequestBody UserSignInRequestDto dto){
        log.info("info log = {}", userService.signIn(dto));
    }
    @PostMapping("/user/logIn")
    public void logIn(@RequestBody UserLogInRequestDto dto){
        log.info("info log = {}", userService.logIn(dto));
    }
}

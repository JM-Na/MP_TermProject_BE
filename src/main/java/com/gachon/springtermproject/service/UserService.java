package com.gachon.springtermproject.service;

import com.gachon.springtermproject.dto.UserLogInRequestDto;
import com.gachon.springtermproject.dto.UserSelectTeamDto;
import com.gachon.springtermproject.dto.UserSignInRequestDto;
import com.gachon.springtermproject.entity.User;
import com.gachon.springtermproject.repository.TeamRepository;
import com.gachon.springtermproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public String signIn(UserSignInRequestDto dto){
        if(checkDuplicateEmail(dto.getEmail()))
            return "Sign in failed:Duplicate email found";
        if(dto.getPwd().equals(dto.getChkPwd())){
            /*int age = calculateAge(dto.getDate_of_birth());
            User user = new User(dto.getEmail(), dto.getPwd(), dto.getName(), dto.getNickName(),
                    dto.getDate_of_birth(), age, dto.getNation());*/
            User user = new User(dto.getEmail(), dto.getPwd(), dto.getNickName());
            userRepository.save(user);
            return "User Code:"+user.getId()+"-Successfully signed in";
        }
        return "Error:Unable to sign in";
    }
    public String logIn(UserLogInRequestDto dto){
        if(!checkDuplicateEmail(dto.getEmail()))
            return "Log in failed:Email does not exist";
        else{
            User user = userRepository.findByEmail(dto.getEmail());
            if(!user.getPwd().equals(dto.getPwd()))
                return "Log in failed:Wrong password";
            //세션 또는 JWT 발급
            return "Log in successful!";
        }
    }
    public String selectTeam(UserSelectTeamDto dto){
        String email = dto.getEmail();
        User user = userRepository.findByEmail(email);
        user.setFavorite_team(teamRepository.findByName(dto.getTeam()));

        return "Successful";
    }
    public String logOut(){
        //세션 또는 JWT 검증
        //유효하지 않을 시 강제 로그아웃
        //부여한 세션 또는 JWT 삭제
        return "Log out successful!";
    }
    public Boolean checkDuplicateEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public int calculateAge(Date date) {
        LocalDate curDate = LocalDate.now();
        return Period.between(date.toLocalDate(), curDate).getYears();
    }
}

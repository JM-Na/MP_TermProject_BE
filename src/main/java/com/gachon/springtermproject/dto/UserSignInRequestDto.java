package com.gachon.springtermproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInRequestDto {
    private String email;
    private String pwd;
    private String chkPwd;
    private String name;
    private String nickName;
    private Date date_of_birth;
    private String nation;

}

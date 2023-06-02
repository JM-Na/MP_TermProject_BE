package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (unique = true)
    private String email;
    @Column
    private String pwd;
    @Column
    private String name;
    @Column
    private String nickname;
    @Column
    private int age;
    @Column
    private Date date_of_birth;
    @Column
    private String nation;
    @ManyToOne
    @JoinColumn(name = "favorite_team")
    private Team favoriteTeam;

    @Builder
    public User(String email, String pwd, String name, String nickname, Date date_of_birth, int age, String nation){
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nickname = nickname;
        this.date_of_birth = date_of_birth;
        this.age = age;
        this.nation = nation;
    }
    @Builder
    public User(String email, String pwd, String nickname){
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
    }
}

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
    private int age;
    @Column
    private Date date_of_birth;
    @Column
    private String nation;
    @ManyToOne
    @JoinColumn
    private Team favorite_team;

    @Builder
    public User(String email, String pwd, String name, int age, Date date_of_birth, String nation, Team team){
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.age = age;
        this.date_of_birth = date_of_birth;
        this.nation = nation;
        this.favorite_team = team;
    }
}

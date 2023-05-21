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
@Builder
@NoArgsConstructor
public class Player {
    @Id
    @Column(name="player_id")
    private int id;
    @Column
    private String name;
    @Column
    private int age;
    @Column
    private Date date_of_birth;
    @Column
    private long weight;
    @Column
    private long height;
    @Column
    private int shirt_number;
    @Column
    private String nation;
    @Column
    private String position;
    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Builder
    public Player(int id, String name, int age, Date date_of_birth, long weight,
                  long height, int shirt_number, String nation, String position, Team team){
        this.id = id;
        this.name = name;
        this.age = age;
        this.date_of_birth = date_of_birth;
        this.weight = weight;
        this.height = height;
        this.shirt_number = shirt_number;
        this.nation = nation;
        this.position = position;
        this.team = team;
    }
}

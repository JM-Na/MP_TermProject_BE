package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Player {
    @Id
    @Column(name="player_id")
    private Long id;
    @Column
    private String name;
    @Column
    private int age;
    @Column
    private Timestamp date_of_birth;
    @Column
    private Timestamp contract_until;
    @Column
    private int height;
    @Column
    private int shirt_number;
    @Column
    private String nation;
    @Column
    private String position;
    @Column
    private Boolean retired;
    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Builder
    public Player(Long id, String name, Timestamp date_of_birth, Timestamp contract_until, int height, int shirt_number,
                  String nation, String position, Boolean retired, Team team){
        this.id = id;
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.contract_until = contract_until;
        this.height = height;
        this.shirt_number = shirt_number;
        this.nation = nation;
        this.position = position;
        this.retired = retired;
        this.team = team;
    }
}

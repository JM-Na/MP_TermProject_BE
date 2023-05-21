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
public class Manager {
    @Id
    @Column(name="manager_id")
    private int id;
    @Column
    private String name;
    @Column
    private int age;
    @Column
    private Date date_of_birth;
    @Column
    private String nation;
    @OneToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Builder
    public Manager(int id, String name, int age, Date date_of_birth,String nation, Team team){
        this.id = id;
        this.name = name;
        this.age = age;
        this.date_of_birth = date_of_birth;
        this.nation = nation;
        this.team = team;
    }
}

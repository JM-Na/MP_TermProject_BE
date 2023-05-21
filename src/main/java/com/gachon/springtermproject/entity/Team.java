package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
public class Team {
    @Id
    @Column(name="team_id")
    private int id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name="league_id")
    private League league;

    @Builder
    public Team(int id, String name, League league){
        this.id = id;
        this.name = name;
        this.league = league;
    }
}

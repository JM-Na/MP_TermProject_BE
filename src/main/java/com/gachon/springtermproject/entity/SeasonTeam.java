package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class SeasonTeam {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Season_id")
    private Season season;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public SeasonTeam(Season season, Team team){
        this.season = season;
        this.team = team;
    }
}

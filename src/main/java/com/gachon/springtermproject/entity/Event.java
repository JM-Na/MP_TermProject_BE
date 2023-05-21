package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
public class Event {
    @Id
    private int id;
    @Column
    private String name;
    @OneToOne
    @JoinColumn(name="home_teamId", referencedColumnName = "team_id")
    private Team home_team;
    @OneToOne
    @JoinColumn(name="away_teamId", referencedColumnName = "team_id")
    private Team away_team;
    @ManyToOne
    @JoinColumn(name="league_id")
    private League league;
    @Column
    private String status;
    @Column
    private LocalDateTime date_time;

    @Builder
    public Event(int id, String name, Team home_team, Team away_team,
                 League league, String status, LocalDateTime date_time){
        this.id = id;
        this.name = name;
        this.home_team = home_team;
        this.away_team = away_team;
        this.league = league;
        this.status = status;
        this.date_time = date_time;
    }
}

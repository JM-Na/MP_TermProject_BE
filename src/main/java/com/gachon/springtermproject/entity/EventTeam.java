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
public class EventTeam {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team teamHome;

    @ManyToOne
    @JoinColumn(name = "team_away_id")
    private Team teamAway;

    @Builder
    public EventTeam(Event event, Team teamHome, Team teamAway ){
        this.event = event;
        this.teamHome = teamHome;
        this.teamAway = teamAway;
    }
}

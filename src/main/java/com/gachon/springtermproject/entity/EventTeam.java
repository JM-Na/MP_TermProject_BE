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
    private Team team_home;

    @ManyToOne
    @JoinColumn(name = "team_away_id")
    private Team team_away;

    @Builder
    public EventTeam(Event event, Team team_home, Team team_away ){
        this.event = event;
        this.team_home = team_home;
        this.team_away = team_away;
    }
}

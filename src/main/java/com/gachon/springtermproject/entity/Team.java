package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "team")
    private List<SeasonTeam> seasonTeams = new ArrayList<>();

    @OneToMany(mappedBy = "team_home")
    private List<EventTeam> eventTeams_home = new ArrayList<>();
    @OneToMany(mappedBy = "team_away")
    private List<EventTeam> eventTeams_away = new ArrayList<>();

    @Builder
    public Team(int id, String name, List<SeasonTeam> seasonTeams, List<EventTeam> eventTeams_home, List<EventTeam> eventTeams_away){
        this.id = id;
        this.name = name;
        this.seasonTeams = seasonTeams;
        this.eventTeams_home = eventTeams_home;
        this.eventTeams_away = eventTeams_away;
    }
}

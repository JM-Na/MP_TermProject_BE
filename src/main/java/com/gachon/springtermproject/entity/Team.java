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
@NoArgsConstructor
public class Team {
    @Id
    @Column
    private Long id;
    @Column
    private String name;
    @OneToMany(mappedBy = "team")
    private List<SeasonTeam> seasonTeams = new ArrayList<>();

    @OneToMany(mappedBy = "teamHome")
    private List<EventTeam> eventTeamsHome = new ArrayList<>();
    @OneToMany(mappedBy = "teamAway")
    private List<EventTeam> eventTeamsAway = new ArrayList<>();

    @Builder
    public Team(Long id, String name){
        this.id = id;
        this.name = name;
    }
}

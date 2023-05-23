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
public class Season {
    @Id
    @Column
    private Long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn
    private Tournament tournament;
    @OneToMany(mappedBy = "season")
    private List<SeasonTeam> seasonTeams = new ArrayList<>();

    @Builder
    public Season(Long id, String name, Tournament tournament){
        this.id = id;
        this.name = name;
        this.tournament = tournament;
    }
}

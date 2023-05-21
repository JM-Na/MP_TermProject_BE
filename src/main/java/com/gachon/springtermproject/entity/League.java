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
public class League {
    @Id
    @Column(name="league_id")
    private int id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name="section_id")
    private Section section;

    @Builder
    public League(int id, String name, Section section){
        this.id = id;
        this.name = name;
        this.section = section;
    }
}

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
public class Section {
    @Id
    @Column(name="section_id")
    private int id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name="sports_id")
    private Sports sports;

    @Builder
    public Section(int id, String name, Sports sports){
        this.id = id;
        this.name = name;
        this.sports = sports;
    }
}

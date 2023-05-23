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
public class Tournament {
    @Id
    @Column
    private Long id;
    @Column
    private Long unique_id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn
    private Category category;
    @Builder
    public Tournament(Long id, Long unique_id, String name, Category category){
        this.id = id;
        this.unique_id = unique_id;
        this.name = name;
        this.category = category;
    }

}

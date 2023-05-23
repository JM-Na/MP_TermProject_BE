package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
public class Category {
    @Id
    @Column
    private Long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn
    private Sports sports;

    @Builder
    public Category(Long id, String name, Sports sports){
        this.id = id;
        this.name = name;
        this.sports = sports;
    }
}

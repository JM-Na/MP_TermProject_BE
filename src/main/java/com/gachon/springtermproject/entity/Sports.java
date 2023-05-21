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
public class Sports {
    @Id
    @Column(name = "sports_id")
    private int id;
    @Column
    private String name;

    @Builder
    public Sports (int id, String name){
        this.id = id;
        this.name = name;
    }
}

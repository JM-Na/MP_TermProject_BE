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
    @Column
    private Long id;
    @Column
    private String name;

    @Builder
    public Sports (Long id, String name){
        this.id = id;
        this.name = name;
    }
}

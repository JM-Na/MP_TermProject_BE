package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Player {
    @Id
    @Column(name="player_id")
    private Long id;
    @Column
    private String name;
    @Column
    private int age;
    @Column
    private Timestamp date_of_birth;
    @Column
    private Long height;
    @Column
    private Long shirt_number;
    @Column
    private String nation;
    @Column
    private String position;
    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Builder
    public Player(Long id, String name, Timestamp date_of_birth, Long height, Long shirt_number,
                  String nation, String position, Team team){
        this.id = id;
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.height = height;
        this.shirt_number = shirt_number;
        this.nation = nation;
        this.position = position;
        this.team = team;
        this.age = calculateAge(date_of_birth);
    }
    public int calculateAge(Timestamp timestamp){
        Date birthDate = new java.util.Date(timestamp.getTime());
        Date currentDate = new java.util.Date();
        java.util.Calendar birthCalendar = java.util.Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        java.util.Calendar currentCalendar = java.util.Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        int age = currentCalendar.get(java.util.Calendar.YEAR) - birthCalendar.get(java.util.Calendar.YEAR);

        // 생일이 지났는지 체크
        if (currentCalendar.get(java.util.Calendar.MONTH) < birthCalendar.get(java.util.Calendar.MONTH)) {
            age--;
        } else if (currentCalendar.get(java.util.Calendar.MONTH) == birthCalendar.get(java.util.Calendar.MONTH)
                && currentCalendar.get(java.util.Calendar.DAY_OF_MONTH) < birthCalendar.get(java.util.Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }
    public boolean containsNull(){
        return id == null || name == null || date_of_birth == null || height == null || shirt_number == null || nation == null || position == null;
    }
}

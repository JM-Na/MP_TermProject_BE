package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
public class Event {
    @Id
    private int id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn
    private Tournament tournament;
    @Column
    private String status;
    @Column
    private LocalDateTime date_time;

    @OneToOne(mappedBy = "event")
    private EventTeam eventTeam;
    @Builder
    public Event(int id, String name, Tournament tournament, String status,
                 LocalDateTime date_time, EventTeam eventTeam){
        this.id = id;
        this.name = name;
        this.tournament = tournament;
        this.status = status;
        this.date_time = date_time;
        this.eventTeam = eventTeam;
    }
}

package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Event {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn
    private Tournament tournament;
    @Column
    private String status;
    @Column(name="round_info")
    private Long roundInfo;
    @Column(name = "date_time")
    private Timestamp dateTime;

    @OneToOne(mappedBy = "event")
    private EventTeam eventTeam;
    @Builder
    public Event(Long id, Tournament tournament, String status, Long roundInfo,
                 Timestamp dateTime){
        this.id = id;
        this.tournament = tournament;
        this.status = status;
        this.roundInfo = roundInfo;
        this.dateTime = dateTime;
    }
}

package com.gachon.springtermproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column
    private String round_info;
    @Column
    private Timestamp date_time;

    @OneToOne(mappedBy = "event")
    private EventTeam eventTeam;
    @Builder
    public Event(Long id, Tournament tournament, String status, String round_info,
                 Timestamp date_time){
        this.id = id;
        this.tournament = tournament;
        this.status = status;
        this.round_info = round_info;
        this.date_time = date_time;
    }
}

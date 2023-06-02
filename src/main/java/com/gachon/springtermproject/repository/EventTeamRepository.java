package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.Event;
import com.gachon.springtermproject.entity.EventTeam;
import com.gachon.springtermproject.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTeamRepository extends JpaRepository<EventTeam, Long>{
    boolean existsByEvent(Event event);
    EventTeam findByEvent(Event event);
}

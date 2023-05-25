package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.EventTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTeamRepository extends JpaRepository<EventTeam, Long>{
    boolean existsByEvent(EventTeam eventTeam);
}

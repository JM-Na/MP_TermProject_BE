package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
    Team findByName(String team);
}

package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.Season;
import com.gachon.springtermproject.entity.SeasonTeam;
import com.gachon.springtermproject.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonTeamRepository extends JpaRepository<SeasonTeam, Long>{
    boolean existsBySeasonAndTeam(Season season, Team team);
}

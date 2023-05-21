package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long>{

}

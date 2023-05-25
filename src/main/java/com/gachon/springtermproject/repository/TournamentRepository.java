package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.Tournament;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long>{
    Tournament findByName(String name);
}

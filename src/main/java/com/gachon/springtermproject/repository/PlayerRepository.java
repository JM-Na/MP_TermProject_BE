package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{
    boolean existsByName(String name);
}

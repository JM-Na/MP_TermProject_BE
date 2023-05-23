package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long>{

}

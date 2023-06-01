package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.Sports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SportsRepository extends JpaRepository<Sports, Long>{
}

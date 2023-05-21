package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long>{

}

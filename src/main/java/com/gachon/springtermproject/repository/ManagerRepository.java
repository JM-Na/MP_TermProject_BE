package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>{

}

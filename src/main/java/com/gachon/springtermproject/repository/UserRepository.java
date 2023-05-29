package com.gachon.springtermproject.repository;

import com.gachon.springtermproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.StyledEditorKit;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Boolean existsByEmail(String email);

    User findByEmail(String email);
}

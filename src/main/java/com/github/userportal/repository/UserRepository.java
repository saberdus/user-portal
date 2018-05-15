package com.github.userportal.repository;

import com.github.userportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Specifies methods used to store USER related information which are stored in the
 * database table .
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findOne(int userId);
    User findByEMail(String eMail);
    User findByUsername(String username);
    List<User> findAll();
}


/*
 * SCRIPT: RoleRepository.java
 * AUTHOR: Wu (https://github.com/stupid-2020)
 * DATE  : 17-JAN-2022
 * NOTE  : 
 */
package com.example.demo.repositories;

import java.util.Optional;

import com.example.demo.models.UserRole;
import com.example.demo.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}

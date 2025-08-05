package com.PulsePoint.PulsePoint.repository;
import com.PulsePoint.PulsePoint.model.Users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, UUID> {
    Users findByUserName(String username);
}

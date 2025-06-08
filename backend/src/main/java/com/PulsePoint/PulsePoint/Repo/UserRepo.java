package com.PulsePoint.PulsePoint.Repo;
import com.PulsePoint.PulsePoint.Models.Users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, UUID> {
    Users findByUsername(String username);
}

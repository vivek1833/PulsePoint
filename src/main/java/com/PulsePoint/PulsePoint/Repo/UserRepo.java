package com.PulsePoint.PulsePoint.Repo;
import com.PulsePoint.PulsePoint.Models.Users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, UUID> {

    List<?> findAllByType(String string);
    
}

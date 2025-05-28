package com.PulsePoint.PulsePoint.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PulsePoint.PulsePoint.Models.Users;

@Repository
public interface UserDao extends JpaRepository<Users, UUID> {

	Users findByUsername(String username);
}
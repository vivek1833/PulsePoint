package com.PulsePoint.PulsePoint.Repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.PulsePoint.PulsePoint.Models.Patient;
import com.PulsePoint.PulsePoint.Models.Users;

import java.util.UUID;

@Repository
public interface PatientRepo extends JpaRepository<Patient, UUID> {

    @Query(value = "SELECT u.* FROM patient p LEFT JOIN users u ON p.user_id = u.id", nativeQuery = true)
    List<Users> fetchUsersFromPatients();
}

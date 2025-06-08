package com.PulsePoint.PulsePoint.Repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.PulsePoint.PulsePoint.DTO.PatientDTO;
import com.PulsePoint.PulsePoint.Models.Patient;

import java.util.UUID;

@Repository
public interface PatientRepo extends JpaRepository<Patient, UUID> {
    @Query(
        value = "SELECT u.id, u.first_name, u.last_name, u.email, u.username, u.type, p.id as patient_id, p.user_id, p.age, p.gender, p.admission_date, p.condition, p.room_no, p.contact_no, p.diagnosis, p.active, u.created_at, u.updated_at FROM users u INNER JOIN patient p ON p.user_id = u.id WHERE u.active = true and p.active = true",
        countQuery = "SELECT count(*) FROM users u INNER JOIN patient p ON p.user_id = u.id WHERE u.active = true and p.active = true", 
        nativeQuery = true)
    List<PatientDTO> fetchUsersFromPatients(Pageable page);
}

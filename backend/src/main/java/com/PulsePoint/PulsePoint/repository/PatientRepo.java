package com.PulsePoint.PulsePoint.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.PulsePoint.PulsePoint.dto.PatientDTO;
import com.PulsePoint.PulsePoint.model.Patient;

import java.util.UUID;

@Repository
public interface PatientRepo extends JpaRepository<Patient, UUID> {
    @Query(value = """
            SELECT
                p.id AS patient_id,
                p.user_id,
                u.first_name,
                u.last_name,
                u.email,
                u.user_name,
                u.type,
                p.age,
                p.gender,
                p.admission_date,
                p.condition,
                p.room_no,
                p.contact_no,
                p.diagnosis,
                p.active,
                u.created_at,
                u.updated_at
            FROM users u
            INNER JOIN patient p ON p.user_id = u.id
            WHERE u.active = true AND p.active = true
            """, nativeQuery = true)
    List<PatientDTO> fetchUsersFromPatients(Pageable page);

}

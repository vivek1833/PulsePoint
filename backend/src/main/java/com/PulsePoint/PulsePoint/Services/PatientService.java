package com.PulsePoint.PulsePoint.Services;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.PulsePoint.PulsePoint.DTO.PatientDTO;
import com.PulsePoint.PulsePoint.Models.Patient;
import com.PulsePoint.PulsePoint.Models.Users;
import com.PulsePoint.PulsePoint.Repo.PatientRepo;
import com.PulsePoint.PulsePoint.Repo.UserRepo;

@Service
public class PatientService {

    @Autowired
    PatientRepo patientRepo;

    PatientDTO patientDTO;

    @Autowired
    UserRepo userRepo;

    // Get all patients
    public List<PatientDTO> getPatients(Integer pageSize, Integer pageNumber, String sortColumn, String sortDirection) {

        Map<String, String> sortMap = Map.of(
            "firstName", "first_name",
            "lastName", "last_name",
            "age", "p.age",
            "roomNo", "p.room_no",
            "condition", "p.condition",
            "admissionDate", "p.admission_date",
            "createdAt", "created_at"
        );

        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), sortMap.get(sortColumn));
        List<PatientDTO> patients = patientRepo.fetchUsersFromPatients(page);
        return patients;
    }

    // Get Patient by id
    public PatientDTO getPatientById(UUID id) {
        Pageable page = PageRequest.of(0, 1);
        PatientDTO patient = patientRepo.fetchUsersFromPatients(page).stream().filter(p -> p.getPatientId().equals(id)).findFirst().get();
        return patient;
    }

    // Create Patient
    public Users createPatient(PatientDTO patientDTO) {
        // Create user
        Users user = new Users();
        user.setFirstName(patientDTO.getFirstName());
        user.setLastName(patientDTO.getLastName());
        user.setEmail(patientDTO.getEmail());
        user.setUsername(patientDTO.getUsername());
        user.setActive(true);
        user.setType("PATIENT");
        user.setCreatedAt(new Date(System.currentTimeMillis()));

        Users savedUser = userRepo.save(user);

        // Create patient with additional fields
        Patient patient = new Patient();
        patient.setUserId(savedUser.getId());
        patient.setAge(patientDTO.getAge());
        patient.setGender(patientDTO.getGender());
        patient.setAdmissionDate(patientDTO.getAdmissionDate());
        patient.setCondition(patientDTO.getCondition());
        patient.setRoomNo(patientDTO.getRoomNo());
        patient.setContactNo(patientDTO.getContactNo());
        patient.setDiagnosis(patientDTO.getDiagnosis());
        patient.setActive(true);
        patient.setCreatedAt(new Date(System.currentTimeMillis()));

        patientRepo.save(patient);
        return savedUser;
    }

    // Update Patient
    public Patient updatePatient(UUID id, PatientDTO patientDTO) {
        Patient patient = patientRepo.findById(id).get();
        patient.setAge(patient.getAge());
        patient.setGender(patientDTO.getGender());
        patient.setAdmissionDate(patientDTO.getAdmissionDate());
        patient.setCondition(patientDTO.getCondition());
        patient.setRoomNo(patientDTO.getRoomNo());
        patient.setContactNo(patientDTO.getContactNo());
        patient.setDiagnosis(patientDTO.getDiagnosis());
        patientRepo.save(patient);

        Users user = userRepo.findById(patient.getUserId()).get();
        user.setFirstName(patientDTO.getFirstName());
        user.setLastName(patientDTO.getLastName());
        user.setEmail(patientDTO.getEmail());
        user.setUsername(patientDTO.getUsername());
        userRepo.save(user);

        return patient;
    }

    // Delete Patient
    public void deletePatient(UUID id) {
        Patient patient = patientRepo.findById(id).get();
        patient.setActive(false);
        patientRepo.save(patient);

        Users user = userRepo.findById(patient.getUserId()).get();
        user.setActive(false);
        userRepo.save(user);
    }
}

package com.PulsePoint.PulsePoint.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.PulsePoint.PulsePoint.dto.PatientDTO;
import com.PulsePoint.PulsePoint.model.Patient;
import com.PulsePoint.PulsePoint.model.Users;
import com.PulsePoint.PulsePoint.repository.PatientRepo;
import com.PulsePoint.PulsePoint.repository.UserRepo;
import com.PulsePoint.PulsePoint.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final UserRepo userRepo;

    public PatientServiceImpl(PatientRepo patientRepo, UserRepo userRepo) {
        this.patientRepo = patientRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<PatientDTO> getPatients(Integer pageSize, Integer pageNumber, String sortColumn, String sortDirection) {
        Map<String, String> sortMap = Map.of(
                "firstName", "first_name",
                "lastName", "last_name",
                "age", "p.age",
                "roomNo", "p.room_no",
                "condition", "p.condition",
                "admissionDate", "p.admission_date",
                "createdAt", "created_at");
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), sortMap.get(sortColumn));
        List<PatientDTO> patients = patientRepo.fetchUsersFromPatients(page);
        
        return patients;
    }

    @Override
    public PatientDTO getPatientById(UUID id) {
        Pageable page = PageRequest.of(0, 1);
        PatientDTO patient = patientRepo.fetchUsersFromPatients(page).stream().filter(p -> p.getPatientId().equals(id)).findFirst().get();
        
        return patient;
    }

    @Override
    public Users createPatient(PatientDTO patientDTO) {
        Users user = new Users();
        user.setFirstName(patientDTO.getFirstName());
        user.setLastName(patientDTO.getLastName());
        user.setEmail(patientDTO.getEmail());
        user.setUserName(patientDTO.getUserName());
        user.setActive(true);
        user.setType("PATIENT");
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        Users savedUser = userRepo.save(user);
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

    @Override
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
        user.setUserName(patientDTO.getUserName());
        userRepo.save(user);
        return patient;
    }

    @Override
    public void deletePatient(UUID id) {
        Patient patient = patientRepo.findById(id).get();
        patient.setActive(false);
        patientRepo.save(patient);
        Users user = userRepo.findById(patient.getUserId()).get();
        user.setActive(false);
        userRepo.save(user);
    }
}

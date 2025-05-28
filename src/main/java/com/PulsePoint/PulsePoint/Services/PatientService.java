package com.PulsePoint.PulsePoint.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.PulsePoint.PulsePoint.Models.Patient;
import com.PulsePoint.PulsePoint.Models.Users;
import com.PulsePoint.PulsePoint.Repo.PatientRepo;
import com.PulsePoint.PulsePoint.Repo.UserRepo;

@Service
public class PatientService {

    private PatientRepo patientRepo;
    private UserRepo userRepo;

    public PatientService(PatientRepo patientRepo, UserRepo userRepo) {
        this.patientRepo = patientRepo;
        this.userRepo = userRepo;
    }
    
    public List<Users> getPatients() {
        List<Users> patients = patientRepo.fetchUsersFromPatients();
        return patients;
    }

    public Users getPatientById(UUID id) {
        Patient patient = patientRepo.findById(id).get();
        Users user_patient = userRepo.findById(patient.getUserId()).get();
        return user_patient;
    }

    public Users createPatient(Users user) {
        // Create user
        Users u = userRepo.save(user);
        u.setType("PATIENT");

        // Add a reference on Patient table on Patient.user_id = User.id
        Patient patient = new Patient();
        patient.setUserId(u.getId());
        patientRepo.save(patient);
        return u;
    }
}

package com.PulsePoint.PulsePoint.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PulsePoint.PulsePoint.Models.Users;
import com.PulsePoint.PulsePoint.Services.PatientService;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /*
     * Get all patients
     */
    @GetMapping("/all")
    public ResponseEntity<?> getPatients() {
        try {
            List<Users> patients = patientService.getPatients();
            return ResponseEntity.status(HttpStatus.OK).body(patients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
        }
    }

    /*
     * Get Patient by id
     */
    @GetMapping("")
    public ResponseEntity<?> getPatientById(@RequestParam UUID id) {
        try {
            Users user_patient = patientService.getPatientById(id);
            return ResponseEntity.status(HttpStatus.OK).body(user_patient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
        }
    }

    /*
     * Create Patient
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPatient(@RequestBody Users user) {
        try {
            Users newPatient = patientService.createPatient(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

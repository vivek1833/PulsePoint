package com.PulsePoint.PulsePoint.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PatientService patientService;

    /*
     * Get all patients
     */
    @GetMapping("/all")
    public ResponseEntity<?> getPatients() {
        List<Users> patients = patientService.getPatients();
        return ResponseEntity.status(HttpStatus.OK).body(patients);
    }

    /*
     * Get Patient by id
     */
    @GetMapping("")
    public ResponseEntity<?> getPatientById(@RequestParam UUID id) {
        Users user_patient = patientService.getPatientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user_patient);
    }

    /*
     * Create Patient
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPatient(@RequestBody Users user) {
        Users newPatient = patientService.createPatient(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
    }
}

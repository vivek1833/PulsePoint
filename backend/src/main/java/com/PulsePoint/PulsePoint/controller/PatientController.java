package com.PulsePoint.PulsePoint.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.PulsePoint.PulsePoint.dto.PatientDTO;
import com.PulsePoint.PulsePoint.model.Patient;
import com.PulsePoint.PulsePoint.model.Users;
import com.PulsePoint.PulsePoint.service.PatientService;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PreAuthorize("hasAnyRole('STAFF', 'PATIENT')")
    @GetMapping("")
    public ResponseEntity<List<PatientDTO>> getPatients(
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "createdAt") String sortColumn,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection) {
        List<PatientDTO> patients = patientService.getPatients(pageSize, pageNumber, sortColumn, sortDirection);
        return ResponseEntity.status(HttpStatus.OK).body(patients);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'PATIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable UUID id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(patient);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'PATIENT')")
    @PostMapping("")
    public ResponseEntity<Users> createPatient(@RequestBody PatientDTO patientDTO) {
        Users newUser = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'PATIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable UUID id, @RequestBody PatientDTO patientDTO) {
        Patient updatedPatient = patientService.updatePatient(id, patientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPatient);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'PATIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

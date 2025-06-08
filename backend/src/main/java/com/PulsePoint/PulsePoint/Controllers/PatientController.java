package com.PulsePoint.PulsePoint.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.PulsePoint.PulsePoint.DTO.PatientDTO;
import com.PulsePoint.PulsePoint.Models.Patient;
import com.PulsePoint.PulsePoint.Models.Users;
import com.PulsePoint.PulsePoint.Services.PatientService;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    /*
     * Get all patients
     * URL: /api/patient?pageSize={pageSize}&pageNumber={pageNumber}
     * Method: GET
     */
    @GetMapping("")
    public ResponseEntity<List<PatientDTO>> getPatients(
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "createdAt") String sortColumn,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection) {
        List<PatientDTO> patients = patientService.getPatients(pageSize, pageNumber, sortColumn, sortDirection);
        return ResponseEntity.status(HttpStatus.OK).body(patients);
    }

    /*
     * Get Patient by id
     * URL: /api/patient/{id}
     * Method: GET
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable UUID id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(patient);
    }

    /*
     * Create Patient
     * URL: /api/patient
     * Method: POST
     */
    @PostMapping("")
    public ResponseEntity<Users> createPatient(@RequestBody PatientDTO patientDTO) {
        Users newUser = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    /*
     * Update Patient
     * URL: /api/patient/{id}
     * Method: PUT
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable UUID id, @RequestBody PatientDTO patientDTO) {
        Patient updatedPatient = patientService.updatePatient(id, patientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPatient);
    }

    /*
     * Delete Patient
     * URL: /api/patient/{id}
     * Method: DELETE
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

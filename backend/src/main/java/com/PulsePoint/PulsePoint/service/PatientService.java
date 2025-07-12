package com.PulsePoint.PulsePoint.service;

import com.PulsePoint.PulsePoint.dto.PatientDTO;
import com.PulsePoint.PulsePoint.model.Patient;
import com.PulsePoint.PulsePoint.model.Users;
import java.util.List;
import java.util.UUID;

public interface PatientService {
    List<PatientDTO> getPatients(Integer pageSize, Integer pageNumber, String sortColumn, String sortDirection);

    PatientDTO getPatientById(UUID id);

    Users createPatient(PatientDTO patientDTO);

    Patient updatePatient(UUID id, PatientDTO patientDTO);

    void deletePatient(UUID id);
}
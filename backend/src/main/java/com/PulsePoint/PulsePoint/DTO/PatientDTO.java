package com.PulsePoint.PulsePoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private UUID patientId;
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String type;
    private Integer age;
    private String gender;
    private Date admissionDate;
    private String condition;
    private String roomNo;
    private String contactNo;
    private String diagnosis;
    private Boolean active;
    private Date createdAt;
    private Date updatedAt;
} 
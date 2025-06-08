package com.PulsePoint.PulsePoint.DTO;

import java.sql.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class PatientDTO {
    // User fields
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String type;
    private Boolean active;
    private Date createdAt;
    private Date updatedAt;

    // Patient fields
    private UUID patientId;
    private UUID userId;
    private int age;
    private String gender;
    private Date admissionDate;
    private String condition;
    private String roomNo;
    private String contactNo;
    private String diagnosis;

    public PatientDTO(UUID id, String firstName, String lastName, String email, String username,
            String type, UUID patientId, UUID userId, int age, String gender, Date admissionDate,
            String condition, String roomNo, String contactNo, String diagnosis, Boolean active, Date createdAt, Date updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.type = type;
        this.patientId = patientId;
        this.userId = userId;
        this.age = age;
        this.gender = gender;
        this.admissionDate = admissionDate;
        this.condition = condition;
        this.roomNo = roomNo;
        this.contactNo = contactNo;
        this.diagnosis = diagnosis;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
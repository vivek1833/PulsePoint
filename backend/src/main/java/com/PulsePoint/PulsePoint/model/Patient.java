package com.PulsePoint.PulsePoint.model;

import java.sql.Date;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    // Reference to User user.id
    private UUID userId;
    private int age;
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

package com.PulsePoint.PulsePoint.Models;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Patient {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    // Reference to User user.id
    private UUID userId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}

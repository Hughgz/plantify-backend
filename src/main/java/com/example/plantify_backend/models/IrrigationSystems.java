package com.example.plantify_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class IrrigationSystems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Status status = Status.Off;
    private LocalDateTime lastIrrigation;
    private Mode mode = Mode.Manual;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensors sensor;
    public enum Status {
        On, Off
    }
    public enum Mode {
        Manual, Auto
    }
}

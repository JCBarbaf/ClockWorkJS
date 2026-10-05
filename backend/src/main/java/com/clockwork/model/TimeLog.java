package com.clockwork.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_logs")
public class TimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeLogType type;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public TimeLog() {
}

public TimeLog(Worker worker, TimeLogType type, LocalDateTime datetime) {
    this.worker = worker;
    this.type = type;
    this.datetime = datetime;
}

public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public Worker getWorker() {
    return worker;
}

public void setWorker(Worker worker) {
    this.worker = worker;
}

public TimeLogType getType() {
    return type;
}

public void setType(TimeLogType type) {
    this.type = type;
}

public LocalDateTime getDatetime() {
    return datetime;
}

public void setDatetime(LocalDateTime datetime) {
    this.datetime = datetime;
}

public LocalDateTime getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}

public LocalDateTime getUpdatedAt() {
    return updatedAt;
}

public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
}

}
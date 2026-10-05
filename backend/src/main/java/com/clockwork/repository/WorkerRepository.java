package com.clockwork.repository;

import com.clockwork.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    Optional<Worker> findByEmployeeCode(String employeeCode);
}

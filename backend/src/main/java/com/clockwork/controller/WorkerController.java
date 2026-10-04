package com.clockwork.controller;

import com.clockwork.model.Worker;
import com.clockwork.repository.WorkerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController 
@RequestMapping("api/workers")
public class WorkerController {
    private final WorkerRepository workerRepository;
    public WorkerController(WorkerRepository workerRepository){
        this.workerRepository = workerRepository;
    }

    @GetMapping("/{employeeCode}")
    public Optional<Worker> getWorker(@PathVariable String employeeCode){
        return workerRepository.findByEmployeeCode(employeeCode);
    }

}

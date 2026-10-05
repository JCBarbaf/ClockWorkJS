package com.clockwork.controller;

import com.clockwork.model.TimeLog;
import com.clockwork.model.TimeLogType;
import com.clockwork.model.Worker;
import com.clockwork.repository.WorkerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.clockwork.repository.TimeLogRepository;

import java.util.List;

@RestController
@RequestMapping("api/workers")
@CrossOrigin
public class WorkerController {
    private final WorkerRepository workerRepository;
    private final TimeLogRepository timeLogRepository;

    public WorkerController(WorkerRepository workerRepository,  TimeLogRepository timeLogRepository) {
        this.workerRepository = workerRepository;
        this.timeLogRepository = timeLogRepository;
    }

    @GetMapping("/{employeeCode}")
    public WorkerResponse getWorker(@PathVariable String employeeCode) {

    Worker worker = workerRepository.findByEmployeeCode(employeeCode)
              .orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Worker not found"
                )
        );

    List<TimeLog> logs = timeLogRepository.findAllByOrderByDatetimeDesc();

    TimeLog lastLog = logs.stream()
            .filter(log -> log.getWorker().getId().equals(worker.getId()))
            .findFirst()
            .orElse(null);

    String nextAction;

    if (lastLog == null || lastLog.getType() == TimeLogType.ClockOut) {
        nextAction = "ClockIn";
    } else {
        nextAction = "ClockOut";
    }

    return new WorkerResponse(worker, nextAction, lastLog);
}

    @GetMapping
    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }
    public record WorkerResponse(
        Worker worker,
        String nextAction,
        TimeLog lastLog
) {
}

}

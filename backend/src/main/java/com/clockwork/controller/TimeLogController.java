package com.clockwork.controller;

import com.clockwork.model.TimeLog;
import com.clockwork.model.TimeLogType;
import com.clockwork.repository.TimeLogRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.clockwork.model.Worker;
import com.clockwork.repository.WorkerRepository;
import java.time.LocalDateTime;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/time-logs")
@CrossOrigin
public class TimeLogController {

    private final TimeLogRepository timeLogRepository;
    private final WorkerRepository workerRepository;

    public TimeLogController(
        TimeLogRepository timeLogRepository,
        WorkerRepository workerRepository) {

    this.timeLogRepository = timeLogRepository;
    this.workerRepository = workerRepository;
}

    @GetMapping
    public List<TimeLog> getTimeLogs() {
        return timeLogRepository.findAllByOrderByDatetimeDesc();
    }

    @PostMapping
    public TimeLog createTimeLog(@RequestBody Map<String, Object> request) {
        Integer workerId = (Integer) request.get("workerId");
        Worker worker = workerRepository.findById(workerId)
         .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Worker not found"
        ));
        TimeLogType type = TimeLogType.valueOf((String) request.get("type"));
        TimeLog timeLog = new TimeLog(
        worker,
        type,
        LocalDateTime.now()
);
return timeLogRepository.save(timeLog);
}
}

package com.clockwork.repository;
import com.clockwork.model.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimeLogRepository extends JpaRepository<TimeLog, Integer> {
     List<TimeLog> findAllByOrderByDatetimeDesc();
    
}

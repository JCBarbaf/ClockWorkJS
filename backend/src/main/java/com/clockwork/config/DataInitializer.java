package com.clockwork.config;

import com.clockwork.model.Worker;
import com.clockwork.repository.WorkerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final WorkerRepository workerRepository;

    public DataInitializer(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public void run(String... args) {

        if (workerRepository.count() == 0) {

            Worker worker1 = new Worker(
                    "AAA676769",
                    "Juan Carlos",
                    "Barba Fernández",
                    "jbarbafernandez@cifpfbmoll.eu"
            );

            Worker worker2 = new Worker(
                    "BBB424242",
                    "Tamara",
                    "Fernandez Viturro",
                    "tfernandezviturro@cifpfbmoll.eu"
            );

            Worker worker3 = new Worker(
                    "CCC111112",
                    "Xavier",
                    "Sastre Flexas",
                    "xsastref@cifpfbmoll.eu"
            );

            workerRepository.save(worker1);
            workerRepository.save(worker2);
            workerRepository.save(worker3);
        }
    }
}
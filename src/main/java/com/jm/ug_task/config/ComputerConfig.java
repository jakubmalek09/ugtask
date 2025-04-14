package com.jm.ug_task.config;

import com.jm.ug_task.model.Computer;
import com.jm.ug_task.model.ComputerEntity;
import com.jm.ug_task.service.ComputerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ComputerConfig {
    @Bean
    public Computer getAcerAspire() {
        return new Computer(
                "ACER Aspire",
                LocalDate.of(2025, 1, 3),
                345D,
                null
        );
    }

    @Bean
    public Computer getDellLatitude() {
        return new Computer(
                "DELL Latitude",
                LocalDate.of(2025, 1, 10),
                543D,
                null
        );
    }

    @Bean
    public Computer getHPVictus() {
        return new Computer(
                "HP Victus",
                LocalDate.of(2025, 1, 19),
                346D,
                null
        );
    }

    @Bean
    public CommandLineRunner calculateUSDAndSaveToXML(List<Computer> computers,
                                                      ComputerService service) {
        return args -> {
            service.mapCostToUSD(computers);
            List<ComputerEntity> computersSaved = service.saveComputers(computers);
            service.saveComputersXML(computersSaved);
        };
    }
}

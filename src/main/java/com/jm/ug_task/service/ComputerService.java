package com.jm.ug_task.service;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jm.ug_task.model.Bill;
import com.jm.ug_task.model.Computer;
import com.jm.ug_task.model.ComputerEntity;
import com.jm.ug_task.model.NPBResponseDTO;
import com.jm.ug_task.repository.ComputerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComputerService {
    private final ComputerRepository repository;
    private final RestTemplate restTemplate;

    public ComputerService(ComputerRepository repository,
                           RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Page<ComputerEntity> searchComputersByNameFragment(String nameFragment,
                                                              Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(nameFragment, pageable);
    }

    public Page<ComputerEntity> searchComputersByPostingDate(LocalDate date,
                                                             Pageable pageable) {
        return repository.findByPostingDate(date, pageable);
    }

    public List<ComputerEntity> saveComputers(List<? extends Computer> computers) {
        return repository.saveAll(
                computers.stream()
                        .map(ComputerEntity::new)
                        .collect(Collectors.toList())
        );
    }

    public List<ComputerEntity> getAll() {
        return repository.findAll();
    }

    public void saveComputersXML(List<ComputerEntity> computers) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.writeValue(
                new File("computers.xml"),
                new Bill(computers.stream().map(ComputerEntity::toComputer).collect(Collectors.toList()))
        );
    }

    public void mapCostToUSD(List<Computer> computers) {
        for (Computer computer : computers)
            if (computer.getCostUSD() == null)
                throw new IllegalArgumentException("Unable to convert cost of %s to PLN with null USD cost".formatted(computer));

        HashMap<LocalDate, List<Computer>> computersByDate = new HashMap<>();

        // Eliminating redundant posting dates to avoid unnecessary requests
        for (Computer computer : computers) {
            LocalDate date = computer.getPostingDate();
            if (computersByDate.containsKey(date)) {
                computersByDate.get(date).add(computer);
            } else {
                LinkedList<Computer> list = new LinkedList<>();
                list.add(computer);
                computersByDate.put(date, list);
            }
        }

        for (LocalDate date : computersByDate.keySet()) {
            double multiplier = getMultiplierForDate(date);
            List<Computer> computersForDate = computersByDate.get(date);
            for (Computer computer : computersForDate)
                computer.setCostPLN(computer.getCostUSD() * multiplier);
        }
    }

    public double getMultiplierForDate(LocalDate date) {
        LocalDate minus7DaysDate = date.minusDays(7);
        String url = String.format("https://api.nbp.pl/api/exchangerates/rates/A/USD/%s/%s/?format=json", minus7DaysDate, date);
        ResponseEntity<NPBResponseDTO> response = restTemplate.getForEntity(url, NPBResponseDTO.class);
        return response.getBody().getRates().get(0).getMid();
    }
}

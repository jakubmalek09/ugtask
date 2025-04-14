package com.jm.ug_task.controller;

import com.jm.ug_task.model.ComputerEntity;
import com.jm.ug_task.service.ComputerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/computer")
public class ComputerController {
    private final ComputerService service;

    public ComputerController(ComputerService service) {
        this.service = service;
    }

    @GetMapping("/byName")
    public Page<ComputerEntity> searchComputersByNameFragment(
            @RequestParam String nameFragment,
            Pageable pageable
    ) {
        return service.searchComputersByNameFragment(nameFragment, pageable);
    }

    @GetMapping("/byDate")
    public Page<ComputerEntity> searchComputersByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Pageable pageable
    ) {
        return service.searchComputersByPostingDate(date, pageable);
    }

    @GetMapping
    public List<ComputerEntity> getAll() {
        return service.getAll();
    }
}

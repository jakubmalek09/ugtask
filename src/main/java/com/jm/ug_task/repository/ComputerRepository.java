package com.jm.ug_task.repository;

import com.jm.ug_task.model.ComputerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ComputerRepository extends JpaRepository<ComputerEntity, Long> {
    Page<ComputerEntity> findByNameContainingIgnoreCase(String fragment, Pageable pageable);
    Page<ComputerEntity> findByPostingDate(LocalDate postingDate, Pageable pageable);
}

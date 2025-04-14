package com.jm.ug_task.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "computer")
public class ComputerEntity extends Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public ComputerEntity() {

    }

    public ComputerEntity(Computer c) {
        this(c.getName(), c.getPostingDate(), c.getCostUSD(), c.getCostPLN());
    }

    public ComputerEntity(String name,
                          LocalDate postingDate,
                          Double costUSD,
                          Double costPLN) {
        super(name, postingDate, costUSD, costPLN);
    }

    public ComputerEntity(String name,
                          LocalDate postingDate,
                          Double costUSD,
                          Double costPLN,
                          Long id) {
        super(name, postingDate, costUSD, costPLN);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Computer toComputer() {
        return new Computer(
                getName(),
                getPostingDate(),
                getCostUSD(),
                getCostPLN()
        );
    }

    @Override
    public String toString() {
        return "ComputerEntity{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                ", postingDate=" + getPostingDate() +
                ", costUSD=" + getCostUSD() +
                ", costPLN=" + getCostPLN() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComputerEntity that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}

package com.jm.ug_task.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
@JacksonXmlRootElement(localName = "komputer")
public class Computer {
    @JacksonXmlProperty(localName = "nazwa")
    @Column(name = "nazwa")
    private String name;

    @JacksonXmlProperty(localName = "data_ksiegowania")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "data_ksiegowania")
    private LocalDate postingDate;

    @JacksonXmlProperty(localName = "koszt_USD")
    @Column(name = "koszt_USD")
    private Double costUSD;

    @JacksonXmlProperty(localName = "koszt_PLN")
    @Column(name = "koszt_PLN")
    private Double costPLN;

    public Computer() {

    }

    public Computer(String name,
                    LocalDate postingDate,
                    Double costUSD,
                    Double costPLN) {
        this.name = name;
        this.postingDate = postingDate;
        this.costUSD = costUSD;
        this.costPLN = costPLN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(LocalDate postingDate) {
        this.postingDate = postingDate;
    }

    public Double getCostUSD() {
        return costUSD;
    }

    public void setCostUSD(Double costUSD) {
        this.costUSD = costUSD;
    }

    public Double getCostPLN() {
        return costPLN;
    }

    public void setCostPLN(Double costPLN) {
        this.costPLN = costPLN;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "name='" + name + '\'' +
                ", postingDate=" + postingDate +
                ", costUSD=" + costUSD +
                ", costPLN=" + costPLN +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Computer computer)) return false;
        return Objects.equals(getName(), computer.getName()) && Objects.equals(getPostingDate(), computer.getPostingDate()) && Objects.equals(getCostUSD(), computer.getCostUSD()) && Objects.equals(getCostPLN(), computer.getCostPLN());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPostingDate(), getCostUSD(), getCostPLN());
    }
}

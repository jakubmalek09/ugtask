package com.jm.ug_task.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "faktura")
public class Bill {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "komputer")
    private List<Computer> computers;

    public Bill() {

    }

    public Bill(List<Computer> computers) {
        this.computers = computers;
    }
}

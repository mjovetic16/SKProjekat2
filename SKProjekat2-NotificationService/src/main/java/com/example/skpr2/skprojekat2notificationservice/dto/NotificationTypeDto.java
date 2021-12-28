package com.example.skpr2.skprojekat2notificationservice.dto;

import com.example.skpr2.skprojekat2notificationservice.domain.Parameter;

import javax.persistence.OneToMany;
import java.util.List;

public class NotificationTypeDto {

    private Long id;

    private String template;

    private List<ParameterDto> parameters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<ParameterDto> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDto> parameters) {
        this.parameters = parameters;
    }
}

package com.example.application.controller;

import com.example.application.model.Formulation;
import com.example.application.service.FormulationService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

@Endpoint
@AnonymousAllowed
public class FormulationEndpoint {
    @Autowired
    FormulationService formulationService;

    public Iterable<Formulation> getAllFormulations() {
        return formulationService.findAll();
    }

}

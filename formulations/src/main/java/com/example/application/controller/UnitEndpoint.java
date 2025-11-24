package com.example.application.controller;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import com.example.application.model.Unit;
import com.example.application.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;

@Endpoint
@AnonymousAllowed
public class UnitEndpoint {
    @Autowired
    UnitService unitService;

    public Iterable<Unit> getAllUnits() {
        return unitService.findAll();
    }

}

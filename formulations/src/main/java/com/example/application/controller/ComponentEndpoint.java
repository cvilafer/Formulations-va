package com.example.application.controller;

import com.example.application.model.Component;
import com.example.application.service.ComponentService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

@Endpoint
@AnonymousAllowed
public class ComponentEndpoint {
    @Autowired
    ComponentService componentService;

    public Iterable<Component> getAllComponents() {
        return componentService.findAll();
    }

}

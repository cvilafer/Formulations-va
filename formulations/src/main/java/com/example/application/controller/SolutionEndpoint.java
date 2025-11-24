package com.example.application.controller;

import com.example.application.model.Solution;
import com.example.application.service.SolutionService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

@Endpoint
@AnonymousAllowed
public class SolutionEndpoint {
    @Autowired
    SolutionService solutionService;

    public Iterable<Solution> getAllSolutions() {
        System.out.println(solutionService.findAll());

        return solutionService.findAll();
    }

}

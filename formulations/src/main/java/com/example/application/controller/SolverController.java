package com.example.application.controller;


import com.example.application.model.Formulation;
import com.example.application.repository.FormulationRepository;
import com.example.application.service.Solver;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

@Endpoint
@AnonymousAllowed
public class SolverController {

    @Autowired
    private Solver solver;

    @Autowired
    private FormulationRepository formulationRepository;


    public void solver() {
        try {
            Formulation formulation = formulationRepository.findById(1).getFirst();
            solver.resoldre_formulacio(formulation);
        } catch (Exception e) {
            //return "Error populating tables: " + e.getMessage();
            System.out.println("Error populating tables: " + e.getMessage());
        }
    }
}



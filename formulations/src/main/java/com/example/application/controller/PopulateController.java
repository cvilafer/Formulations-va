package com.example.application.controller;


import com.example.application.service.IngredientService;
import com.example.application.utils.PopulateTables;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

@Endpoint
@AnonymousAllowed
public class PopulateController {

    @Autowired
    private PopulateTables populateTables;


    public String populateAllTables() {
        try {
            return populateTables.PopulateAll();
        } catch (Exception e) {
            return "Error populating tables: " + e.getMessage();
        }
    }
}



package com.example.application.controller;

import com.example.application.model.Ingredient;
import com.example.application.service.IngredientService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

@Endpoint
@AnonymousAllowed
public class IngredientEndpoint {
    @Autowired
    IngredientService ingredientService;

    public Iterable<Ingredient> getAllIngredients() {
        System.out.println(ingredientService.findAll());

        return ingredientService.findAll();
    }

}

package com.example.application.service;

import com.example.application.model.Ingredient;
import com.example.application.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IngredientService {

    // let s implement all CRUD operations

    @Autowired
    IngredientRepository ingredientRepository;

    public Iterable<Ingredient> findAll() {

        Iterable<Ingredient> foundCard = new ArrayList<>();

        foundCard = ingredientRepository.findAll();

        return foundCard;
    }

}


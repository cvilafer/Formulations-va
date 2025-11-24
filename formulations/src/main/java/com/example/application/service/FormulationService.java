package com.example.application.service;

import com.example.application.model.Formulation;
import com.example.application.repository.FormulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FormulationService {

    // let s implement all CRUD operations

    @Autowired
    FormulationRepository formulationRepository;

    public Iterable<Formulation> findAll() {

        Iterable<Formulation> foundCard = new ArrayList<>();

        foundCard = formulationRepository.findAll();

        return foundCard;
    }

}


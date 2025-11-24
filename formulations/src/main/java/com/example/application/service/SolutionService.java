package com.example.application.service;

import com.example.application.model.Solution;
import com.example.application.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SolutionService {

    // let s implement all CRUD operations

    @Autowired
    SolutionRepository solutionRepository;

    public Iterable<Solution> findAll() {

        Iterable<Solution> foundCard = new ArrayList<>();

        foundCard = solutionRepository.findAll();

        return foundCard;
    }

}


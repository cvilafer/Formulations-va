package com.example.application.service;

import com.example.application.model.Constraint;
import com.example.application.repository.ConstraintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ConstraintService {

    // let s implement all CRUD operations

    @Autowired
    ConstraintRepository constraintRepository;

    public Iterable<Constraint> findAll() {

        Iterable<Constraint> foundCard = new ArrayList<>();

        foundCard = constraintRepository.findAll();

        return foundCard;
    }

}


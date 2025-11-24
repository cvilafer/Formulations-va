package com.example.application.service;

import com.example.application.model.Component;
import com.example.application.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ComponentService {

    // let s implement all CRUD operations

    @Autowired
    ComponentRepository componentRepository;

    public Iterable<Component> findAll() {

        Iterable<Component> foundCard = new ArrayList<>();

        foundCard = componentRepository.findAll();

        return foundCard;
    }

}


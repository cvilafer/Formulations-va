package com.example.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;





import com.example.application.model.Unit;
import com.example.application.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UnitService {

    // let s implement all CRUD operations

    @Autowired
    UnitRepository unitRepository;

    public Iterable<Unit> findAll() {

        Iterable<Unit> foundCard = new ArrayList<>();

        foundCard = unitRepository.findAll();

        return foundCard;
    }

}


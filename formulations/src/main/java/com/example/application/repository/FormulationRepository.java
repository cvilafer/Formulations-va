package com.example.application.repository;


import com.example.application.model.Formulation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FormulationRepository extends CrudRepository<Formulation, Integer> {

    List<Formulation> findByDescription(String description);

    List<Formulation> findById(int id);

}



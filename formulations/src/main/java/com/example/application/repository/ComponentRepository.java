package com.example.application.repository;


import com.example.application.model.Component;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ComponentRepository extends CrudRepository<Component, Integer> {

    List<Component> findByDescription(String description);

}

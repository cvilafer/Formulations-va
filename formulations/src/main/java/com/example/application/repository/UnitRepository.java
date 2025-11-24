package com.example.application.repository;


import com.example.application.model.Unit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UnitRepository extends CrudRepository<Unit, Integer> {

    List<Unit> findByDescription(String description);

}

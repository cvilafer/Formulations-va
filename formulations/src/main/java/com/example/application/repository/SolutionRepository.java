package com.example.application.repository;


import com.example.application.model.Solution;
import org.springframework.data.repository.CrudRepository;

public interface SolutionRepository extends CrudRepository<Solution, Integer> {
}

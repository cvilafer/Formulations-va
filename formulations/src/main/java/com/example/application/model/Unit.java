package com.example.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Unit {

    @Id
    private int id;
    private String description;

    public Unit() {


    }

    public Unit(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {

            return description;

    }

    public static Unit getUnitByDescription(List<Unit> units,String description) {

        return units.stream()
                .filter(p -> p.description.equals(description))
                .findFirst()
                .orElse(null);
    }
}

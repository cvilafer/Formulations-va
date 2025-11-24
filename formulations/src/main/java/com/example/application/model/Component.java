package com.example.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.List;


@Entity
public class Component {

    @Id
    private int id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Component() {

    }

    public Component(int id, String description, Unit unit) {
        this.id = id;
        this.description = description;
        this.unit = unit;

    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Component getComponentByDescription(List<Component> components, String description) {

        return components.stream()
                .filter(p -> p.description.equals(description))
                .findFirst()
                .orElse(null);
    }


}

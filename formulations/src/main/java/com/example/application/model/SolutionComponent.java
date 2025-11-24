package com.example.application.model;

import jakarta.persistence.*;

@Entity
public class SolutionComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // ID artificial solo para JPA

    // private Solution solution;
    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;
    private double Quantity;
    @ManyToOne
    @JoinColumn(name = "formulation_id")
    private Formulation formulation;

    public Formulation getFormulation() {
        return formulation;
    }

    public void setFormulation(Formulation formulation) {
        this.formulation = formulation;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}

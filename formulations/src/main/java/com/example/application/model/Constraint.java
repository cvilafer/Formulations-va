package com.example.application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "constraints") // usa un nombre que no sea reservado
public class Constraint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // ID artificial solo para JPA

    @ManyToOne
    @JoinColumn(name = "formulation_id")
    private Formulation formulation;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;
    private double Cant_min;
    private double Cant_max;

    public void setComponent(Component component) {
        this.component = component;
    }

    public Constraint() {


    }

    public Constraint(Formulation formulation, Component component, double Cant_min, double Cant_max) {


        this.formulation = formulation;
        this.component = component;
        this.Cant_min = Cant_min;
        this.Cant_max = Cant_max;

    }

    public Formulation getFormulation() {
        return formulation;
    }

    public void setFormulation(Formulation formulation) {
        this.formulation = formulation;
    }

    public Double getCantMax() {
        return Cant_max;
    }

    public Double getCantMin() {
        return Cant_min;
    }


    public Component getComponent() {
        return component;
    }

}

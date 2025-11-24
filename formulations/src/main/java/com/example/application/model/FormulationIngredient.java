package com.example.application.model;

import jakarta.persistence.*;

@Entity
public class FormulationIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // ID artificial solo para JPA

    //@ManyToOne //( optional = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "formulation_id", nullable = true)
    private Formulation formulation;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "ingredient_id" ) //, nullable = false)
    private Ingredient ingredient;

    public FormulationIngredient() {


    }

    public FormulationIngredient(Formulation formulation, Ingredient ingredient) {
        this.formulation = formulation;
        this.ingredient = ingredient;

    }

    public Formulation getFormulation() {
        return formulation;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}



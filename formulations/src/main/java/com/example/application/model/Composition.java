package com.example.application.model;

import jakarta.persistence.*;

@Entity
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;
    private double quantity;

    public void setComponent(Component component) {
        this.component = component;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Composition() {

    }

    public Composition(Ingredient ingredient, Component component, double quantity) {
        this.ingredient = ingredient;
        this.component = component;
        this.quantity = quantity;

    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Component getComponent() {
        return component;
    }

    public double getQuantity() {
        return quantity;
    }
}

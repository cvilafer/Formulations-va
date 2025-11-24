package com.example.application.model;

import jakarta.persistence.*;

@Entity
public class SolutionIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // ID artificial solo para JPA

    @ManyToOne
    @JoinColumn(name = "solution_id")
    private Solution solution;
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
    private double quantity;

    public Solution getSolution() {
        return solution;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {return this.ingredient;}

    public double getQuantity() {return this.quantity;}

}

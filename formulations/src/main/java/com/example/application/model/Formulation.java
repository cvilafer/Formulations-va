package com.example.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Formulation {

    @Id
    private int id;
    private String description;
    private double lastCost;

    //private List<FormulationIngredient> ingredients;

    /*@ManyToMany
    @JoinTable(
            name = "Ingredients_Formulacio",
            joinColumns = @JoinColumn(name = "formulation_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )*/
    @JsonIgnore
    // @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@OneToMany(mappedBy = "inssuranceCia", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "formulation",            cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    //@JoinColumn(name = "inssurance_cia_id")
    private List<FormulationIngredient> ingredients;

    public Formulation() {

    }

    public Formulation(int id, String description, double lastCost) {
        this.id = id;
        this.description = description;
        this.lastCost = lastCost;
        ingredients=new ArrayList<FormulationIngredient>();
    }

    public int Get_Num_Ingredients() {
        return ingredients.size();
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {}

    public List<FormulationIngredient>  GetIngredients(){
        return ingredients;
    }

    public void setLastCost(double lastCost) {
        this.lastCost = lastCost;
    }


    public static Formulation getFormulationByDescription(List<Formulation> formulations,String description) {

        return formulations.stream()
                .filter(p -> p.description.equals(description))
                .findFirst()
                .orElse(null);
    }

}

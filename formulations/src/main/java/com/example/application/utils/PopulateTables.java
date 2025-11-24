package com.example.application.utils;

import com.example.application.model.*;
import com.example.application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Component
public class PopulateTables {

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    IngredientRepository  ingredientRepository;

    @Autowired
    ComponentRepository  componentRepository;

    @Autowired
    CompositionRepository compositionRepository;

    @Autowired
    FormulationRepository  formulationRepository ;

    @Autowired
    ConstraintRepository  constraintRepository;

    @Autowired
    FormulationIngredientRepository formulationIngredientRepository;


    public void PopulateUnit() {

        //List<Unit> units=unitRepository.findAll();  //.getUnits();

        //units.add(new Unit(1, "g"));
        //units.add(new Unit(2, "L"));

        unitRepository.save(new Unit(1, "g"));
        unitRepository.save(new Unit(2, "L"));

    }

    public void PopulateIngredient() {

        //List<Ingredient> ingredients=myDataStore.getIngredients();

        ingredientRepository.save(new Ingredient(1, "Arros",unitRepository.findByDescription("g").getFirst(),3));
        ingredientRepository.save(new Ingredient(2, "Blat",unitRepository.findByDescription("g").getFirst(),2));
        ingredientRepository.save(new Ingredient(3, "Pollastre",unitRepository.findByDescription("g").getFirst(),5));



    }

    public void PopulateComponent() {

         //List<Component> components=myDataStore.getComponents();

        componentRepository.save(new Component(1, "Grasses",unitRepository.findByDescription("g").getFirst()));
        componentRepository.save(new Component(2, "Proteines",unitRepository.findByDescription("g").getFirst()));
        componentRepository.save(new Component(3, "Fibra",unitRepository.findByDescription("g").getFirst()));
        componentRepository.save(new Component(4, "Hidrats de carboni",unitRepository.findByDescription("g").getFirst()));
        componentRepository.save(new Component(5, "Calories",null));
    }

    public void PopulateComposition() {

        //List<Composition> compositions=myDataStore.getCompositions();



        compositionRepository.save(  new Composition( ingredientRepository.findByDescription("Arros").getFirst(),componentRepository.findByDescription("Grasses").getFirst(),0.2)  );
        compositionRepository.save(  new Composition( ingredientRepository.findByDescription("Arros").getFirst(),componentRepository.findByDescription("Proteines").getFirst(),0.3)  );
        compositionRepository.save(  new Composition( ingredientRepository.findByDescription("Arros").getFirst(),componentRepository.findByDescription("Fibra").getFirst(),0.5)  );

        compositionRepository.save(  new Composition( ingredientRepository.findByDescription("Blat").getFirst(),componentRepository.findByDescription("Grasses").getFirst(),0.2)  );
        compositionRepository.save(  new Composition( ingredientRepository.findByDescription("Blat").getFirst(),componentRepository.findByDescription("Proteines").getFirst(),0.2)  );
        compositionRepository.save(  new Composition( ingredientRepository.findByDescription("Blat").getFirst(),componentRepository.findByDescription("Fibra").getFirst(),0.6)  );

        compositionRepository.save(  new Composition( ingredientRepository.findByDescription("Pollastre").getFirst(),componentRepository.findByDescription("Grasses").getFirst(),0.4)  );
        compositionRepository.save(  new Composition( ingredientRepository.findByDescription("Pollastre").getFirst(),componentRepository.findByDescription("Proteines").getFirst(),0.5)  );
        compositionRepository.save(  new Composition( ingredientRepository.findByDescription("Pollastre").getFirst(),componentRepository.findByDescription("Fibra").getFirst(),0.1)  );

    }

    public  void PopulateFormulation() {

        //List<Formulation> formulations=myDataStore.getFormulations();

        formulationRepository.save(new Formulation(1, "Primera Formulació de prova",4));
        formulationRepository.save(new Formulation(2, "Segona Formulació de prova",2));
        formulationRepository.save(new Formulation(3, "Tercera Formulació de prova",2));

    }



    public void PopulateConstraints() {

        //List<Constraint> constraints=myDataStore.getConstraints();


        constraintRepository.save(  new Constraint( formulationRepository.findByDescription("Primera Formulació de prova").getFirst(),componentRepository.findByDescription("Grasses").getFirst(),0.3,0.5)  );
        constraintRepository.save(  new Constraint( formulationRepository.findByDescription("Primera Formulació de prova").getFirst(),componentRepository.findByDescription("Proteines").getFirst(),0.4,0.6)  );
        constraintRepository.save(  new Constraint( formulationRepository.findByDescription("Primera Formulació de prova").getFirst(),componentRepository.findByDescription("Fibra").getFirst(),0.05,0.15)  );



    }

    public void PopulateFormulationIngredient() {

        //List<FormulationIngredient> formulationIngredients=myDataStore.getFormulationsIngredients();


        FormulationIngredient fi=new FormulationIngredient( formulationRepository.findByDescription("Primera Formulació de prova").getFirst(),ingredientRepository.findByDescription("Arros").getFirst());
        formulationIngredientRepository.save(  fi );
        formulationRepository.findByDescription("Primera Formulació de prova").getFirst().GetIngredients().add(fi);

        fi=new FormulationIngredient( formulationRepository.findByDescription("Primera Formulació de prova").getFirst(),ingredientRepository.findByDescription("Blat").getFirst());
        formulationIngredientRepository.save(  fi  );
        formulationRepository.findByDescription("Primera Formulació de prova").getFirst().GetIngredients().add(fi);

        fi=new FormulationIngredient( formulationRepository.findByDescription("Primera Formulació de prova").getFirst(),ingredientRepository.findByDescription("Pollastre").getFirst());
        formulationIngredientRepository.save(  fi  );
        formulationRepository.findByDescription("Primera Formulació de prova").getFirst().GetIngredients().add(fi);

        fi= new FormulationIngredient( formulationRepository.findByDescription("Segona Formulació de prova").getFirst(),ingredientRepository.findByDescription("Arros").getFirst());
        formulationIngredientRepository.save( fi  );
        formulationRepository.findByDescription("Segona Formulació de prova").getFirst().GetIngredients().add(fi);

        fi=new FormulationIngredient( formulationRepository.findByDescription("Segona Formulació de prova").getFirst(),ingredientRepository.findByDescription("Blat").getFirst());
        formulationIngredientRepository.save(  fi );
        formulationRepository.findByDescription("Segona Formulació de prova").getFirst().GetIngredients().add(fi);

        fi=new FormulationIngredient( formulationRepository.findByDescription("Tercera Formulació de prova").getFirst(),ingredientRepository.findByDescription("Arros").getFirst());
        formulationIngredientRepository.save(  fi  );
        formulationRepository.findByDescription("Tercera Formulació de prova").getFirst().GetIngredients().add(fi);

        fi=new FormulationIngredient( formulationRepository.findByDescription("Tercera Formulació de prova").getFirst(),ingredientRepository.findByDescription("Blat").getFirst());
        formulationIngredientRepository.save( fi  );
        formulationRepository.findByDescription("Tercera Formulació de prova").getFirst().GetIngredients().add(fi);

        fi=new FormulationIngredient( formulationRepository.findByDescription("Tercera Formulació de prova").getFirst(),ingredientRepository.findByDescription("Pollastre").getFirst());
        formulationIngredientRepository.save(  fi  );
        formulationRepository.findByDescription("Tercera Formulació de prova").getFirst().GetIngredients().add(fi);



    }


    public String PopulateAll() {

        constraintRepository.deleteAll();
        formulationIngredientRepository.deleteAll();

        PopulateUnit();

        PopulateComponent();
        PopulateIngredient();
        PopulateComposition();
        PopulateFormulation();
        PopulateFormulationIngredient();


        PopulateConstraints();

        return "correct";
    }


}




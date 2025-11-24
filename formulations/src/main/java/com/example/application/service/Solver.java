package com.example.application.service;


import com.example.application.model.*;
import com.example.application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.Iterable;

import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class Solver  implements SolverService {

    @Autowired
    private IngredientRepository ingredientRepository;


    @Autowired
    private ConstraintRepository constraintRepository;

    @Autowired
    private CompositionRepository compositionRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private SolutionIngredientRepository solutionIngredientRepository;

    @Autowired
    private SolutionComponentRepository solutionComponentRepository;

    private int num_ing;   //   'Número d'ingredients utilitzats en la formulació. També número dimensions equacions

    private double[][] m=new double[100][100]; ;
    private double[] r=new double[100];
    private int[] ing=new int[100];   //'Conjunt d'ingredients

    private double[][] eq=new double[100][100];
    private int[] tipus_eq=new int[100];   // 'Indicarà si és una equació o una inequació: -1 per <   0 per =   +1 per >
    private int[] eq_rel=new int[100];   //'Indicarà les equacions que han sortit de la mateixa condició ("plans paral.lels"). Al redimensionar-lo se suposo que queda tot a 0




    private int num_solucions;

    //private DataStore dataStore;

    public Solver(){

    }

    public void resoldre_formulacio(Formulation formulation) {


        System.out.println("resoldre_formulacio");
        // 'Comprovar que els tots els ingredients tenen la mateix unitat o cap:
        //    If num_unitats_ingredients_formulacio_a_part_nul(Formulacio_escollida) > 1 Then
        //        MsgBox "Atenció: El programa suposa que tots els Ingredients i la Barreja final tenen la mateixa Unitat"
        //    End If

        num_ing=formulation.Get_Num_Ingredients();

        if(num_ing==0) {
            System.out.println("Cal definir els ingredients per a la formulaciñó");
            return;
        }

        //List<Ingredient> ingredients=dataStore.getIngredients();

        List<Ingredient> ingredients=(List<Ingredient>)ingredientRepository.findAll();

        for(int i=1;i<=ingredients.size();i++) {
            ing[i]=ingredients.get(i-1).getId();

        }

        //Calcular número equacions total:
        int num_constraints=0;
        //List<Constraint> constraints=dataStore.getConstraints();

        List<Constraint> constraints=(List<Constraint>)constraintRepository.findAll();
        /*for(int i=0;i<constraints.size();i++) {
            if(constraints.get(i).getFormulation()==formulation) {
                num_constraints++;
            }

        }*/

        int constraints1=constraints.stream().filter(p -> p.getFormulation() == formulation && p.getCantMax()!=null).collect(Collectors.toList()).size();

        int constraints2=constraints.stream().filter(p -> p.getFormulation() == formulation && p.getCantMin()!=null).collect(Collectors.toList()).size();

        int num_eq =1+num_ing+constraints1+constraints2;;  // 1 + num_ing + Get_Num_Equacions_Condicions_Formulacio(Formulacio_escollida)


        /*'Redimensionar array eq segons num_eq i num_ing:
        ReDim eq(num_eq, num_ing + 1)

        ReDim tipus_eq(num_eq) 'Redimensionem array tipus_eq

        ReDim eq_rel(num_eq) 'Redimensionem array eq_rel

        ReDim m(num_ing, num_ing + 1) 'Redimensionem l'array m() pel Gauss
        ReDim r(num_ing) 'Redimensionem l'array r() pel Gauss
        ReDim r_min(num_ing) 'Redimensionem l'array r_min()*/

        //Generar l'equació: suma cantitats ingredients = 1
        for(int i=1;i<=num_ing+1;i++) {
            eq[1][i] = 1;

        }
        tipus_eq[1]=0; //equació =

        //Generar equacions per cada ingredient: cantiat ingredient>0
        for(int j=1;j<=num_ing;j++) {
            for(int i=1;i<=num_ing+1;i++) {
                if( i == j) {
                    eq[1 + j][ i] = 1;

                } else {
                    eq[1 + j][i] = 0 ;

                }
            }
            tipus_eq[1 + j] = 1; //'equació >
        }

        //'Generar equacions condicions: cantitat component (per unitat de barreja final) <= o >= n

        int j = 1 + num_ing + 1;

        //List<Composition> compositions=dataStore.getCompositions();

        List<Composition> compositions=(List<Composition>)compositionRepository.findAll();

        System.out.println(num_ing);
        for(int i=1;i<=num_ing;i++) {
            System.out.println(ing[i]);
        }

        for(Constraint constraint:constraints) {
            if(constraint.getCantMin()!=null) {
                for(int i=1;i<=num_ing;i++) {
                    int i2 = i;

                    List<Composition> compositions_ing=compositions.stream().filter(p -> p.getIngredient().getId() == ing[i2] && p.getComponent().getId()==constraint.getComponent().getId()).collect(Collectors.toList());
                    if(compositions_ing.size()==0) {
                        System.out.println("Falta indicar Composició per Ingredient " + ing[i2]);
                        return;
                    }

                    eq[j][i] = compositions_ing.get(0).getQuantity();

                }
                eq[j][num_ing + 1] = constraint.getCantMin();
                tipus_eq[j] = 1; //equació >

                j = j + 1;
            }

            if(constraint.getCantMax()!=null) {
                for(int i=1;i<=num_ing;i++) {
                    int i2 = i;
                    List<Composition> compositions_ing=compositions.stream().filter(p -> p.getIngredient().getId() == ing[i2] && p.getComponent().getId()==constraint.getComponent().getId()).collect(Collectors.toList());
                    if(compositions_ing.size()==0) {
                        System.out.println("Falta indicar Composició per Ingredient " + ing[i2]);
                        return;
                    }

                    eq[j][i] = compositions_ing.get(0).getQuantity();

                }
                eq[j][num_ing + 1] = constraint.getCantMax();
                tipus_eq[j] = -1; //equació >

                j = j + 1;
            }

            if(constraint.getCantMin()!=null && constraint.getCantMax()!=null) {
                eq_rel[j - 2] = j - 1;
                eq_rel[j - 1] = j - 2;
            }

        }

        //Un cop generades les equacions tenim que resoldre tots els possibles sistemes d'equacions:
        int[] cont=new int[num_ing+1];

        for(int i=1;i<=num_ing;i++) {
            cont[i]=i;
        }

        boolean inc_cont=false; //'Indica si s'ha pogut incrementar el contador
        boolean solucio_formulacio_trobada=false;
        double cost_min;
        //int num_solucions;

        solucio_formulacio_trobada = false;
        num_solucions = 0;


        Borrar_Solucions();  //'Borrem les taules de Solucions abans de generar les Sol.lucions





        do {

            //Verificar que no hi ha cap parell d'equacions relacionades (de la mateixa condició):
            boolean eq_rel_trobada;

            eq_rel_trobada = false;
            for(int i = 1;i<= num_ing - 1;i++) {
                if(eq_rel[cont[i]] == cont[i + 1]) {
                    eq_rel_trobada = true;
                    break;
                }

            }

            boolean GoTo_no_gauss=false;

            boolean solucio_trobada=false;

            if(!GoTo_no_gauss && !eq_rel_trobada) {
                //'Copiar equacions sel.leccionades pel contador a l'array m():
                for (j = 1; j <= num_ing; j++) {
                    for (int i = 1; i <= num_ing + 1; i++) {
                        m[j][i] = eq[cont[j]][i];

                    }
                }



                solucio_trobada = resoldre_equacio(); //La sol.lució es troba a l'array r()
            } else {
                GoTo_no_gauss=true;
            }

            boolean equacions_complertes=false;

            if(!GoTo_no_gauss && solucio_trobada) {



                equacions_complertes = true;

                j = 1;  // 'Apunta a cont(1)

                for(int i = 1;i<=num_eq;i++) {
                    //'Mirar que l'equació no es trobi ja dins m() (o del contador)
                    //            'j = 1 'Apunta a cont(1)
                    if(i !=cont[j] ) {
                        //'Verificar que la sol.lució r() compleix amb l'equació eq(i,)

                        double suma;
                        int k;

                        suma = 0;
                        for(k = 1;k<=num_ing;k++) {
                            suma = suma + eq[i][k] * r[k];
                        }

                        //'Dim eq_complerta As Boolean
                        //'eq_complerta = False

                        if(tipus_eq[i] == 0) {
                            if (suma != eq[i][num_ing + 1]) {
                                equacions_complertes = false;
                                break;
                            }
                        } else if(tipus_eq[i] == 1) {

                            if (suma < eq[i][num_ing + 1]) {
                                equacions_complertes = false;
                                break;
                            }
                        } else {  //'tipus_eq(i) = -1
                            if (suma > eq[i][num_ing + 1]) {
                                equacions_complertes = false;
                                break;
                            }

                        }



                    } else {

                        if(j < num_ing) {
                            j = j + 1;
                        }

                    }

                }






            } else {
                GoTo_no_gauss=true;
            }

            if(!GoTo_no_gauss && equacions_complertes) {

                //'******** Sol.lució trobada a la Formulació: *********

                solucio_formulacio_trobada = true;
                num_solucions = num_solucions + 1;

                Afegir_Solucio(formulation);

            } else {
                GoTo_no_gauss=true;
            }

            //no_gauss:

            //'Increment contador
            for(int i = num_ing;i>=2;i--) {  //'Busquem el primer "digit" que podem incrementar començant pel final (el cont(1) sempre el deixem quiet a 1 per la primera equació)
                if(i == num_ing) {
                    if(cont[i] < num_eq) {
                        inc_cont = true;
                        cont[i] = cont[i] + 1;
                        break;

                    } else {
                        inc_cont = false;
                    }


                } else {    //'i<num_ing

                    if(cont[i + 1] > cont[i] + 1) {
                        inc_cont = true;
                        cont[i] = cont[i] + 1;

                        for (j = i + 1; j <= num_ing; j++) {  // 'Inicialitzar part següent contador
                            cont[j] = cont[i] + j - i;
                        }
                        break;
                    } else {
                        inc_cont = false;
                    }



                }
            }

        } while (inc_cont);

        //'Copiar Cost mínim al registre associat a la Formulació:
        double min_cost;

        List<Solution> solutions =(List<Solution>)solutionRepository.findAll();

        /*OptionalDouble min_cost_o = dataStore.getSolutions().stream()
                .mapToDouble(Solution::getCost)
                .min();*/

        OptionalDouble min_cost_o = solutions.stream()
                .mapToDouble(Solution::getCost)
                .min();

        min_cost=min_cost_o.getAsDouble();

        formulation.setLastCost(min_cost);

        //'Mostrar número solucions trobades
        System.out.println( num_solucions + " solucions trobades")  ;
    }


    private void Borrar_Solucions() {
        //solutions.clear();

        solutionRepository.deleteAll();

        //solutionIngredients.clear();

        solutionIngredientRepository.deleteAll();

        //solutionComponents.clear();

        solutionComponentRepository.deleteAll();

    }



    private boolean resoldre_equacio() {  // 'Possible millora: Assegurar els 0's

        int f;
        int c;
        int f2;
        int c2;
        double temp;
        double suma;
        double fact;

        for (f = 1; f < num_ing; f++) {

            if (m[f][f] == 0) {  //Cal intercanviar la fila per una altra de més a sota
                f2 = f + 1;
                while (f2 <= num_ing && m[f2 <= num_ing ? f2 : 1][f] == 0) {
                    f2 += 1;

                }
                if (f2 <= num_ing) {  //Intercanviar fila
                    for (c = 1; c <= num_ing + 1; c++) {
                        temp = m[f][c];
                        m[f][c] = m[f2][c];
                        m[f2][c] = temp;
                    }
                } else {  // Correlació linial: no solució
                    return false;
                }
            }

            for (f2 = f + 1; f2 <= num_ing; f2++) {
                fact = m[f2][f] / m[f][f];
                for (c = 1; c <= num_ing + 1; c++) {
                    m[f2][c] = m[f2][c] - fact * m[f][c];
                }
            }
        }

        //Calcular solució final
        for (c = num_ing; c >= 1; c--) {
            suma = 0;
            for (c2 = c + 1; c2 <= num_ing; c2++) {
                suma += (m[c][c2] * r[c2]);

            }
            r[c] = (m[c][num_ing + 1] - suma) / m[c][c];
        }

        return true;
    }

    private void Afegir_Solucio(Formulation formulation) {

        List<Ingredient> ingredients=(List<Ingredient>)ingredientRepository.findAll();

        double suma = 0;
        for(int i = 1;i<=num_ing;i++) {
            int i2=i;



            //List<Ingredient> ingredient=dataStore.getIngredients().stream().filter(p -> p.getId() == ing[i2]).collect(Collectors.toList());
            List<Ingredient> ingredient=ingredients.stream().filter(p -> p.getId() == ing[i2]).collect(Collectors.toList());
            suma+=r[i]*ingredient.get(0).getCost();
        }

        //'Debug.Print Get_Desc_Ingredient(ing(i)) & " " & r(i)

        Solution solution=new Solution();
        solution.setId(num_solucions);
        solution.setCost(suma);
        solution.setFormulation(formulation);
        //dataStore.getSolutions().add(solution);

        solutionRepository.save(solution);

        // 'Afegim els ingredients i les seves quantitats a la taula Solucio_Ingredients:

        for(int i = 1;i<=num_ing;i++) {
            int i2=i;

            SolutionIngredient solutionIngredient = new SolutionIngredient();
            solutionIngredient.setSolution(solution);



            //List<Ingredient> ingredient=dataStore.getIngredients().stream().filter(p -> p.getId() == ing[i2]).collect(Collectors.toList());
            List<Ingredient> ingredient=ingredients.stream().filter(p -> p.getId() == ing[i2]).collect(Collectors.toList());
            solutionIngredient.setIngredient(ingredient.get(0));
            solutionIngredient.setQuantity(r[i]);

            //dataStore.getSolutionsIngredients().add(solutionIngredient);

            solutionIngredientRepository.save(solutionIngredient);
            solution.getSolutionIngredients().add(solutionIngredient);

            //'Afegim els components i les seves quantitats totals a la taula Solucio_Components:


        }




    }

}

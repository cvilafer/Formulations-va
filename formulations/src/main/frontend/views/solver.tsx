

import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useEffect, useState } from 'react';
import { SolverController } from 'Frontend/generated/endpoints';


export const config: ViewConfig = {
  menu: { order: 4, icon: 'line-awesome/svg/cog-solid.svg' },
  title: 'Solver',
};

export default function SolverView() {

    //const [ingredients, setPopulate] = useState<Ingredient[]>([]);
    const [response,setResponse]=useState<string | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchSolver = async () => {
          try {
            setLoading(true);
            const response = await SolverController.solver();
            //alert(response);
            //setIngredients(ingredientsData ? Array.from(ingredientsData).filter((ingredient): ingredient is Ingredient => ingredient !== undefined) : []);
            setResponse("correct");
          } catch (err) {
            setError('Failed to fetch populate: ' + (err as Error).message);
          } finally {
            setLoading(false);
          }
        };

        fetchSolver();
      }, []);



  return (
    <div className="flex flex-col h-full items-center justify-center p-l text-center box-border">

      <h2>Solver {response}</h2>

    </div>
  );
}

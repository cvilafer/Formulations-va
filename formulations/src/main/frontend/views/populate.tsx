

import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useEffect, useState } from 'react';
import { PopulateController } from 'Frontend/generated/endpoints';


export const config: ViewConfig = {
  menu: { order: 6, icon: 'line-awesome/svg/clipboard-users-solid.svg' },
  title: 'Populate',
};

export default function PopulateView() {

    //const [ingredients, setPopulate] = useState<Ingredient[]>([]);
    const [response,setResponse]=useState<string | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchPopulate = async () => {
          try {
            setLoading(true);
            const response = await PopulateController.populateAllTables();
            //alert(response);
            //setIngredients(ingredientsData ? Array.from(ingredientsData).filter((ingredient): ingredient is Ingredient => ingredient !== undefined) : []);
            setResponse(response);
          } catch (err) {
            setError('Failed to fetch populate: ' + (err as Error).message);
          } finally {
            setLoading(false);
          }
        };

        fetchPopulate();
      }, []);



  return (
    <div className="flex flex-col h-full items-center justify-center p-l text-center box-border">

      <h2>Populate {response}</h2>

    </div>
  );
}

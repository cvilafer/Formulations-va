import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useEffect, useState } from 'react';
import { IngredientEndpoint } from 'Frontend/generated/endpoints';
import Ingredient from 'Frontend/generated/com/example/application/model/Ingredient';

export const config: ViewConfig = { menu: { order: 2, icon: 'line-awesome/svg/apple.svg' }, title: 'Ingredients' };

export default function IngredientsView() {
  const [ingredients, setIngredients] = useState<Ingredient[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchIngredients = async () => {
      try {
        setLoading(true);
        const ingredientsData = await IngredientEndpoint.getAllIngredients();
        //alert(ingredientsData.length);
        setIngredients(ingredientsData ? Array.from(ingredientsData).filter((ingredient): ingredient is Ingredient => ingredient !== undefined) : []);
      } catch (err) {
        setError('Failed to fetch ingredients: ' + (err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    fetchIngredients();
  }, []);

  if (loading) {
    return (
      <div className="flex flex-col h-full items-center justify-center p-4">
        <h1>Ingredients</h1>
        <p>Loading ingredients...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex flex-col h-full items-center justify-center p-4">
        <h1>Ingredients</h1>
        <p className="text-red-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="flex flex-col h-full p-4">
      <h1 className="text-2xl font-bold mb-4">Ingredients ({ingredients.length})</h1>

      <div className="grid grid-cols-1 gap-4">

        {ingredients.map((ingredient) => (
          <div key={ingredient.id} className="border rounded-lg p-4 shadow-md">
            <h3 className="text-lg font-semibold">{ingredient.description}</h3>
            <div className="mt-2 space-y-1">


              <p><strong>Price:</strong> ${ingredient.cost}</p>


            </div>
          </div>
        ))}
      </div>

      {ingredients.length === 0 && (
        <div className="text-center mt-8">
          <p>No ingredients found. Try populating the database first.</p>
        </div>
      )}
    </div>
  );
}

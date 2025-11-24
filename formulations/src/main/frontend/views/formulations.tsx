


import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useEffect, useState } from 'react';
import { FormulationEndpoint } from 'Frontend/generated/endpoints';
import Formulation from 'Frontend/generated/com/example/application/model/Formulation';

export const config: ViewConfig = { menu: { order: 1, icon: 'line-awesome/svg/wpforms.svg' }, title: 'Formulations' };

export default function FormulationsView() {
  const [formulations, setFormulations] = useState<Formulation[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchFormulations = async () => {
      try {
        setLoading(true);
        const formulationsData = await FormulationEndpoint.getAllFormulations();
        //alert(formulationsData.length);
        setFormulations(formulationsData ? Array.from(formulationsData).filter((formulation): formulation is Formulation => formulation !== undefined) : []);
      } catch (err) {
        setError('Failed to fetch formulations: ' + (err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    fetchFormulations();
  }, []);

  if (loading) {
    return (
      <div className="flex flex-col h-full items-center justify-center p-4">
        <h1>Formulations</h1>
        <p>Loading formulations...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex flex-col h-full items-center justify-center p-4">
        <h1>Formulations</h1>
        <p className="text-red-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="flex flex-col h-full p-4">
      <h1 className="text-2xl font-bold mb-4">Formulations ({formulations.length})</h1>

      <div className="grid grid-cols-1 gap-4">

        {formulations.map((formulation) => (
          <div key={formulation.id} className="border rounded-lg p-4 shadow-md">
            <h3 className="text-lg font-semibold">{formulation.description}</h3>
            <div className="mt-2 space-y-1">


              <p><strong>Price:</strong> ${formulation.lastCost}</p>


            </div>
          </div>
        ))}
      </div>

      {formulations.length === 0 && (
        <div className="text-center mt-8">
          <p>No formulations found. Try populating the database first.</p>
        </div>
      )}
    </div>
  );
}

import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useEffect, useState } from 'react';
import { SolutionEndpoint } from 'Frontend/generated/endpoints';
import Solution from 'Frontend/generated/com/example/application/model/Solution';

export const config: ViewConfig = { menu: { order: 5, icon: 'line-awesome/svg/clipboard-list-solid.svg' }, title: 'Solutions' };

export default function SolutionsView() {
  const [solutions, setSolutions] = useState<Solution[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchSolutions = async () => {
      try {
        setLoading(true);
        const solutionsData = await SolutionEndpoint.getAllSolutions();
        //alert(solutionsData.length);
        setSolutions(solutionsData ? Array.from(solutionsData).filter((solution): solution is Solution => solution !== undefined) : []);
      } catch (err) {
        setError('Failed to fetch solutions: ' + (err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    fetchSolutions();
  }, []);

  if (loading) {
    return (
      <div className="flex flex-col h-full items-center justify-center p-4">
        <h1>Solutions</h1>
        <p>Loading solutions...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex flex-col h-full items-center justify-center p-4">
        <h1>Solutions</h1>
        <p className="text-red-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="flex flex-col h-full p-4">
      <h1 className="text-2xl font-bold mb-4">Solutions ({solutions.length})</h1>

      <div className="grid grid-cols-1 gap-4">

        {solutions.map((solution) => (
          <div key={solution.id} className="border rounded-lg p-4 shadow-md">
            <h3 className="text-lg font-semibold">{solution.description}</h3>
            <div className="mt-2 space-y-1">


              <p><strong>Price:</strong> ${solution.cost}</p>


            </div>
          </div>
        ))}
      </div>

      {solutions.length === 0 && (
        <div className="text-center mt-8">
          <p>No solutions found. Try populating the database first.</p>
        </div>
      )}
    </div>
  );
}

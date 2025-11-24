import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useEffect, useState } from 'react';
import { ComponentEndpoint } from 'Frontend/generated/endpoints';
import Component from 'Frontend/generated/com/example/application/model/Component';

export const config: ViewConfig = { menu: { order: 3, icon: 'line-awesome/svg/bone-solid.svg' }, title: 'Components' };

export default function ComponentsView() {
  const [components, setComponents] = useState<Component[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchComponents = async () => {
      try {
        setLoading(true);
        const componentsData = await ComponentEndpoint.getAllComponents();
        //alert(componentsData.length);
        setComponents(componentsData ? Array.from(componentsData).filter((component): component is Component => component !== undefined) : []);
      } catch (err) {
        setError('Failed to fetch components: ' + (err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    fetchComponents();
  }, []);

  if (loading) {
    return (
      <div className="flex flex-col h-full items-center justify-center p-4">
        <h1>Components</h1>
        <p>Loading components...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex flex-col h-full items-center justify-center p-4">
        <h1>Components</h1>
        <p className="text-red-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="flex flex-col h-full p-4">
      <h1 className="text-2xl font-bold mb-4">Components ({components.length})</h1>

      <div className="grid grid-cols-1 gap-4">

        {components.map((component) => (
          <div key={component.id} className="border rounded-lg p-4 shadow-md">
            <h3 className="text-lg font-semibold">{component.description}</h3>
            <div className="mt-2 space-y-1">





            </div>
          </div>
        ))}
      </div>

      {components.length === 0 && (
        <div className="text-center mt-8">
          <p>No components found. Try populating the database first.</p>
        </div>
      )}
    </div>
  );
}

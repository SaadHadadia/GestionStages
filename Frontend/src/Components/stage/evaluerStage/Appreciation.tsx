import React from 'react';

interface AppreciationProps {
  appreciation: string;
  setAppreciation: React.Dispatch<React.SetStateAction<string>>;
  pointsForts: string;
  setPointsForts: React.Dispatch<React.SetStateAction<string>>;
  pointsFaibles: string;
  setPointsFaibles: React.Dispatch<React.SetStateAction<string>>;
}

const Appreciation: React.FC<AppreciationProps> = ({
  appreciation,
  setAppreciation,
  pointsForts,
  setPointsForts,
  pointsFaibles,
  setPointsFaibles,
}) => {
  return (
    <div>
      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mb-2">
          Appréciation générale
        </label>
        <textarea
          value={appreciation}
          onChange={(e) => setAppreciation(e.target.value)}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          rows={4}
        />
      </div>

      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mb-2">
          Points forts
        </label>
        <textarea
          value={pointsForts}
          onChange={(e) => setPointsForts(e.target.value)}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          rows={4}
        />
      </div>

      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mb-2">
          Points faibles
        </label>
        <textarea
          value={pointsFaibles}
          onChange={(e) => setPointsFaibles(e.target.value)}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          rows={4}
        />
      </div>
    </div>
  );
};

export default Appreciation;
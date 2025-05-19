import React from 'react';

interface StageInfosProps {
  description: string;
  setDescription: React.Dispatch<React.SetStateAction<string>>;
  objectif: string;
  setObjectif: React.Dispatch<React.SetStateAction<string>>;
}

const StageInfos: React.FC<StageInfosProps> = ({
  description,
  setDescription,
  objectif,
  setObjectif,
}) => {
  return (
    <div>
      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mb-2">
          Description du stage
        </label>
        <textarea
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="w-full mb-4 p-2 border border-gray-300 rounded"
          rows={4}
        />
      </div>

      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mb-2">
          Objectifs du stage
        </label>
        <textarea
          value={objectif}
          onChange={(e) => setObjectif(e.target.value)}
          className="w-full mb-4 p-2 border border-gray-300 rounded"
          rows={4}
        />
      </div>
    </div>
  );
};

export default StageInfos;
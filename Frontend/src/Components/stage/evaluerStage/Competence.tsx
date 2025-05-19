import React from 'react';

interface CompetenceProps {
  competences: {
    technique: number;
    communication: number;
    autonomie: number;
    travailEquipe: number;
    adaptation: number;
  };
  setCompetences: React.Dispatch<React.SetStateAction<{
    technique: number;
    communication: number;
    autonomie: number;
    travailEquipe: number;
    adaptation: number;
  }>>;
}

const Competence: React.FC<CompetenceProps> = ({ competences, setCompetences }) => {
  const handleChange = (type: keyof typeof competences, value: number) => {
    setCompetences(prev => ({
      ...prev,
      [type]: value
    }));
  };

  const renderRatingInput = (type: keyof typeof competences, label: string) => (
    <div className="mb-4">
      <label className="block text-gray-700 text-sm font-bold mb-2">
        {label}
      </label>
      <div className="flex gap-2">
        {[1, 2, 3, 4, 5].map((rating) => (
          <label key={rating} className="flex items-center">
            <input
              type="radio"
              name={type}
              value={rating}
              checked={competences[type] === rating}
              onChange={() => handleChange(type, rating)}
              className="mr-1"
            />
            {rating}
          </label>
        ))}
      </div>
    </div>
  );

  return (
    <div>
      {renderRatingInput('technique', 'Compétence technique')}
      {renderRatingInput('communication', 'Communication')}
      {renderRatingInput('autonomie', 'Autonomie')}
      {renderRatingInput('travailEquipe', 'Travail en équipe')}
      {renderRatingInput('adaptation', 'Adaptation')}
    </div>
  );
};

export default Competence;
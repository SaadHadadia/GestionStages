import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Loader2, Save, X } from 'lucide-react';

interface Competence {
  id: number;
  intitule: string;
  categorie: string;
}

interface Evaluation {
  id: number;
  categorie: string;
  valeur: string;
}

interface Appreciation {
  competenceId: number;
  evaluationId: string;
  commentaire: string;
}

const EvaluateStage: React.FC = () => {
  const { periodeId } = useParams<{ periodeId: string }>();
  const navigate = useNavigate();
  const [competences, setCompetences] = useState<Competence[]>([]);
  const [evaluations, setEvaluations] = useState<Evaluation[]>([]);
  const [appreciations, setAppreciations] = useState<Appreciation[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const tuteurUsername =
    JSON.parse(localStorage.getItem('userData') || '{}').username || '';

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const [competencesRes, evaluationsRes] = await Promise.all([
          axios.get('http://localhost:8081/api/competences'),
          axios.get('http://localhost:8081/api/evaluations'),
        ]);

        setCompetences(competencesRes.data);
        setEvaluations(evaluationsRes.data);

        const initialAppreciations = competencesRes.data.map((comp: Competence) => ({
          competenceId: comp.id,
          evaluationId: '',
          commentaire: '',
        }));
        setAppreciations(initialAppreciations);
      } catch (err) {
        setError('Failed to load competences or evaluations.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleInputChange = (
    index: number,
    field: keyof Appreciation,
    value: string
  ) => {
    setAppreciations((prev) => {
      const updated = [...prev];
      updated[index] = { ...updated[index], [field]: value };
      return updated;
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitting(true);
    setError(null);

    const invalidAppreciations = appreciations.some((app) => !app.evaluationId);
    if (invalidAppreciations) {
      setError('Please select an evaluation for each competence.');
      setSubmitting(false);
      return;
    }

    const payload = {
      periodeId: parseInt(periodeId || '', 10),
      tuteurUsername,
      appreciations,
    };

    try {
      await axios.post(
        `http://localhost:8081/api/stages/${periodeId}/evaluer`,
        payload,
        {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        }
      );
      alert('Evaluation submitted successfully!');
      navigate('/dashboard');
    } catch (err: any) {
      setError(
        err.response?.data?.message ||
          'Failed to submit evaluation. Please try again.'
      );
      console.error(err);
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <Loader2 className="w-8 h-8 animate-spin text-blue-500" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex justify-center items-center h-screen text-red-500">
        {error}
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white shadow-md rounded-lg">
      <h1 className="text-2xl font-bold mb-6 text-center">
        Évaluer le Stage (Période ID: {periodeId})
      </h1>
      <form onSubmit={handleSubmit} className="space-y-6">
        {competences.map((competence, index) => (
          <div
            key={competence.id}
            className="p-4 border rounded-lg bg-gray-50"
          >
            <h3 className="text-lg font-semibold mb-2">
              {competence.intitule} ({competence.categorie})
            </h3>
            <div className="mb-4">
              <label
                htmlFor={`evaluation-${index}`}
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Évaluation
              </label>
              <select
                id={`evaluation-${index}`}
                value={appreciations[index]?.evaluationId || ''}
                onChange={(e) =>
                  handleInputChange(index, 'evaluationId', e.target.value)
                }
                className="w-full p-2 border rounded-md focus:ring-blue-500 focus:border-blue-500"
                required
              >
                <option value="" disabled>
                  Sélectionner une évaluation
                </option>
                {evaluations.map((evaluation) => (
                  <option key={evaluation.id} value={evaluation.id}>
                    {evaluation.categorie} - {evaluation.valeur}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label
                htmlFor={`commentaire-${index}`}
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Commentaire
              </label>
              <textarea
                id={`commentaire-${index}`}
                value={appreciations[index]?.commentaire || ''}
                onChange={(e) =>
                  handleInputChange(index, 'commentaire', e.target.value)
                }
                className="w-full p-2 border rounded-md focus:ring-blue-500 focus:border-blue-500"
                rows={4}
                placeholder="Entrez votre commentaire ici..."
              />
            </div>
          </div>
        ))}
        {error && <p className="text-red-500 text-center">{error}</p>}
        <div className="flex justify-end space-x-4">
          <button
            type="button"
            onClick={() => navigate('/dashboard')}
            className="flex items-center px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400"
            disabled={submitting}
          >
            <X className="w-5 h-5 mr-2" />
            Annuler
          </button>
          <button
            type="submit"
            className="flex items-center px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 disabled:bg-blue-300"
            disabled={submitting}
          >
            {submitting ? (
              <Loader2 className="w-5 h-5 mr-2 animate-spin" />
            ) : (
              <Save className="w-5 h-5 mr-2" />
            )}
            Soumettre
          </button>
        </div>
      </form>
    </div>
  );
};

export default EvaluateStage;
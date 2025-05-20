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
}

interface Stage {
  id: number;
  description: string;
  objectif: string;
  entreprise: string;
}

interface Periode {
  id: number;
  stageId: number;
  dateDebut: string;
  dateFin: string;
  tuteurUsername: string;
  stagiaireUsername: string;
}

interface Tuteur {
  username: string;
  nom: string;
  prenom: string;
}

interface Stagiaire {
  username: string;
  nom: string;
  prenom: string;
}

interface StageInputDTO {
  description: string;
  objectif: string;
}

interface EvaluationStageDTO {
  periodeId: number;
  tuteurUsername: string;
  stage: StageInputDTO;
  appreciations: AppreciationInputDTO[];
}

interface AppreciationInputDTO {
  competenceId: number;
  evaluationId: string;
}

const EvaluateStage: React.FC = () => {
  const { periodeId } = useParams<{ periodeId: string }>();
  const navigate = useNavigate();
  const [stage, setStage] = useState<Stage | null>(null);
  const [periode, setPeriode] = useState<Periode | null>(null);
  const [tuteur, setTuteur] = useState<Tuteur | null>(null);
  const [stagiaire, setStagiaire] = useState<Stagiaire | null>(null);
  const [competencesByCategory, setCompetencesByCategory] = useState<
    Record<string, Competence[]>
  >({});
  const [evaluations, setEvaluations] = useState<Evaluation[]>([]);
  const [appreciations, setAppreciations] = useState<Record<number, AppreciationInputDTO>>({});
  const [stageInput, setStageInput] = useState<StageInputDTO>({
    description: '',
    objectif: '',
  });
  const [loading, setLoading] = useState<boolean>(true);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const getToken = () => localStorage.getItem('token');

  const tuteurUsername =
    JSON.parse(localStorage.getItem('userData') || '{}').username || '';

  const categoryLabels: Record<string, string> = {
    GLOBALE: 'Appréciations globales sur l\'étudiant(e)',
    INDIVIDU: 'Compétences liées à l\'individu',
    ENTREPRISE: 'Compétences liées à l\'entreprise',
    TECHNIQUE: 'Compétences techniques',
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const [periodeRes, competencesRes, evaluationsRes] = await Promise.all([
          axios.get(`${import.meta.env.VITE_API_URL}/api/periodes/${periodeId}`, {
            headers: { Authorization: `Bearer ${getToken()}` },
          }),
          axios.get(`${import.meta.env.VITE_API_URL}/api/competences`, {
            headers: { Authorization: `Bearer ${getToken()}` },
          }),
          axios.get(`${import.meta.env.VITE_API_URL}/api/evaluations`, {
            headers: { Authorization: `Bearer ${getToken()}` },
          }),
        ]);

        setPeriode(periodeRes.data);
        const stageRes = await axios.get(
          `${import.meta.env.VITE_API_URL}/api/stages/${periodeRes.data.stageId}`,
          { headers: { Authorization: `Bearer ${getToken()}` } }
        );
        setStage(stageRes.data);
        setStageInput({ description: stageRes.data.description, objectif: stageRes.data.objectif });

        const tuteurRes = await axios.get(
          `${import.meta.env.VITE_API_URL}/api/tuteurs/${tuteurUsername}`,
          { headers: { Authorization: `Bearer ${getToken()}` } }
        );
        setTuteur(tuteurRes.data);

        const stagiaireRes = await axios.get(
          `${import.meta.env.VITE_API_URL}/api/stagiaires/${periodeRes.data.stagiaireUsername}`,
          { headers: { Authorization: `Bearer ${getToken()}` } }
        );
        setStagiaire(stagiaireRes.data);

        const groupedCompetences = competencesRes.data.reduce(
          (acc: Record<string, Competence[]>, comp: Competence) => {
            if (!acc[comp.categorie]) acc[comp.categorie] = [];
            acc[comp.categorie].push(comp);
            return acc;
          },
          {}
        );
        setCompetencesByCategory(groupedCompetences);

        setEvaluations(evaluationsRes.data);

        const initialAppreciations = competencesRes.data.reduce(
          (acc: Record<number, AppreciationInputDTO>, comp: Competence) => {
            acc[comp.id] = { competenceId: comp.id, evaluationId: '' };
            return acc;
          },
          {}
        );
        setAppreciations(initialAppreciations);
      } catch (err: any) {
        setError('Failed to load stage, competences, or evaluations.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [periodeId]);

  const handleInputChange = (
    competenceId: number,
    field: keyof AppreciationInputDTO,
    value: string
  ) => {
    setAppreciations((prev) => ({
      ...prev,
      [competenceId]: { ...prev[competenceId], [field]: value },
    }));
  };

  const handleStageInputChange = (field: keyof StageInputDTO, value: string) => {
    setStageInput((prev) => ({ ...prev, [field]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitting(true);
    setError(null);

    const appreciationList = Object.values(appreciations);
    const invalidAppreciations = appreciationList.some((app) => !app.evaluationId);
    if (invalidAppreciations) {
      setError('Please select an evaluation for each competence.');
      setSubmitting(false);
      return;
    }

    const payload: EvaluationStageDTO = {
      periodeId: parseInt(periodeId || '', 10),
      tuteurUsername,
      stage: stageInput,
      appreciations: appreciationList,
    };

    try {
      await axios.post(
        `${import.meta.env.VITE_API_URL}/api/stages/${periodeId}/evaluer`,
        payload,
        { headers: { Authorization: `Bearer ${getToken()}` } }
      );
      alert('Evaluation submitted successfully!');
      navigate('/dashboard');
    } catch (err: any) {
      setError(
        err.response?.data?.message || 'Failed to submit evaluation. Please try again.'
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
    <div className="max-w-5xl mx-auto p-6 bg-white shadow-md rounded-lg">
      <h1 className="text-2xl font-bold mb-6 text-center">Évaluer le Stage</h1>
      {/* Stage Information with Editable Fields */}
      {stage && periode && tuteur && stagiaire && (
        <div className="mb-8 p-4 bg-gray-100 rounded-lg">
          <h2 className="text-xl font-bold mb-4">Détails du Stage</h2>
          <p>
            <span className="font-semibold">Entreprise:</span>{" "}
            {stage.entreprise}
          </p>
          <p>
            <span className="font-semibold">Période:</span>{" "}
            {new Date(periode.dateDebut).toLocaleDateString()} -{" "}
            {new Date(periode.dateFin).toLocaleDateString()}
          </p>
          <p>
            <span className="font-semibold">Tuteur:</span>{" "}
            {`${tuteur.prenom} ${tuteur.nom}`}
          </p>
          <p>
            <span className="font-semibold">Stagiaire:</span>{" "}
            {`${stagiaire.prenom} ${stagiaire.nom}`}
          </p>
        </div>
      )}

      <div>
        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700 mb-1">
            Sujet
          </label>
          <input
            type="text"
            value={stageInput.description}
            onChange={(e) =>
              handleStageInputChange("description", e.target.value)
            }
            className="w-full p-2 border rounded-md focus:ring-blue-500 focus:border-blue-500"
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700 mb-1">
            Objectif
          </label>
          <input
            type="text"
            value={stageInput.objectif}
            onChange={(e) => handleStageInputChange("objectif", e.target.value)}
            className="w-full p-2 border rounded-md focus:ring-blue-500 focus:border-blue-500"
          />
        </div>
      </div>

      <form onSubmit={handleSubmit} className="space-y-8">
        {/* Competences Grouped by Category */}
        {Object.keys(competencesByCategory)
          .sort()
          .map((category) => (
            <div key={category} className="border rounded-lg p-4">
              <h2 className="text-xl font-semibold mb-4">
                {categoryLabels[category]}
              </h2>
              <table className="w-full border-collapse">
                <thead>
                  <tr className="bg-gray-200">
                    <th className="border p-2 text-left">Compétence</th>
                    {evaluations.map((evaluation) => (
                      <th
                        key={evaluation.id}
                        className="border p-2 text-center"
                      >
                        {evaluation.categorie}
                      </th>
                    ))}
                  </tr>
                </thead>
                <tbody>
                  {competencesByCategory[category].map(
                    (competence: Competence) => (
                      <tr key={competence.id} className="border-t">
                        <td className="border p-2">{competence.intitule}</td>
                        {evaluations.map((evaluation) => (
                          <td
                            key={evaluation.id}
                            className="border p-2 text-center"
                          >
                            <input
                              type="radio"
                              name={`evaluation-${competence.id}`}
                              value={evaluation.id.toString()}
                              checked={
                                appreciations[competence.id]?.evaluationId ===
                                evaluation.id.toString()
                              }
                              onChange={(e) =>
                                handleInputChange(
                                  competence.id,
                                  "evaluationId",
                                  e.target.value
                                )
                              }
                              className="h-4 w-4 text-blue-600 focus:ring-blue-500 cursor-pointer"
                              required
                            />
                          </td>
                        ))}
                      </tr>
                    )
                  )}
                </tbody>
              </table>
            </div>
          ))}
        {error && <p className="text-red-500 text-center">{error}</p>}
        <div className="flex justify-end space-x-4">
          <button
            type="button"
            onClick={() => navigate("/dashboard")}
            className="flex items-center px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 cursor-pointer"
            disabled={submitting}
          >
            <X className="w-5 h-5 mr-2" />
            Annuler
          </button>
          <button
            type="submit"
            className="flex items-center px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 disabled:bg-blue-300 cursor-pointer"
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
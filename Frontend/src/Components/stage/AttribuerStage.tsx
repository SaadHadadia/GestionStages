import React, { useState, useEffect } from "react";
import axios from "axios";

interface Tuteur {
  id: number;
  username: string;
  firstname: string;
  lastname: string;
}

interface Stagiaire {
  id: number;
  username: string;
  firstname: string;
  lastname: string;
}

const AttribuerStage: React.FC = () => {
  const [tuteurs, setTuteurs] = useState<Tuteur[]>([]);
  const [stagiaires, setStagiaires] = useState<Stagiaire[]>([]);
  const [selectedTuteur, setSelectedTuteur] = useState<string>("");
  const [selectedStagiaire, setSelectedStagiaire] = useState<string>("");
  const [dateDebut, setDateDebut] = useState<string>("");
  const [dateFin, setDateFin] = useState<string>("");
  const [error, setError] = useState<string>("");

  const getToken = () => localStorage.getItem("token");

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const [tuteursResponse, stagiairesResponse] = await Promise.all([
          axios.get(`${import.meta.env.VITE_API_URL}/api/tuteurs`, {
            headers: {
              Authorization: `Bearer ${getToken()}`,
            },
          }),
          axios.get(`${import.meta.env.VITE_API_URL}/api/stagiaires`, {
            headers: {
              Authorization: `Bearer ${getToken()}`,
            },
          }),
        ]);
        setTuteurs(tuteursResponse.data);
        setStagiaires(stagiairesResponse.data);
      } catch (err) {
        setError("Erreur lors du chargement des utilisateurs");
      }
    };

    fetchUsers();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!selectedTuteur || !selectedStagiaire || !dateDebut || !dateFin) {
      setError("Veuillez remplir tous les champs");
      return;
    }

    try {
      await axios.post(
        `${import.meta.env.VITE_API_URL}/api/stages/attribuer`,
        {
          tuteurId: selectedTuteur,
          stagiaireId: selectedStagiaire,
          dateDebut,
          dateFin,
        },
        {
          headers: {
            Authorization: `Bearer ${getToken()}`,
          },
        }
      );
      // Reset form
      setSelectedTuteur("");
      setSelectedStagiaire("");
      setDateDebut("");
      setDateFin("");
      setError("");
      alert("Stage attribué avec succès!");
    } catch (err) {
      setError("Erreur lors de l'attribution du stage");
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-6">
      <h2 className="text-2xl font-bold mb-6">Attribution de Stage</h2>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-6">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Tuteur
          </label>
          <select
            value={selectedTuteur}
            onChange={(e) => setSelectedTuteur(e.target.value)}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 w-full px-4 py-2"
          >
            <option value="">Sélectionnez un tuteur</option>
            {tuteurs.map((tuteur) => (
              <option key={tuteur.username} value={tuteur.username}>
                {tuteur.firstname} {tuteur.lastname}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Stagiaire
          </label>
          <select
            value={selectedStagiaire}
            onChange={(e) => setSelectedStagiaire(e.target.value)}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 w-full px-4 py-2"
          >
            <option value="">Sélectionnez un stagiaire</option>
            {stagiaires.map((stagiaire) => (
              <option key={stagiaire.username} value={stagiaire.username}>
                {stagiaire.firstname} {stagiaire.lastname}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Date de début
          </label>
          <input
            type="date"
            value={dateDebut}
            onChange={(e) => setDateDebut(e.target.value)}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 w-full px-4 py-2"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Date de fin
          </label>
          <input
            type="date"
            value={dateFin}
            onChange={(e) => setDateFin(e.target.value)}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 w-full px-4 py-2"
          />
        </div>

        <div>
          <button
            type="submit"
            className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            Attribuer le stage
          </button>
        </div>
      </form>
    </div>
  );
};

export default AttribuerStage;

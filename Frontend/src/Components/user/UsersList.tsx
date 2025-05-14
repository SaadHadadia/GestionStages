// src/pages/users/UsersList.tsx

import React, { useEffect, useState } from "react";
import axios from "axios";
import AddUser from "./AddUser";
import { useNavigate } from "react-router-dom";

interface User {
  id: number;
  username: string;
  firstname: string;
  lastname: string;
  type: "Tuteur" | "Stagiaire" | "Admin"; // Added Admin
  entreprise?: string | null;
  institution?: string | null;
}

const UsersList: React.FC = () => {
  const navigate = useNavigate();

  const [users, setUsers] = useState<User[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [filterType, setFilterType] = useState<
    "all" | "Tuteur" | "Stagiaire" | "Admin"
  >("all");

  const [formData, setFormData] = useState<{
    username: string;
    firstname: string;
    lastname: string;
    type: "Tuteur" | "Stagiaire" | "Admin";
    entreprise: string | null;
    institution: string | null;
  }>({
    username: "",
    firstname: "",
    lastname: "",
    type: "Tuteur" as const,
    entreprise: null,
    institution: null,
  });

  const getToken = () => localStorage.getItem("token");

  const fetchUsers = async () => {
    try {
      const res = await axios.get(`${import.meta.env.VITE_API_URL}/api/users`, {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      });
      setUsers(res.data);
    } catch (err) {
      setError("Erreur lors du chargement des utilisateurs.");
    }
  };

  useEffect(() => {
    const userData = localStorage.getItem("userData");
    if (!userData) {
      navigate("/login");
      return;
    }

    const user = JSON.parse(userData);
    if (user.type !== "Admin") {
      navigate("/unauthorized");
      return;
    }

    fetchUsers();
  }, [navigate]);

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleAddUser = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      let endpoint;
      let userData;

      switch (formData.type) {
        case "Tuteur":
          endpoint = "/api/users/addTuteur";
          userData = {
            username: formData.username,
            firstname: formData.firstname,
            lastname: formData.lastname,
            entreprise: formData.entreprise,
          };
          break;
        case "Stagiaire":
          endpoint = "/api/users/addStagiaire";
          userData = {
            username: formData.username,
            firstname: formData.firstname,
            lastname: formData.lastname,
            institution: formData.institution,
          };
          break;
        case "Admin":
          endpoint = "/api/users/addAdmin";
          userData = {
            username: formData.username,
            firstname: formData.firstname,
            lastname: formData.lastname,
          };
          break;
        default:
          throw new Error("Type d'utilisateur non valide");
      }

      const res = await axios.post(
        `${import.meta.env.VITE_API_URL}${endpoint}`,
        userData,
        {
          headers: {
            Authorization: `Bearer ${getToken()}`,
          },
        }
      );

      await fetchUsers();
      setShowModal(false);
      setFormData({
        username: "",
        firstname: "",
        lastname: "",
        type: "Tuteur" as const,
        entreprise: null,
        institution: null,
      });
    } catch (err) {
      setError("Erreur lors de l'ajout de l'utilisateur.");
    }
  };

  const filteredUsers = users.filter((user) =>
    filterType === "all" ? true : user.type === filterType
  );

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Liste des utilisateurs</h1>

      {error && <div className="text-red-600 mb-4">{error}</div>}

      <div className="flex justify-between items-center mb-6">
        <div className="flex items-center gap-4">
          <button
            onClick={() => setShowModal(true)}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition-colors cursor-pointer"
          >
            Ajouter un utilisateur
          </button>

          <select
            value={filterType}
            onChange={(e) =>
              setFilterType(
                e.target.value as "all" | "Tuteur" | "Stagiaire" | "Admin"
              )
            }
            className="border rounded px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 cursor-pointer"
          >
            <option value="all">Tous les utilisateurs</option>
            <option value="Admin">Administrateurs</option>
            <option value="Tuteur">Tuteurs</option>
            <option value="Stagiaire">Stagiaires</option>
          </select>
        </div>

        <div className="text-gray-600">
          {filteredUsers.length} utilisateur(s) trouvé(s)
        </div>
      </div>

      <div className="overflow-x-auto shadow-md rounded-lg">
        <table className="w-full border-collapse bg-white">
          <thead>
            <tr className="bg-gray-50 border-b border-gray-200">
              <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Nom d'utilisateur
              </th>
              <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Prénom
              </th>
              <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Nom
              </th>
              <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Type
              </th>
              <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Entreprise/Institution
              </th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-200">
            {filteredUsers.map((user) => (
              <tr key={user.id} className="hover:bg-gray-50 transition-colors">
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {user.username}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {user.firstname}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {user.lastname}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm cursor-pointer">
                  <span
                    className={`px-3 py-1 rounded-full text-xs font-medium
                    ${
                      user.type === "Tuteur"
                        ? "bg-blue-100 text-blue-800"
                        : user.type === "Admin"
                        ? "bg-purple-100 text-purple-800"
                        : "bg-green-100 text-green-800"
                    }`}
                  >
                    {user.type}
                  </span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  {user.type === "Tuteur"
                    ? user.entreprise ?? "Non défini"
                    : user.institution ?? "Non défini"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showModal && (
        <AddUser
          formData={formData}
          onChange={handleInputChange}
          onSubmit={handleAddUser}
          onClose={() => setShowModal(false)}
        />
      )}
    </div>
  );
};

export default UsersList;

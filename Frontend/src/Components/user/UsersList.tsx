import React, { useEffect, useState } from "react";
import axios from "axios";
import { getToken } from "../../config/Auth";

interface User {
  username: string;
  firstname: string;
  lastname: string;
  type: "Tuteur" | "Stagiere";
  entreprise: string | null;
}

const UsersList = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [filteredType, setFilteredType] = useState<
    "Tous" | "Tuteur" | "Stagiere"
  >("Tous");
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);

  const [formData, setFormData] = useState<User>({
    username: "",
    firstname: "",
    lastname: "",
    type: "Tuteur",
    entreprise: "",
  });

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const res = await axios.get(
          `${import.meta.env.VITE_API_URL}/api/users`,
          {
            headers: {
              Authorization: `Bearer ${getToken()}`,
            },
          }
        );
        setUsers(res.data);
      } catch (err) {
        setError("Impossible de récupérer les utilisateurs.");
      }
    };

    fetchUsers();
  }, []);

  const utilisateursFiltres =
    filteredType === "Tous"
      ? users
      : users.filter((user) => user.type === filteredType);

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleAddUser = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      // POST vers ton API (à adapter)
      const res = await axios.post(
        `${import.meta.env.VITE_API_URL}/api/users`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${getToken()}`,
          },
        }
      );
      setUsers([...users, res.data]);
      setShowModal(false);
      setFormData({
        username: "",
        firstname: "",
        lastname: "",
        type: "Tuteur",
        entreprise: "",
      });
    } catch (err) {
      setError("Erreur lors de l'ajout de l'utilisateur.");
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-6 py-10">
      <div className="mb-8 flex flex-col md:flex-row items-start md:items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-800 mb-4 md:mb-0">
          Liste des utilisateurs
        </h1>
        <div className="flex flex-col sm:flex-row gap-4 items-center">
          <select
            value={filteredType}
            onChange={(e) => setFilteredType(e.target.value as any)}
            className="border border-gray-300 rounded-md px-3 py-2 shadow-sm focus:ring-blue-500 focus:border-blue-500"
          >
            <option value="Tous">Tous</option>
            <option value="Tuteur">Tuteur</option>
            <option value="Stagiere">Stagiaire</option>
          </select>
          <button
            onClick={() => setShowModal(true)}
            className="bg-blue-600 text-white px-4 py-2 rounded shadow hover:bg-blue-700 transition"
          >
            Ajouter un utilisateur
          </button>
        </div>
      </div>

      {error && <p className="text-red-500 mb-4 text-center">{error}</p>}

      <div className="bg-white shadow-lg rounded-lg overflow-hidden border border-gray-200">
        <table className="min-w-full table-auto text-sm">
          <thead className="bg-gray-100 text-gray-700 text-left">
            <tr>
              <th className="px-6 py-3">Nom d'utilisateur</th>
              <th className="px-6 py-3">Prénom</th>
              <th className="px-6 py-3">Nom</th>
              <th className="px-6 py-3">Rôle</th>
              <th className="px-6 py-3">Entreprise / Institution</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {utilisateursFiltres.length > 0 ? (
              utilisateursFiltres.map((user, index) => (
                <tr
                  key={index}
                  className="hover:bg-gray-50 transition duration-150"
                >
                  <td className="px-6 py-4">{user.username}</td>
                  <td className="px-6 py-4">{user.firstname}</td>
                  <td className="px-6 py-4">{user.lastname}</td>
                  <td className="px-6 py-4">
                    <span
                      className={`inline-block px-3 py-1 text-xs font-semibold rounded-full text-white ${
                        user.type === "Tuteur" ? "bg-blue-500" : "bg-green-500"
                      }`}
                    >
                      {user.type}
                    </span>
                  </td>
                  <td className="px-6 py-4 text-gray-600">
                    {user.entreprise ?? (
                      <span className="text-gray-400 italic">Aucune</span>
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={5} className="px-6 py-6 text-center text-gray-500">
                  Aucun utilisateur trouvé.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-lg w-full max-w-md p-6">
            <h2 className="text-xl font-semibold mb-4">
              Ajouter un utilisateur
            </h2>
            <form onSubmit={handleAddUser} className="space-y-4">
              <input
                name="username"
                value={formData.username}
                onChange={handleInputChange}
                placeholder="Nom d'utilisateur"
                className="w-full border rounded px-4 py-2"
                required
              />
              <input
                name="firstname"
                value={formData.firstname}
                onChange={handleInputChange}
                placeholder="Prénom"
                className="w-full border rounded px-4 py-2"
                required
              />
              <input
                name="lastname"
                value={formData.lastname}
                onChange={handleInputChange}
                placeholder="Nom"
                className="w-full border rounded px-4 py-2"
                required
              />
              <select
                name="type"
                value={formData.type}
                onChange={handleInputChange}
                className="w-full border rounded px-4 py-2"
              >
                <option value="Tuteur">Tuteur</option>
                <option value="Stagiere">Stagiaire</option>
              </select>
              <input
                name="entreprise"
                value={formData.entreprise ?? ""}
                onChange={handleInputChange}
                placeholder="Entreprise / Institution"
                className="w-full border rounded px-4 py-2"
              />
              <div className="flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setShowModal(false)}
                  className="px-4 py-2 border rounded hover:bg-gray-100"
                >
                  Annuler
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                >
                  Ajouter
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default UsersList;

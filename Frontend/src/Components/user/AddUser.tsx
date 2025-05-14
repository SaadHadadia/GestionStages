import React from "react";

interface Props {
  formData: {
    username: string;
    firstname: string;
    lastname: string;
    type: "Tuteur" | "Stagiaire" | "Admin";
    entreprise: string | null;
    institution: string | null;
  };
  onChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
  onSubmit: (e: React.FormEvent) => void;
  onClose: () => void;
}

const AddUser: React.FC<Props> = ({
  formData,
  onChange,
  onSubmit,
  onClose,
}) => {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      <div className="absolute inset-0 bg-black opacity-50" />
      <div className="relative bg-white rounded-lg shadow-lg w-full max-w-md p-6">
        <h2 className="text-xl font-semibold mb-4">Ajouter un utilisateur</h2>
        <form onSubmit={onSubmit} className="space-y-4">
          <input
            name="username"
            value={formData.username}
            onChange={onChange}
            placeholder="Nom d'utilisateur"
            className="w-full border rounded px-4 py-2"
            required
          />
          <input
            name="firstname"
            value={formData.firstname}
            onChange={onChange}
            placeholder="Prénom"
            className="w-full border rounded px-4 py-2"
            required
          />
          <input
            name="lastname"
            value={formData.lastname}
            onChange={onChange}
            placeholder="Nom"
            className="w-full border rounded px-4 py-2"
            required
          />
          <select
            name="type"
            value={formData.type}
            onChange={onChange}
            className="w-full border rounded px-4 py-2"
          >
            <option value="Tuteur">Tuteur</option>
            <option value="Stagiaire">Stagiaire</option>
            <option value="Admin">Admin</option>
          </select>
          {formData.type === "Tuteur" ? (
            <input
              name="entreprise"
              value={formData.entreprise ?? ""}
              onChange={onChange}
              placeholder="Entreprise"
              className="w-full border rounded px-4 py-2"
            />
          ) : formData.type === "Stagiaire" ? (
            <input
              name="institution"
              value={formData.institution ?? ""}
              onChange={onChange}
              placeholder="Institution"
              className="w-full border rounded px-4 py-2"
            />
          ) : null}
          <div className="flex justify-end gap-2">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 border rounded hover:bg-gray-100 cursor-pointer"
            >
              Annuler
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 cursor-pointer"
            >
              Ajouter
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddUser;
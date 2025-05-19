import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    confirmPassword: "",
    firstname: "",
    lastname: "",
    entreprise: "",
  });

  const navigate = useNavigate();
  const [error, setError] = useState("");

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    if (formData.password !== formData.confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    try {
      await axios.post(`${import.meta.env.VITE_API_URL}/register`, {
        username: formData.username,
        password: formData.password,
        firstname: formData.firstname,
        lastname: formData.lastname,
      });
      navigate("/login");
    } catch (err) {
      setError("Registration failed.");
    }
  };

  return (
    <form
      onSubmit={handleRegister}
      className="max-w-md mx-auto mt-10 p-6 bg-white rounded shadow"
    >
      <h2 className="text-2xl font-bold mb-4 text-center">Register</h2>
      <input
        className="w-full p-2 mb-2 border rounded"
        placeholder="Nom d'utilisateur"
        value={formData.username}
        onChange={(e) => setFormData({ ...formData, username: e.target.value })}
        required
      />
      <input
        className="w-full p-2 mb-2 border rounded"
        type="password"
        placeholder="Mot de passe"
        value={formData.password}
        onChange={(e) => setFormData({ ...formData, password: e.target.value })}
        required
      />
      <input
        className="w-full p-2 mb-2 border rounded"
        type="password"
        placeholder="Confirmer le mot de passe"
        value={formData.confirmPassword}
        onChange={(e) =>
          setFormData({ ...formData, confirmPassword: e.target.value })
        }
        required
      />
      <input
        className="w-full p-2 mb-2 border rounded"
        type="text"
        placeholder="Entreprise"
        value={formData.entreprise}
        onChange={(e) =>
          setFormData({ ...formData, entreprise: e.target.value })
        }
        required
      />
      <input
        className="w-full p-2 mb-2 border rounded"
        placeholder="Prénom"
        value={formData.firstname}
        onChange={(e) =>
          setFormData({ ...formData, firstname: e.target.value })
        }
        required
      />
      <input
        className="w-full p-2 mb-2 border rounded"
        placeholder="Nom"
        value={formData.lastname}
        onChange={(e) => setFormData({ ...formData, lastname: e.target.value })}
        required
      />
      <button
        className="w-full p-2 bg-blue-500 text-white rounded hover:bg-blue-600 cursor-pointer"
        type="submit"
      >
        Register
      </button>
      {error && <p className="text-red-500 mt-2">{error}</p>}
      <div className="text-center mt-8">
        <p className="text-center mt-4">
          Vous avez deja un compte?{" "}
          <a href="/login" className="text-blue-500 hover:underline">
            Login
          </a>
        </p>
      </div>
    </form>
  );
};

export default Register;

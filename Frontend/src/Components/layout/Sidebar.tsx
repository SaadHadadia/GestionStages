import React from "react";
import { LogOut, User, Home, Settings, Workflow } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { removeToken } from "../../config/Auth";

// Example: get role from localStorage (adapt as needed)
const getRole = () => {
  const userData = localStorage.getItem("userData");
  if (!userData) return "guest";
  try {
    const parsed = JSON.parse(userData);
    return parsed.type || "guest";
  } catch {
    return "guest";
  }
};

const Sidebar = () => {
  const navigate = useNavigate();
  const role = getRole();

  const logout = () => {
    removeToken();
    navigate("/login");
  };

  const links = [
    {
      name: "Accueil",
      icon: <Home size={20} />,
      path: "/",
      roles: ["Admin", "Tuteur", "Stagiaire"],
    },
    {
      name: "Utilisateurs",
      icon: <User size={20} />,
      path: "/users",
      roles: ["Admin"],
    },
    {
      name: "Paramètres",
      icon: <Settings size={20} />,
      path: "/settings",
      roles: ["Admin", "Tuteur", "Stagiaire"], 
    },
    {
      name: "Attribuer Stage",
      icon: <Workflow size={20} />,
      path: "/stage/attribuer",
      roles: ["Admin"],
    },
    {
      name: "Mes Stages",
      icon: <Workflow size={20} />,
      roles: ["Tuteur"],
      path : ""
    }
  ];

  const visibleLinks = links.filter(link => link.roles.includes(role));

  return (
    <aside className="w-64 h-screen fixed bg-white text-gray-900 flex flex-col justify-between shadow-lg">
      <div>
        <div className="text-xl font-bold p-6 border-b border-gray-300">
          Gestion des stages
        </div>
        <nav className="flex flex-col gap-2 px-4 mt-6">
          {visibleLinks.map((link) => (
            <button
              key={link.name}
              onClick={() => navigate(link.path)}
              className="flex items-center gap-3 px-4 py-2 rounded hover:bg-gray-100 transition text-left cursor-pointer"
            >
              {link.icon}
              <span>{link.name}</span>
            </button>
          ))}
        </nav>
      </div>

      <div className="p-4 border-t border-gray-300">
        <button
          onClick={logout}
          className="flex items-center gap-3 w-full px-4 py-2 rounded hover:bg-red-100 transition cursor-pointer"
        >
          <LogOut size={20} />
          <span>Déconnexion</span>
        </button>
      </div>
    </aside>
  );
};

export default Sidebar;

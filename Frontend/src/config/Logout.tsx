import { useEffect } from "react";
import { removeToken } from "./Auth";
import { useNavigate } from "react-router-dom";

const Logout = () => {
  const navigate = useNavigate();

  useEffect(() => {
    removeToken();
    navigate("/login");
  }, []);

  return <p>Logging out...</p>;
};

export default Logout;

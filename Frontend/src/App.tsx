import {
  BrowserRouter,
  Routes,
  Route,
  Outlet,
  useLocation,
} from "react-router-dom";
import Login from "./Components/Login";
import Signup from "./Components/Register";
import Home from "./Components/Home";
import NotFound from "./Components/NotFound";
import UsersList from "./Components/user/UsersList";
import PrivateRoute from "./Components/ProtectedRoute";
import Logout from "./Components/Logout";
import Sidebar from "./Components/layout/Sidebar";

// Wrapper pour inclure la sidebar dans les routes privées
const PrivateLayout = () => (
  <div className="flex">
    <Sidebar />
    <div className="ml-64 w-full p-6">
      <Outlet />
    </div>
  </div>
);

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Routes publiques */}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Signup />} />
        <Route path="/logout" element={<Logout />} />

        {/* Routes privées avec Sidebar */}
        <Route
          element={
            <PrivateRoute>
              <PrivateLayout />
            </PrivateRoute>
          }
        >
          <Route path="/users" element={<UsersList />} />
          {/* Ajoute ici d'autres routes privées si besoin */}
        </Route>

        {/* Route 404 */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

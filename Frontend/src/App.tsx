import {
  BrowserRouter,
  Routes,
  Route,
  Outlet,
  useLocation,
} from "react-router-dom";
import Login from "./Components/Login";
import Home from "./Components/Home";
import NotFound from "./Components/NotFound";
import UsersList from "./Components/user/UsersList";
import PrivateRoute from "./Components/ProtectedRoute";
import Logout from "./Components/Logout";
import Sidebar from "./Components/layout/Sidebar";
import Unauthorized from './Components/Unauthorized';
import AttribuerStage from './Components/AttribuerStage';

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
        <Route path="/logout" element={<Logout />} />
        <Route path="/unauthorized" element={<Unauthorized />} />

        {/* Routes privées avec Sidebar */}
        <Route
          element={
            <PrivateRoute>
              <PrivateLayout />
            </PrivateRoute>
          }
        >
          <Route
            path="/users"
            element={
              <PrivateRoute allowedRoles={["Admin"]}>
                <UsersList />
              </PrivateRoute>
            }
          />

          <Route
            path="/attribuerstage"
            element={
              <PrivateRoute allowedRoles={["Admin"]}>
                <AttribuerStage />
              </PrivateRoute>
            }
          />
        </Route>

        {/* Route 404 - Should be last */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

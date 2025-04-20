import Login from "./Components/Login";
import Signup from "./Components/Signup";
import Home from "./Components/Home";
import NotFound from "./Components/NotFound";
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  return <BrowserRouter>
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/signup" element={<Signup />} />
      <Route path="/login" element={<Login />} />
      <Route path="*" element={<NotFound/>} />
    </Routes>
  </BrowserRouter>;
}

export default App

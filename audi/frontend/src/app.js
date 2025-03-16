import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import AdminDashboard from "./pages/AdminDashboard";
import VenueSelection from "./pages/VenueSelection";
import AuditoriumPage from "./pages/AuditoriumPage";
import GroundPage from "./pages/GroundPage";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/venues" element={<VenueSelection />} />
        <Route path="/venues/auditorium" element={<AuditoriumPage />} />
        <Route path="/venues/ground" element={<GroundPage />} />
      </Routes>
    </Router>
  );
}

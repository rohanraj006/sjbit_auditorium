import React from "react";
import { useNavigate } from "react-router-dom";

export default function VenueSelection() {
  const navigate = useNavigate();

  return (
    <div className="p-6 text-center">
      <h2 className="text-2xl font-bold mb-6">Select a Venue</h2>
      <div className="grid grid-cols-2 gap-6">
        <div
          className="border p-6 rounded-lg shadow-md cursor-pointer hover:bg-gray-100"
          onClick={() => navigate("/booking/auditorium")}
        >
          <h3 className="text-xl font-bold">SJBIT Auditorium</h3>
          
        </div>
        <div
          className="border p-6 rounded-lg shadow-md cursor-pointer hover:bg-gray-100"
          onClick={() => navigate("/booking/ground")}
        >
          <h3 className="text-xl font-bold">SJBIT Ground</h3>
          
        </div>
      </div>
    </div>
  );
}

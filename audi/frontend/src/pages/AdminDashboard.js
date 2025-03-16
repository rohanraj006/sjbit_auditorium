import React, { useState, useEffect } from "react";

export default function AdminDashboard() {
  const [users, setUsers] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [newUser, setNewUser] = useState({ username: "", password: "", role: "user" });

  useEffect(() => {
    fetch("http://localhost:8080/audi/getBookings")
      .then((res) => res.json())
      .then((data) => setBookings(data));
  }, []);

  const handleApprove = (bookingId) => {
    fetch(`http://localhost:8080/audi/approveBooking/${bookingId}`, { method: "POST" })
      .then(() => setBookings(bookings.filter(b => b.id !== bookingId)));
  };

  const handleReject = (bookingId) => {
    fetch(`http://localhost:8080/audi/rejectBooking/${bookingId}`, { method: "POST" })
      .then(() => setBookings(bookings.filter(b => b.id !== bookingId)));
  };

  const handleAddUser = () => {
    fetch("http://localhost:8080/audi/addUser", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newUser)
    }).then(() => setNewUser({ username: "", password: "", role: "user" }));
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Admin Dashboard</h2>
      <div className="mb-6">
        <h3 className="text-xl font-bold">Add New User</h3>
        <input
          type="text"
          placeholder="Username"
          className="p-2 border rounded mr-2"
          value={newUser.username}
          onChange={(e) => setNewUser({ ...newUser, username: e.target.value })}
        />
        <input
          type="password"
          placeholder="Password"
          className="p-2 border rounded mr-2"
          value={newUser.password}
          onChange={(e) => setNewUser({ ...newUser, password: e.target.value })}
        />
        <select
          className="p-2 border rounded"
          value={newUser.role}
          onChange={(e) => setNewUser({ ...newUser, role: e.target.value })}
        >
          <option value="user">User</option>
          <option value="admin">Admin</option>
        </select>
        <button className="ml-2 bg-blue-500 text-white p-2 rounded" onClick={handleAddUser}>
          Add User
        </button>
      </div>
      <div>
        <h3 className="text-xl font-bold">Pending Bookings</h3>
        {bookings.map((booking) => (
          <div key={booking.id} className="border p-3 my-2 flex justify-between">
            <span>{booking.eventName} ({booking.venue})</span>
            <div>
              <button className="bg-green-500 text-white p-2 rounded mr-2" onClick={() => handleApprove(booking.id)}>
                Approve
              </button>
              <button className="bg-red-500 text-white p-2 rounded" onClick={() => handleReject(booking.id)}>
                Reject
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

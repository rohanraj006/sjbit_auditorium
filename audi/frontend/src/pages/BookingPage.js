import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

export default function BookingPage() {
  const { venue } = useParams();
  const [selectedDate, setSelectedDate] = useState("");
  const [eventDetails, setEventDetails] = useState({
    department: "",
    eventName: "",
    startTime: "",
    endTime: "",
  });
  const [bookedDates, setBookedDates] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch(`http://localhost:8080/audi/BookingServlet?venue=${venue}`)
      .then((res) => res.json())
      .then((data) => {
        if (data.bookings) {
          setBookedDates(data.bookings.map((booking) => booking.date));
        }
      })
      .catch((error) => console.error("Error fetching bookings:", error));
  }, [venue]);

  const handleBooking = () => {
    fetch("http://localhost:8080/audi/BookingServlet", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({
        venue,
        event_name: eventDetails.eventName,
        department: eventDetails.department,
        date: selectedDate,
        start_time: eventDetails.startTime,
        end_time: eventDetails.endTime,
      }),
    })
      .then((res) => res.json())
      .then((data) => setMessage(data.message))
      .catch((error) => setMessage("Error booking venue."));
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Book {venue}</h2>
      <input
        type="date"
        className="border p-2 mb-4"
        value={selectedDate}
        onChange={(e) => setSelectedDate(e.target.value)}
      />
      <input
        type="text"
        placeholder="Hosting Department"
        className="border p-2 mb-4"
        onChange={(e) => setEventDetails({ ...eventDetails, department: e.target.value })}
      />
      <input
        type="text"
        placeholder="Event Name"
        className="border p-2 mb-4"
        onChange={(e) => setEventDetails({ ...eventDetails, eventName: e.target.value })}
      />
      <input
        type="time"
        className="border p-2 mb-4"
        onChange={(e) => setEventDetails({ ...eventDetails, startTime: e.target.value })}
      />
      <input
        type="time"
        className="border p-2 mb-4"
        onChange={(e) => setEventDetails({ ...eventDetails, endTime: e.target.value })}
      />
      <button className="bg-blue-500 text-white px-4 py-2" onClick={handleBooking}>
        Submit Booking
      </button>
      {message && <p className="mt-4 text-red-500">{message}</p>}
    </div>
  );
}

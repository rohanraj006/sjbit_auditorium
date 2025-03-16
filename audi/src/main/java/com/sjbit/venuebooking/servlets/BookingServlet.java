package com.sjbit.venuebooking.servlets;

import com.sjbit.venuebooking.dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String venue = request.getParameter("venue");
        String eventName = request.getParameter("event_name");
        String department = request.getParameter("department");
        String date = request.getParameter("date");
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");

        boolean success = BookingDAO.addBooking(venue, eventName, department, date, startTime, endTime);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (success) {
            out.println("<h3>Booking Successful</h3>");
        } else {
            out.println("<h3>Slot Already Booked</h3>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String venue = request.getParameter("venue");
        List<Map<String, String>> bookings = BookingDAO.getBookingsForVenue(venue);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Bookings for " + venue + "</h2>");
        for (Map<String, String> booking : bookings) {
            out.println("<p>Event: " + booking.get("event_name") + " - " + booking.get("date") + " (" + booking.get("start_time") + " to " + booking.get("end_time") + ")</p>");
        }
    }
}

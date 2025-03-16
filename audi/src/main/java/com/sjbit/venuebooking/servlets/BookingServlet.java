package com.sjbit.venuebooking.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.sjbit.venuebooking.dao.BookingDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); // Allow frontend requests
        
        String venue = request.getParameter("venue");
        String eventName = request.getParameter("event_name");
        String department = request.getParameter("department");
        String date = request.getParameter("date");
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");

        boolean success = BookingDAO.addBooking(venue, eventName, department, date, startTime, endTime);
        
        PrintWriter out = response.getWriter();
        if (success) {
            out.print("{\"status\": \"success\", \"message\": \"Booking Successful\"}");
        } else {
            out.print("{\"status\": \"error\", \"message\": \"Slot Already Booked\"}");
        }
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String venue = request.getParameter("venue");
        List<Map<String, String>> bookings = BookingDAO.getBookingsForVenue(venue);

        PrintWriter out = response.getWriter();
        out.print("{\"bookings\": " + bookings.toString() + "}");
        out.flush();
    }
}

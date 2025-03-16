package com.sjbit.venuebooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sjbit.venuebooking.utils.DatabaseConnection;

public class BookingDAO {
    public static List<Map<String, String>> getBookingsForVenue(String venue) {
        List<Map<String, String>> bookings = new ArrayList<>();
        String sql = "SELECT event_name, start_time, end_time, date FROM bookings WHERE venue_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, venue);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, String> booking = new HashMap<>();
                booking.put("event_name", rs.getString("event_name"));
                booking.put("start_time", rs.getString("start_time"));
                booking.put("end_time", rs.getString("end_time"));
                booking.put("date", rs.getString("date"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public static boolean addBooking(String venue, String eventName, String department, String date, String startTime, String endTime) {
        if (isSlotBooked(venue, date, startTime, endTime)) {
            return false;
        }
        String sql = "INSERT INTO bookings (venue_name, event_name, department, date, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, venue);
            stmt.setString(2, eventName);
            stmt.setString(3, department);
            stmt.setString(4, date);
            stmt.setString(5, startTime);
            stmt.setString(6, endTime);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSlotBooked(String venue, String date, String startTime, String endTime) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE venue_name = ? AND date = ? AND ((start_time <= ? AND end_time > ?) OR (start_time < ? AND end_time >= ?))";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, venue);
            stmt.setString(2, date);
            stmt.setString(3, startTime);
            stmt.setString(4, startTime);
            stmt.setString(5, endTime);
            stmt.setString(6, endTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

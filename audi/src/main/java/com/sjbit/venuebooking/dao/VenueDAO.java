package com.sjbit.venuebooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sjbit.venuebooking.utils.DatabaseConnection;

public class VenueDAO {

    // Get a list of available venues
    public static List<String> getAllVenues() {
        List<String> venues = new ArrayList<>();
        String sql = "SELECT venue_name FROM venues";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                venues.add(rs.getString("venue_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venues;
    }
}

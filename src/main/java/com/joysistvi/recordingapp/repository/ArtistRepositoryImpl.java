package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Artist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepositoryImpl implements ArtistRepository {

    // Constructor Injection
    // Composition -> has a relationship
    private final DbConnection dbConnection;

    public ArtistRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Artist> findAllArtists() {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 0";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {



            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                artists.add(new Artist(id, name));

                //System.out.printf("%-4d %-20s%n", id, name);
            }

        } catch (SQLException e) {
            System.out.println("Read Artists Error: " + e.getMessage());
        }

        return artists;
    }
}

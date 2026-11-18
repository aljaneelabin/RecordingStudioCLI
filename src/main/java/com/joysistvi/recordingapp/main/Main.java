package com.joysistvi.recordingapp.main;

import java.sql.*;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/recording_studio_db";
    static String username = "root";
    static String password = "";

    public static void main(String[] args) {

        // ================ CRUD ALBUM ================
        viewAllAlbums();
        createAlbum("Clap Clap Clap", 2019, 7);
        updateAlbum(6, "Coco (Revised)", 2000, 4 );
        deleteAlbum(11);

        // ================ CRUD ARTIST ================
        viewAllArtist();
        createArtist("IV of Spades");
        updateArtist(8, "IV of Spades");
        deleteArtist(7);
    }

    //ducking exception database connection
    public static Connection connect () throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    //CRUD Operation

    // Read Albums
    public static void viewAllAlbums() {

        String query = "SELECT albums.id, albums.name, albums.year, artist.names AS artist_name " +
                "FROM albums " +
                "JOIN artist " +
                "ON albums.artist_id = artist.id"; // Create SQL Statement / Query

        try (
                Connection connection = connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {

            System.out.println("\n ------------------- Albums ------------------- ");
            System.out.printf("%-4s %-20s %-8s %-20s%n",
                    "ID", "Name", "Year", "Artist");

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int year = resultSet.getInt("year");
                String artistName = resultSet.getString("artist_name");

                System.out.printf("%-4d %-20s %-8d %-20s%n",
                        id, name, year, artistName);
            }

        } catch (SQLException e) {
            System.out.println("Read Albums: " + e.getMessage());
        }
    }


    //DML - Data Manipulation Language (prepareStatement) - > Create, Update, Delete
    //DQL - Data Query Language (createStatement) -> Read, Select

    // Create Album
    public static void createAlbum(String name, int year, int artistId) {

        String query = "INSERT INTO albums (name, year, artist_id) " + "VALUES (?, ?, ?)"; // anti-sql

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            //setting parameter wild cards
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, artistId);

            preparedStatement.executeUpdate();

            System.out.println("Album: " + name + " created successfully!");
            viewAllAlbums(); // RAW

        } catch (SQLException e) {
            System.out.println("Create Album: " + e.getMessage());
        }
    }

    // Update Album
    public static void updateAlbum (int id, String name, int year, int artistId){
        String query = "UPDATE albums SET name = ?, year = ?, artist_id = ? WHERE id =?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){

            //setting parameter wild cards / placeholders
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, artistId);
            preparedStatement.setInt(4, id);

            //update and check affected rows
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Album ID " + id + " updated successfully!");
                viewAllAlbums();
            } else {
                System.out.println("No album found with ID " + id + ".");
            }

        } catch (SQLException e) {
            System.out.println("Update Album: " + e.getMessage());
        }
    }

    // Delete Album
    public static void deleteAlbum (int id){
        String query = "DELETE FROM albums where id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Album ID " + id + " deleted successfully!");
                viewAllAlbums();
            } else {
                System.out.println("No album found with ID " + id + ".");
            }


        } catch (SQLException e) {
            System.out.println("Delete Album: " + e.getMessage());
        }
    }

    // Read Artist
    public static void viewAllArtist() {

        String query = "SELECT id, names FROM artist";

        try (
                Connection connection = connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {

            System.out.println("\n ------------------- Artists ------------------- ");
            System.out.printf("%-4s %-25s%n", "ID", "Artist Name");

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("names");

                System.out.printf("%-4s %-25s%n", id, name);
            }

        } catch (SQLException e) {
            System.out.println("Read Artist: " + e.getMessage());
        }
    }

    // Create Artist
    public static void createArtist(String name) {

        String query = "INSERT INTO artist (names) VALUES (?)";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            //setting parameter wild cards
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            System.out.println("Artist: " + name + " created successfully!");
            viewAllArtist(); // RAW

        } catch (SQLException e) {
            System.out.println("Create Artist: " + e.getMessage());
        }
    }

    // Update Artist
    public static void updateArtist (int id, String name){
        String query = "UPDATE artist SET names = ? WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){

            //setting parameter wild cards / placeholders
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            //update and check affected rows
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Artist ID " + id + " updated successfully!");
                viewAllArtist();
            } else {
                System.out.println("No artist found with ID " + id + ".");
            }

        } catch (SQLException e) {
            System.out.println("Update Artist: " + e.getMessage());
        }
    }

    // Delete Artist
    public static void deleteArtist (int id){
        String query = "DELETE FROM artist where id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Artist ID " + id + " deleted successfully!");
                viewAllArtist();
            } else {
                System.out.println("No artist found with ID " + id + ".");
            }


        } catch (SQLException e) {
            System.out.println("Delete Artist: " + e.getMessage());
        }
    }
}

/*
 // ginamit para iwas sa SQLException (to handle possible exception)
       // try-catch resources
        try (Connection connection = connect()) {
            System.out.println("Connected Successfully:" + connection);

        } catch (Exception e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
 */
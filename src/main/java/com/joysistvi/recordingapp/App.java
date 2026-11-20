package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.repository.ArtistRepository;
import com.joysistvi.recordingapp.repository.ArtistRepositoryImpl;
import com.joysistvi.recordingapp.service.ArtistService;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;
import com.joysistvi.recordingapp.view.ArtistView;

import java.util.List;

public class App {

    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();

        ArtistRepository artistRepository = new ArtistRepositoryImpl(dbConnection) ;
        ArtistService artistService = new ArtistServiceImpl(artistRepository);
        ArtistView artistView = new ArtistView(artistService);


    }
}

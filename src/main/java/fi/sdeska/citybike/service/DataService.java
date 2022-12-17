package fi.sdeska.citybike.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.sdeska.citybike.data.JourneyRepository;
import fi.sdeska.citybike.data.StationRepository;

@Service
public class DataService {

    private final JourneyRepository journeys;
    private final StationRepository stations;

    @Autowired
    DataService(JourneyRepository journeyRepo, StationRepository stationRepo) {
        this.journeys = journeyRepo;
        this.stations = stationRepo;
    }
    
}

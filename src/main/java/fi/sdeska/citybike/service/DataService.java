package fi.sdeska.citybike.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.iotanalytics.model.ResourceAlreadyExistsException;

import fi.sdeska.citybike.data.Journey;
import fi.sdeska.citybike.data.JourneyRepository;
import fi.sdeska.citybike.data.Station;
import fi.sdeska.citybike.data.StationRepository;

/**
 * This class handles any interactions with the database.
 */
@Service
public class DataService {

    @Autowired
    private StationRepository stations;
    @Autowired
    private JourneyRepository journeys;

    /**
     * Saves a new Station-entity to the database if there is no row with the same ID yet.
     * @param station the station to be saved.
     * @return the saved entity.
     */
    public Station saveStation(Station station) {

        Optional<Station> savedStation = stations.findById((long) station.getId());
        if (savedStation.isPresent()) {
            throw new ResourceAlreadyExistsException("Station with the given ID already exists");
        }
        return stations.save(station);

    }

    /**
     * Saves a new Journey-entity to the database.
     * @param journeyData the journey to be saved.
     * @return the saved entity.
     */
    public Journey saveJourney(Journey journey) {

        return journeys.save(journey);

    }
    
}

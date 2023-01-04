package fi.sdeska.citybike.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.iotanalytics.model.ResourceAlreadyExistsException;

import fi.sdeska.citybike.data.Journey;
import fi.sdeska.citybike.data.JourneyRepository;
import fi.sdeska.citybike.data.Station;
import fi.sdeska.citybike.data.StationRepository;

@Service
public class DataService {

    @Autowired
    private JourneyRepository journeys;
    @Autowired
    private StationRepository stations;

    /**
     * Saves the provided journey data into a new database entry. 
     * Skips data where distance or duration is less than 10.
     * @param journeyData data of the journey as a comma separated string.
     */
    public Journey saveJourney(Journey journey) {

        Optional<Journey> savedJourney = journeys.findById((long) journey.getId());
        if (savedJourney.isPresent()) {
            throw new ResourceAlreadyExistsException("Journey with the given ID already exists");
        }
        return journeys.save(journey);

    }

    public Station saveStation(Station station) {

        Optional<Station> savedStation = stations.findById((long) station.getId());
        if (savedStation.isPresent()) {
            throw new ResourceAlreadyExistsException("Station with the given ID already exists");
        }
        return stations.save(station);

    }
    
}

package fi.sdeska.citybike.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.sdeska.citybike.data.Journey;
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

    /**
     * Saves the provided journey data into a new database entry. 
     * Skips data where distance or duration is less than 10.
     * @param journeyData data of the journey as a comma separated string.
     */
    public void saveJourney(String journeyData) {
        
        var data = journeyData.split(",");
        var distance = Integer.parseInt(data[6]);
        var duration = Integer.parseInt(data[7]);

        // Do not save data if distance in meters or duration in seconds was less than 10.
        if (10 > Math.min(distance, duration)) {
            return;
        }

        var journey = new Journey();
        journey.setDepartureDate(new DateTime(data[0]));
        journey.setReturnDate(new DateTime(data[1]));
        journey.setDepartureStationID(Integer.parseInt(data[2]));
        journey.setDepartureStationName(data[3]);
        journey.setReturnStationID(Integer.parseInt(data[4]));
        journey.setReturnStationName(data[5]);
        journey.setDistance(distance);
        journey.setDuration(duration);

    }
    
}

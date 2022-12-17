package fi.sdeska.citybike.service;

import java.awt.geom.Point2D;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.sdeska.citybike.data.Journey;
import fi.sdeska.citybike.data.JourneyRepository;
import fi.sdeska.citybike.data.Station;
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

    public void saveStation(String stationData) {

        var data = stationData.split(",");
        var coords = new Point2D.Double(Integer.parseInt(data[11]), Integer.parseInt(data[12]));

        var station = new Station();
        station.setFID(Integer.parseInt(data[0]));
        station.setID(Integer.parseInt(data[1]));
        station.setNameFin(data[2]);
        station.setNameSwe(data[3]);
        station.setNameEng(data[4]);
        station.setAddressFin(data[5]);
        station.setAddressSwe(data[6]);
        station.setCityFin(data[7]);
        station.setCitySwe(data[8]);
        station.setOperator(data[9]);
        station.setCapacity(Integer.parseInt(data[10]));
        station.setCoords(coords);

    }
    
}

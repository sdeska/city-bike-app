package fi.sdeska.citybike.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import fi.sdeska.citybike.data.Journey;
import fi.sdeska.citybike.data.JourneyRepository;
import fi.sdeska.citybike.data.Station;
import fi.sdeska.citybike.data.StationRepository;

@Service
@ComponentScan("fi.sdeska.citybike.data")
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
    public void saveJourney(String journeyData) {

        var data = splitData(journeyData);
        var distance = Integer.parseInt(data[6]);
        var duration = Integer.parseInt(data[7]);

        // Do not save data if distance in meters or duration in seconds was less than 10.
        if (10 > Math.min(distance, duration)) {
            return;
        }

        var journey = new Journey().setDepartureDate(new DateTime(data[0]))
                                   .setReturnDate(new DateTime(data[1]))
                                   .setDepartureStationID(Integer.parseInt(data[2]))
                                   .setDepartureStationName(data[3])
                                   .setReturnStationID(Integer.parseInt(data[4]))
                                   .setReturnStationName(data[5])
                                   .setDistance(distance)
                                   .setDuration(duration);

        journeys.save(journey);

    }

    public void saveStation(String stationData) {

        var data = splitData(stationData);

        var station = new Station().setFID(Integer.parseInt(data[0]))
                                   .setID(Integer.parseInt(data[1]))
                                   .setNameFin(data[2])
                                   .setNameSwe(data[3])
                                   .setNameEng(data[4])
                                   .setAddressFin(data[5])
                                   .setAddressSwe(data[6])
                                   .setCityFin(data[7])
                                   .setCitySwe(data[8])
                                   .setOperator(data[9])
                                   .setCapacity(Integer.parseInt(data[10]))
                                   .setX(Double.parseDouble(data[11]))
                                   .setY(Double.parseDouble(data[12]));

        stations.save(station);

    }

    private String[] splitData(String data) {

        // This regex splits by comma if there are zero or even number of quotation marks following it.
        // It also has complexity of O(N^2), but the data fed into it will not be too long, so it will suffice in this case.
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        return data.split(regex, -1);

    }
    
}

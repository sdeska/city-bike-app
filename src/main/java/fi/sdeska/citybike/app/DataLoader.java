package fi.sdeska.citybike.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import fi.sdeska.citybike.data.Journey;
import fi.sdeska.citybike.data.Station;
import fi.sdeska.citybike.service.DataService;

@Component
public class DataLoader implements CommandLineRunner {

    private enum DataType {
        STATIONS,
        JOURNEYS
    }

    @Autowired
    private DataService dataService;

    @Override
    public void run(String... args) throws Exception {

        loadFile("Helsingin_ja_Espoon_kaupunkipyöräasemat_avoin.csv", DataType.STATIONS);
        /*loadJourneys("2021-05.csv");
        loadJourneys("2021-06.csv");
        loadJourneys("2021-07.csv");*/

    }

    /**
     * Gets the contents of a file with the given {@link #path} and passes them as a parameter to the 
     * correct method, determined by {@link #type}.
     * @param path 
     * @param type 
     */
    public void loadFile(String path, DataType type) {

        // Attempting to get the contents of the file with the given path.
        ClassPathResource resource = new ClassPathResource(path);
        InputStream file = null;
        try {
            file = resource.getInputStream();
        } catch (IOException e) {
            System.err.println("Failed to load file \"" + path + "\"");
            return;
        }

        var reader = new BufferedReader(new InputStreamReader(file));
        // Calling the correct method based on data contained in the file.
        if (type == DataType.STATIONS) {
            loadStations(reader);
            return;
        }
        loadJourneys(reader);

    }

    private void loadStations(BufferedReader content) {

        System.out.println("Loading stations from file.");
        try {
            // Throw the first line away since it does not contain data.
            content.readLine();
            String station = null;
            while ((station = content.readLine()) != null) {
                parseStation(station);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("All stations saved.");

    }

    private void loadJourneys(BufferedReader content) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private void parseStation(String stationData) {

        var data = splitData(stationData);

        var station = Station.builder()
                            .fId(Integer.parseInt(data[0]))
                            .id(Integer.parseInt(data[1]))
                            .nameFin(data[2])
                            .nameSwe(data[3])
                            .nameEng(data[4])
                            .addressFin(data[5])
                            .addressSwe(data[6])
                            .cityFin(data[7])
                            .citySwe(data[8])
                            .operator(data[9])
                            .capacity(Integer.parseInt(data[10]))
                            .x(Double.parseDouble(data[11]))
                            .y(Double.parseDouble(data[12]))
                            .build();

        dataService.saveStation(station);

    }

    private void parseJourney(String journeyData) {

        var data = splitData(journeyData);
        var distance = Integer.parseInt(data[6]);
        var duration = Integer.parseInt(data[7]);

        // Do not save data if distance in meters or duration in seconds was less than 10.
        if (10 > Math.min(distance, duration)) {
            return;
        }

        var journey = Journey.builder()
                            .departureDate(new DateTime(data[0]))
                            .returnDate(new DateTime(data[1]))
                            .departureStationID(Integer.parseInt(data[2]))
                            .departureStationName(data[3])
                            .returnStationID(Integer.parseInt(data[4]))
                            .returnStationName(data[5])
                            .distance(distance)
                            .duration(duration)
                            .build();

        dataService.saveJourney(journey);

    }

    private String[] splitData(String data) {

        // This regex splits by comma if there are zero or even number of quotation marks following it.
        // It also has complexity of O(N^2), but the data fed into it will not be too long, so it will suffice in this case.
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        return data.split(regex, -1);

    }

}

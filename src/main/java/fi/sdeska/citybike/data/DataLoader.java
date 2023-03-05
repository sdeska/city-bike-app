package fi.sdeska.citybike.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import fi.sdeska.citybike.entity.Journey;
import fi.sdeska.citybike.entity.Station;
import fi.sdeska.citybike.service.JourneyService;
import fi.sdeska.citybike.service.StationService;

/**
 * This class takes care of loading data from .csv files, parsing and saving it to the Postgres database.
 * Execution on startup is implemented by using CommandLineRunner.
 */
@Component
public class DataLoader implements CommandLineRunner {

    /**
     * Is used to specify the type 
     * of data present in .csv files.
     */
    private enum DataType {
        STATIONS,
        JOURNEYS
    }

    @Autowired
    private StationService stationService;
    @Autowired
    private JourneyService journeyService;

    /**
     * Overridden run method of CommandLineRunner.
     * Gets executed automatically on startup.
     */
    @Override
    public void run(String... args) throws Exception {

        loadFile("Helsingin_ja_Espoon_kaupunkipyöräasemat_avoin.csv", DataType.STATIONS);
        // Amount of journeys loaded per file is limited in loadJourneys for testing.
        loadFile("2021-05.csv", DataType.JOURNEYS);
        loadFile("2021-06.csv", DataType.JOURNEYS);
        loadFile("2021-07.csv", DataType.JOURNEYS);

    }

    /**
     * Gets the contents of a file with the given path and passes them as a parameter to the 
     * correct method, determined by type.
     * @param path the path to the file.
     * @param type the type of data the file contains.
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

    /**
     * Reads a file containing station data. Parses and saves data by calling parseStation() for every read line.
     * @param content the reader containing the data from the file.
     */
    public void loadStations(BufferedReader content) {

        System.out.println("Loading stations from file.");
        try {
            // Throw the first line away since it does not contain data.
            content.readLine();
            String station = null;
            while ((station = content.readLine()) != null) {
                parseStation(station);
            }
        } catch (IOException e) {
            System.err.println("ERROR: Loading stations from file failed.");
            e.printStackTrace();
        }
        System.out.println("All stations saved.");

    }

    /**
     * Reads a file containing journey data. Parses and saves data by calling parseJourney() for every read line.
     * @param content the reader containing the data from the file.
     */
    public void loadJourneys(BufferedReader content) {

        System.out.println("Loading journeys from file.");
        try {
            // Throw the first line away since it does not contain data.
            content.readLine();
            String journey = null;
            // Variable 'count' is in place just to limit loaded journeys during development.
            int count = 0;
            while ((journey = content.readLine()) != null) {
                parseJourney(journey);
                System.out.println("Journey " + count + "/599");
                count++;
                if (count == 600) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: Loading journeys from file failed.");
            e.printStackTrace();
        }
        System.out.println("All journeys saved.");

    }

    /**
     * Parses station data, creates a new Station object and saves it to the database.
     * @param stationData a string containing all Station data fields in order, separated by commas.
     */
    public void parseStation(String stationData) {

        var data = splitData(stationData);

        var station = Station.builder()
                            .fId(Long.parseLong(data[0]))
                            .id(Long.parseLong(data[1]))
                            .nameFin(data[2])
                            .nameSwe(data[3])
                            .nameEng(data[4])
                            .addressFin(data[5])
                            .addressSwe(data[6])
                            .cityFin(data[7])
                            .citySwe(data[8])
                            .operator(data[9])
                            .capacity(Long.parseLong(data[10]))
                            .x(Double.parseDouble(data[11]))
                            .y(Double.parseDouble(data[12]))
                            .build();

        stationService.saveStation(station);

    }

    /**
     * Parses journey data, creates a new Journey object and saves it to the database.
     * @param journeyData a string containing all Journey data fields in order, separated by commas.
     */
    public void parseJourney(String journeyData) {

        var data = splitData(journeyData);
        if (!validateJourney(data)) {
            return;
        }

        var journey = Journey.builder()
                            .departureDate(new DateTime(data[0]))
                            .returnDate(new DateTime(data[1]))
                            .departureStationID(Long.parseLong(data[2]))
                            .departureStationName(data[3])
                            .returnStationID(Long.parseLong(data[4]))
                            .returnStationName(data[5])
                            .distance(Long.parseLong(data[6]))
                            .duration(Long.parseLong(data[7]))
                            .build();

        journeyService.saveJourney(journey);

    }

    /**
     * Checks whether values distance and duration are the correct format and large enough to be valid.
     * @param data a string containing all Journey data fields in order, separated by commas.
     * @return true if data is valid, false otherwise.
     */
    public boolean validateJourney(String[] data) {

        // Data array should always have a length of exactly 8.
        if (data.length != 8) {
            return false;
        }
        // Only distance and duration need to be checked after parsing.
        Long distance = null;
        Long duration = null;
        try {
            new DateTime(data[0]);
            new DateTime(data[1]);
            Long.parseLong(data[2]);
            Long.parseLong(data[4]);
            distance = Long.parseLong(data[6]);
            duration = Long.parseLong(data[7]);
        } catch (NumberFormatException e) {
            System.err.println("Illegal value for Long, skipping line of data.");
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal value for DateTime, skipping line of data");
            return false;
        }

        // Data is invalid if distance in meters or duration in seconds is less than 10.
        return Math.min(distance, duration) > 10L;

    }

    /**
     * Splits strings by comma, except if the comma is between quotation marks.
     * @param data a string containing the data to be split.
     * @return a string array containing the split string parts.
     */
    public String[] splitData(String data) {

        // This regex splits by comma if there are zero or even number of quotation marks following it.
        // Updated to match pattern instead of split, as well as using a more efficient regex.
        var regex = "\"[^\"]*\"|[^,]+";
        return Pattern.compile(regex).matcher(data).results().map(MatchResult::group).toArray(String[]::new);

    }

}

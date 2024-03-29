package fi.sdeska.citybike.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOG = LogManager.getLogger();

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
     * Used for attempting to load program data from files.
     * Gets executed automatically on startup.
     */
    @Override
    public void run(String... args) throws Exception {

        // Scanner scanner = new Scanner(System.in);
        // //scanner.useDelimiter(System.lineSeparator());
        // while (true) {

        //     System.out.print("Awaiting command\n>");
        //     if (!cli(scanner)) {
        //         break;
        //     }

        // }
        // scanner.close();

        loadFiles();

    }

    /**
     * Waits for a single input to the supplied Scanner and processes it.
     * @param scanner the scanner whose input to read.
     * @return false if the program should stop receiving commands, true otherwise.
     */
    private boolean cli(Scanner scanner) {

        String input = scanner.nextLine();
        switch (input.toLowerCase()) {
            case "exit":        return false;
            case "help":        System.out.println("Commands:\n\texit\n\thelp\n\tload files"); break;
            case "load files":  loadFiles(); break;
            default:            System.out.println("Command not recognized.");
        }
        return true;

    }

    /**
     * Loads data from the files with the (for now) hardcoded names.
     */
    private void loadFiles() {

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
            LOG.info("Failed to load file \"" + path + "\"");
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

        LOG.info("Loading stations from file.");
        try {
            // Throw the first line away since it does not contain data.
            content.readLine();
            String station = null;
            while ((station = content.readLine()) != null) {
                parseStation(station);
            }
        } catch (IOException e) {
            LOG.error("Loading stations from file failed.");
            e.printStackTrace();
        }
        LOG.info("All stations saved.");

    }

    /**
     * Reads a file containing journey data. Parses and saves data by calling parseJourney() for every read line.
     * @param content the reader containing the data from the file.
     */
    public void loadJourneys(BufferedReader content) {

        LOG.info("Loading journeys from file.");
        try {
            // Throw the first line away since it does not contain data.
            content.readLine();
            String journey = null;
            // Variable 'count' is in place just to limit loaded journeys during development.
            int count = 0;
            while ((journey = content.readLine()) != null) {
                parseJourney(journey);
                count++;
                if (count == 600) {
                    break;
                }
            }
        } catch (IOException e) {
            LOG.error("Loading journeys from file failed.");
            e.printStackTrace();
        }
        LOG.info("All journeys saved.");

    }

    /**
     * Parses station data, creates a new Station object and saves it to the database.
     * @param stationData a string containing all Station data fields in order, separated by commas.
     */
    public void parseStation(String stationData) {

        var data = splitData(stationData);
        if (!validateStation(data)) {
            return;
        }

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
                            .departureDate(LocalDateTime.parse(data[0]))
                            .returnDate(LocalDateTime.parse(data[1]))
                            .departureStationID(Long.parseLong(data[2]))
                            .departureStationName(data[3])
                            .returnStationID(Long.parseLong(data[4]))
                            .returnStationName(data[5])
                            .distance(Long.parseLong(data[6]))
                            .duration(Long.parseLong(data[7]) * 1000) // Data file format has duration in seconds. Saving in milliseconds for unifying with date standards.
                            .build();

        journeyService.saveJourney(journey);

    }

    /**
     * Checks whether all parseable values in Station data are valid.
     * @param data array of strings containing the data of a single Station.
     * @return true if data is valid, false otherwise.
     */
    public boolean validateStation(String[] data) {

        // Data array should always have a length of exactly 13.
        if (data.length != 13) {
            return false;
        }
        try {
            Long.parseLong(data[0]);
            Long.parseLong(data[1]);
            Long.parseLong(data[10]);
            Double.parseDouble(data[11]);
            Double.parseDouble(data[12]);
        } catch (NumberFormatException e) {
            LOG.error("Illegal value for Long or Double, skipping line of data.");
            return false;
        }

        return true;

    }

    /**
     * Checks whether all parseable values in Journey data are valid. Also checks validity of distance and duration.
     * @param data array of strings containing the data of a single Journey.
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
            LocalDateTime.parse(data[0]);
            LocalDateTime.parse(data[1]);
            Long.parseLong(data[2]);
            Long.parseLong(data[4]);
            distance = Long.parseLong(data[6]);
            duration = Long.parseLong(data[7]);
        } catch (NumberFormatException e) {
            LOG.error("Illegal value for Long, skipping line of data.");
            return false;
        } catch (DateTimeParseException e) {
            LOG.error("Illegal value for Date, skipping line of data.");
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

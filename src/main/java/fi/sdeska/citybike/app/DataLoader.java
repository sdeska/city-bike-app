package fi.sdeska.citybike.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    enum DataType {
        STATIONS,
        JOURNEYS
    }

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

        // Calling the correct method based on data contained in the file.
        if (type == DataType.STATIONS) {
            loadStations(new BufferedReader(new InputStreamReader(file)));
            return;
        }
        loadJourneys(new BufferedReader(new InputStreamReader(file)));

    }

    public void loadStations(BufferedReader content) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void loadJourneys(BufferedReader content) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}

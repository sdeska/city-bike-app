package fi.sdeska.citybike.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        loadStations("Helsingin_ja_Espoon_kaupunkipyöräasemat_avoin.csv");
        loadJourneys("2021-05.csv");
        loadJourneys("2021-06.csv");
        loadJourneys("2021-07.csv");

    }

    public void loadStations(String filename) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void loadJourneys(String filename) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}

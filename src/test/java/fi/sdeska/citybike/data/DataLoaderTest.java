package fi.sdeska.citybike.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fi.sdeska.citybike.service.JourneyService;
import fi.sdeska.citybike.service.StationService;

@ExtendWith(MockitoExtension.class)
class DataLoaderTest {

    @Mock
    private StationService stationService;
    @Mock
    private JourneyService journeyService;

    @InjectMocks
    private DataLoader dataLoader;

    @Test
    void testValidateStation() {

        var data = new String[]{"82",
                                "711",
                                "Kirjurinkuja",
                                "Skrivargränden",
                                "Kirjurinkuja",
                                "Kirjurinkuja 1",
                                "Skrivargränden 1",
                                "Espoo",
                                "Esbo",
                                "CityBike Finland",
                                "26",
                                "24.826368",
                                "60.215751"};

        assertTrue(dataLoader.validateStation(data));
        data[0] = "250.1"; // NumberFormatException.
        assertFalse(dataLoader.validateStation(data));
        data[0] = "82"; // Back to valid.

        data[11] = "A"; // NumberFormatException.
        assertFalse(dataLoader.validateStation(data));

    }

    @Test
    void testValidateJourney() {

        var data = new String[]{"2021-05-31T23:43:07",
                                "2021-05-31T23:46:12",
                                "060",
                                "Kaapelitehdas",
                                "059",
                                "Salmisaarenranta",
                                "741",
                                "184"};

        assertTrue(dataLoader.validateJourney(data));
        data[0] = "AAA"; // Illegal DateTime format.
        assertFalse(dataLoader.validateJourney(data));
        data[0] = "2021-05-31T23:43:07"; // Back to valid.

        data[2] = "500.25"; // NumberFormatException.
        assertFalse(dataLoader.validateJourney(data));
        data[2] = "060"; // Back to valid.

        data[6] = "7"; // Test with less than minimum distance.
        assertFalse(dataLoader.validateJourney(data));
        data[6] = "741"; // Back to valid.

        data[7] = "5"; // Test with less than minimum duration.
        assertFalse(dataLoader.validateJourney(data));
        data[7] = "20.5"; // NumberFormatException.
        assertFalse(dataLoader.validateJourney(data));

    }

    @Test
    void testSplitData() {
        
        String splitWithoutQuotes = "e,e,e,e,e";
        String splitIntoTwo = "\"Hello, whoever is looking at this.\", \"I hope you have a wonderful day. :)\"";
        String splitIntoFour = "e,e,\"e,e\",e";
        assertEquals(5, dataLoader.splitData(splitWithoutQuotes).length);
        assertEquals(2, dataLoader.splitData(splitIntoTwo).length);
        assertEquals(4, dataLoader.splitData(splitIntoFour).length);

    }

}

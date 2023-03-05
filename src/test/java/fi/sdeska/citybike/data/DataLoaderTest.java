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
    void testValidateJourney() {

        var journeyData = new String[]{"2021-05-31T23:43:07",
                                       "2021-05-31T23:46:12",
                                       "060",
                                       "Kaapelitehdas",
                                       "059",
                                       "Salmisaarenranta",
                                       "741",
                                       "184"};

        assertTrue(dataLoader.validateJourney(journeyData));
        journeyData[0] = "000"; // Illegal DateTime format.
        assertFalse(dataLoader.validateJourney(journeyData));
        journeyData[0] = "2021-05-31T23:43:07"; // Back to valid.

        journeyData[3] = "500.25"; // NumberFormatException.
        assertFalse(dataLoader.validateJourney(journeyData));
        journeyData[3] = "060"; // Back to valid.

        journeyData[6] = "7"; // Test with less than minimum distance.
        assertFalse(dataLoader.validateJourney(journeyData));
        journeyData[6] = "741"; // Back to valid.

        journeyData[7] = "5"; // Test with less than minimum duration.
        assertFalse(dataLoader.validateJourney(journeyData));
        journeyData[7] = "20.5"; // NumberFormatException.
        assertFalse(dataLoader.validateJourney(journeyData));

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

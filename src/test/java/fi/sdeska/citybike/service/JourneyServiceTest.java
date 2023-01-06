package fi.sdeska.citybike.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fi.sdeska.citybike.data.Journey;
import fi.sdeska.citybike.data.JourneyRepository;

@ExtendWith(MockitoExtension.class)
class JourneyServiceTest {

    @Mock
    private JourneyRepository journeys;

    @InjectMocks
    private JourneyServiceImpl journeyService;

    private Journey journey;

    @BeforeEach
    public void setup() {

        journey = Journey.builder()
                        .departureDate(new DateTime("2021-05-31T23:57:25"))
                        .returnDate(new DateTime("2021-06-01T00:05:46"))
                        .departureStationID(1L)
                        .departureStationName("Nimi")
                        .returnStationID(1L)
                        .returnStationName("Nimi")
                        .distance(2L)
                        .duration(2L)
                        .build();

    }

    @Test
    void shouldFetchAllJourneys() {

        List<Journey> expected = Arrays.asList(journey);
        doReturn(expected).when(journeys).findAll();

        List<Journey> actual = journeyService.fetchAllJourneys();
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void shouldReturnSavedJourney() {

        when(journeys.save(journey)).thenReturn(journey);

        Journey savedJourney = journeyService.saveJourney(journey);
        assertThat(savedJourney).isEqualTo(journey);

    }

    @Test
    void shouldUpdateJourney() {

        // Method not implemented yet.
        assertThatExceptionOfType(NotYetImplementedException.class).isThrownBy(() -> {
            journeyService.updateJourney(journey, 1L);
        });

    }

    @Test
    void shouldDeleteJourneyById() {

        journeyService.deleteJourneyById(1L);
        verify(journeys, times(1)).deleteById(1L);

    }
    
}

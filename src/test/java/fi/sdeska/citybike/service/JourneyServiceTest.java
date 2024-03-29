package fi.sdeska.citybike.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import fi.sdeska.citybike.entity.Journey;
import fi.sdeska.citybike.repository.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;

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
                        .id(2L)
                        .departureDate(new Date("2021-05-31T23:57:25"))
                        .returnDate(new Date("2021-06-01T00:05:46"))
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

        // Create the expected updated journey, equal to journey except for id = 3.
        var expected = journey;
        expected.setId(3L);

        when(journeys.findById(2L)).thenReturn(Optional.of(journey));
        when(journeys.save(expected)).thenReturn(expected);
        
        // Update the original journey with id = 2. The saved journey is expected to be the one with id = 3.
        Journey updatedJourney = journeyService.updateJourney(journey, 2L);
        assertThat(updatedJourney).isEqualTo(expected);

    }

    @Test
    void shouldThrowWhenJourneyIsAbsent() {

        when(journeys.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            journeyService.updateJourney(journey, 1L);
        });

    }

    @Test
    void shouldDeleteJourneyById() {

        journeyService.deleteJourneyById(2L);
        verify(journeys, times(1)).deleteById(2L);

    }

    @Test
    void shouldFetchJourneyById() {

        when(journeys.findById(2L)).thenReturn(Optional.of(journey));
        assertThat(journeyService.fetchJourneyById(2L)).isEqualTo(journey);

    }

    @Test
    void shouldThrowWhenJourneyNotFound() {

        when(journeys.findById(2L)).thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
            journeyService.fetchJourneyById(2L);
        });

    }

    @Test
    void shouldFetchPaginated() {

        var order = new Order(Sort.Direction.ASC, "id");

        var expectedPage = new PageImpl<>(Arrays.asList(journey));
        when(journeys.findAll(PageRequest.of(0, 100, Sort.by(order)))).thenReturn(expectedPage);

        var actualPage = journeyService.fetchPaginated(PageRequest.of(0, 100, Sort.by(order)));
        assertThat(actualPage.getContent().get(0).getId()).isEqualTo(expectedPage.getContent().get(0).getId());

    }
    
}

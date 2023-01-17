package fi.sdeska.citybike.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
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

import com.amazonaws.services.iotanalytics.model.ResourceAlreadyExistsException;
import com.amazonaws.services.iotanalytics.model.ResourceNotFoundException;

import fi.sdeska.citybike.data.Station;
import fi.sdeska.citybike.data.StationRepository;

@ExtendWith(MockitoExtension.class)
class StationServiceTest {

    @Mock
    private StationRepository stations;

    @InjectMocks
    private StationServiceImpl stationService;

    private Station station;

    @BeforeEach
    public void setup() {

        station = Station.builder()
                        .fId(1L)
                        .id(55L)
                        .nameFin("Nimi")
                        .nameSwe("Namn")
                        .nameEng("Name")
                        .addressFin("Osoite")
                        .addressSwe("Adress")
                        .cityFin("Kaupunki")
                        .citySwe("Stad")
                        .operator("Elisa")
                        .capacity(1L)
                        .x(1D)
                        .y(1D)
                        .build();

    }

    /*@Test
    void shouldFetchAllStations() {

        List<Station> expected = Arrays.asList(station);
        when(stations.findAll()).thenReturn(expected);

        List<Station> actual = stationService.fetchAllStations();
        assertThat(actual).isEqualTo(expected);

    }*/

    @Test
    void shouldReturnSavedStation() {

        when(stations.save(station)).thenReturn(station);

        Station savedStation = stationService.saveStation(station);
        assertThat(savedStation).isEqualTo(station);

    }

    @Test
    void shouldThrowWhenSavedStationExists() {

        when(stations.findById(station.getId())).thenReturn(Optional.of(station));

        assertThatExceptionOfType(ResourceAlreadyExistsException.class).isThrownBy(() -> {
            stationService.saveStation(station);
        });

    }

    @Test
    void shouldUpdateStation() {

        // Create the expected updated station, equal to station except for id = 3.
        var expected = station;
        expected.setId(3L);

        when(stations.findById(1L)).thenReturn(Optional.of(station));
        when(stations.save(expected)).thenReturn(expected);
        
        // Update the original station with id = 1. The saved station is expected to be the one with id = 3.
        Station updatedStation = stationService.updateStation(expected, 1L);
        assertThat(updatedStation).isEqualTo(expected);

    }

    @Test
    void shouldThrowWhenStationIsAbsent() {

        when(stations.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            stationService.updateStation(station, 1L);
        });

    }

    @Test
    void shouldDeleteStationById() {

        stationService.deleteStationById(1L);
        verify(stations, times(1)).deleteById(1L);

    }

    @Test
    void shouldFetchStationById() {

        when(stations.findById(1L)).thenReturn(Optional.of(station));
        assertThat(stationService.fetchStationById(1L)).isEqualTo(station);

    }

    @Test
    void shouldThrowWhenStationNotFound() {

        when(stations.findById(2L)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> {
            stationService.fetchStationById(2L);
        });

    }

    @Test
    void shouldFetchPaginated() {

        var order = new Order(Sort.Direction.ASC, "id");

        var expectedPage = new PageImpl<>(Arrays.asList(station));
        when(stations.findAll(PageRequest.of(0, 100, Sort.by(order)))).thenReturn(expectedPage);

        var actualPage = stationService.fetchPaginated(PageRequest.of(0, 100, Sort.by(order)));
        assertThat(actualPage.getContent().get(0).getId()).isEqualTo(expectedPage.getContent().get(0).getId());

    }

}

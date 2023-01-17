package fi.sdeska.citybike.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fi.sdeska.citybike.data.Station;

public interface StationService {
    
    Station saveStation(Station station);
    List<Station> fetchAllStations();
    Station updateStation(Station station, Long id);
    void deleteStationById(Long id);
    Station fetchStationById(Long id);
    Page<Station> fetchPaginated(Pageable pageable);

}

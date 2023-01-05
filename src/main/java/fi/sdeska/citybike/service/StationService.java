package fi.sdeska.citybike.service;

import java.util.List;

import fi.sdeska.citybike.data.Station;

public interface StationService {
    
    Station saveStation(Station station);
    List<Station> fetchAllStations();
    Station updateStation(Station station, Long id);
    void deleteStationById(Long id);

}

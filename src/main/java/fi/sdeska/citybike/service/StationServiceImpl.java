package fi.sdeska.citybike.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.amazonaws.services.iotanalytics.model.ResourceAlreadyExistsException;
import com.amazonaws.services.iotanalytics.model.ResourceNotFoundException;

import fi.sdeska.citybike.data.Station;
import fi.sdeska.citybike.data.StationRepository;

/**
 * This class handles any interactions with stations in the database.
 */
@Component
public class StationServiceImpl implements StationService {
    
    @Autowired
    private StationRepository stations;

    @Override
    public Station saveStation(Station station) {

        Optional<Station> savedStation = stations.findById((long) station.getId());
        if (savedStation.isPresent()) {
            throw new ResourceAlreadyExistsException("Station with the given ID already exists.");
        }
        return stations.save(station);

    }

    @Override
    public List<Station> fetchAllStations() {
        return (List<Station>) stations.findAll();
    }

    @Override
    public Station updateStation(Station station, Long id) {

        Optional<Station> savedStation = stations.findById(id);
        if (savedStation.isEmpty()) {
            throw new ResourceNotFoundException("Station with the given ID does not exist.");
        }
        return stations.save(station);
        
    }

    @Override
    public void deleteStationById(Long id) {
        stations.deleteById(id);
    }

    @Override
    public Station fetchStationById(Long id) {
        
        var foundStation = stations.findById(id);
        if (foundStation.isEmpty()) {
            throw new ResourceNotFoundException("Station with the given ID does not exist.");
        }
        return foundStation.get();
    
    }

    public Page<Station> findPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentIndex = pageable.getPageNumber();
        int startIndex = currentIndex * pageSize;
        var stationsList = fetchAllStations();
        List<Station> pageContents = null;

        if (stationsList.size() < startIndex) {
            pageContents = Collections.emptyList();
        }
        else {
            int lastIndex = Math.min(startIndex + pageSize, stationsList.size());
            pageContents = stationsList.subList(startIndex, lastIndex);
        }

        return new PageImpl<>(pageContents, PageRequest.of(currentIndex, pageSize), stationsList.size());

    }

}

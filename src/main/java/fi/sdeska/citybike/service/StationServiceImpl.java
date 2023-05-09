package fi.sdeska.citybike.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.amazonaws.services.iotanalytics.model.ResourceNotFoundException;

import fi.sdeska.citybike.entity.Station;
import fi.sdeska.citybike.repository.StationRepository;

/**
 * This class implements the StationService interface.
 */
@Component
public class StationServiceImpl implements StationService {
    
    @Autowired
    private StationRepository stations;

    /**
     * Saves a station unless a station with the corresponding ID already exists.
     */
    @Override
    public Station saveStation(@NonNull Station station) {

        Optional<Station> savedStation = stations.findById((long) station.getId());
        if (savedStation.isPresent()) {
            System.err.println("Station already exists. Skipping line with ID: " + station.getId());
        }
        return stations.save(station);

    }

    @Override
    public List<Station> fetchAllStations() {
        return stations.findAll();
    }

    /**
     * @throws ResourceNotFoundException when a station with the given ID does not exist in the database.
     */
    @Override
    public Station updateStation(Station station, @NonNull Long id) {

        Optional<Station> savedStation = stations.findById(id);
        if (savedStation.isEmpty()) {
            throw new ResourceNotFoundException("Station with the given ID does not exist.");
        }
        return stations.save(station);
        
    }

    @Override
    public void deleteStationById(@NonNull Long id) {
        stations.deleteById(id);
    }

    /**
     * @throws ResourceNotFoundException when a station with the given ID does not exist in the database.
     */
    @Override
    public Station fetchStationById(Long id) {
        
        var foundStation = stations.findById(id);
        if (foundStation.isEmpty()) {
            throw new ResourceNotFoundException("Station with the given ID does not exist.");
        }
        return foundStation.get();
    
    }

    @Override
    public Page<Station> fetchPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentIndex = pageable.getPageNumber();
        int startIndex = currentIndex * pageSize;

        var stationPage = stations.findAll(pageable);
        var totalStations = stationPage.getTotalElements();

        List<Station> pageContents = null;
        if (totalStations < startIndex) {
            pageContents = Collections.emptyList();
        }
        else {
            pageContents = stationPage.getContent();
        }

        return new PageImpl<>(pageContents, PageRequest.of(currentIndex, pageSize, pageable.getSort()), totalStations);

    }

    @Override
    public Long getNumberOfJourneysStartingHere(Long id) {

        return stations.getNumberOfJourneysStartingHere(id);
        
    }

    @Override
    public Long getNumberOfJourneysEndingHere(Long id) {

        return stations.getNumberOfJourneysEndingHere(id);

    }

    @Override
    public List<Station> getStationsWithSmallestAndLargestLatitudes() {

        var stationList = stations.findAll();
        var latitudes = new ArrayList<Double>(stationList.size());
        var result = new ArrayList<Station>(2);
        for (var station : stationList) {
            latitudes.add(station.getY());
        }
        var smallest = Collections.min(latitudes);
        result.add(stationList.get(latitudes.indexOf(smallest)));
        var largest = Collections.max(latitudes);
        result.add(stationList.get(latitudes.indexOf(largest)));
        return result;

    }

    @Override
    public List<Station> getStationsWithSmallestAndLargestLongitudes() {

        var stationList = stations.findAll();
        var longitudes = new ArrayList<Double>(stationList.size());
        var result = new ArrayList<Station>(2);
        for (var station : stationList) {
            longitudes.add(station.getX());
        }
        var smallest = Collections.min(longitudes);
        result.add(stationList.get(longitudes.indexOf(smallest)));
        var largest = Collections.max(longitudes);
        result.add(stationList.get(longitudes.indexOf(largest)));
        return result;

    }

}

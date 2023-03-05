package fi.sdeska.citybike.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import fi.sdeska.citybike.entity.Station;

/**
 * This interface defines the methods for any service implementation interacting with stations in the repository.
 */
public interface StationService {
    
    /**
     * Saves a station into the station repository.
     * Action taken in the case where a station with the given ID already exists is up to the implementation.
     * @param station the station to be saved.
     * @return the saved station.
     */
    Station saveStation(@NonNull Station station);

    /**
     * Fetches all stations present in the repository.
     * @return a list containing all found stations.
     */
    List<Station> fetchAllStations();

    /**
     * Updates a station with the given ID which already exists in the repository.
     * Action taken in the case where a station with the given ID does not exist is up to the implementation.
     * @param station the new station to replace the one with the given ID.
     * @param id the ID of the old, to-be-replaced station.
     * @return the saved station.
     */
    Station updateStation(Station station, @NonNull Long id);

    /**
     * Deletes a station with the given ID.
     * @param id the ID of the station to be deleted.
     */
    void deleteStationById(@NonNull Long id);

    /**
     * Fetches a station specified by its ID.
     * @param id the ID of the station to be fetched.
     * @return the found station.
     */
    Station fetchStationById(Long id);

    /**
     * Fetches a page containing the currently visible stations and information about all the stations.
     * @param pageable the pageable which contains pagination and ordering information.
     * @return the page containing currently visible stations.
     */
    Page<Station> fetchPaginated(Pageable pageable);

    Long getNumberOfJourneysStartingHere(Long id);

}

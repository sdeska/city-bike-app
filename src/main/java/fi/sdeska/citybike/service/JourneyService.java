package fi.sdeska.citybike.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import fi.sdeska.citybike.data.Journey;

/**
 * This interface defines the methods for any service implementation interacting with journeys in the repository.
 */
public interface JourneyService {
    
    /**
     * Saves a journey into the journey repository.
     * Action taken in the case where a journey with the given ID already exists is up to the implementation.
     * @param journey the journey to be saved.
     * @return the saved journey.
     */
    Journey saveJourney(@NonNull Journey journey);
    
    /**
     * Fetches all journeys present in the repository.
     * @return a list containing all found journeys.
     */
    List<Journey> fetchAllJourneys();

    /**
     * Updates a journey with the given ID which already exists in the repository.
     * Action taken in the case where a journey with the given ID does not exist is up to the implementation.
     * @param journey the new journey to replace the one with the given ID.
     * @param id the ID of the old, to-be-replaced journey.
     * @return the saved journey.
     */
    Journey updateJourney(Journey journey, @NonNull Long id);

    /**
     * Deletes a journey with the given ID.
     * @param id the ID of the journey to be deleted.
     */
    void deleteJourneyById(Long id);

    /**
     * Fetches a journey specified by its ID.
     * @param id the ID of the journey to be fetched.
     * @return the found journey.
     */
    Journey fetchJourneyById(Long id);

    /**
     * Fetches a page containing the currently visible journeys and information about all the journeys.
     * @param pageable the pageable which contains pagination and ordering information.
     * @return the page containing currently visible journeys.
     */
    Page<Journey> fetchPaginated(Pageable pageable);

}

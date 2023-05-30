package fi.sdeska.citybike.repository;

import java.util.Optional;

import fi.sdeska.citybike.entity.Journey;

/**
 * This interface defines custom JourneyRepository methods for using custom queries.
 */
public interface CustomJourneyRepository {

    /**
     * Finds a Journey from the database by the key departureDate, returnDate, departureStationID,
     * returnStationID, distance, duration.
     * @param journey the Journey for which to search.
     * @return the entity with the given id or Optional#empty() if none found.
     */
    Optional<Journey> findByKey(Journey journey);
    
}

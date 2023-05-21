package fi.sdeska.citybike.repository;

import org.springframework.data.domain.Page;

import fi.sdeska.citybike.entity.Station;

/**
 * This interface defines custom StationRepository methods for using custom queries.
 */
public interface CustomStationRepository {

    /**
     * Gets the number of journeys starting at a given station from the database.
     * @param id the ID of the station.
     * @return the number of journeys starting at the station.
     */
    Long getNumberOfJourneysStartingHere(Long id);

    /**
     * Gets the number of journeys ending at a given station from the database.
     * @param id the ID of the station.
     * @return the number of journeys ending at the station.
     */
    Long getNumberOfJourneysEndingHere(Long id);

    Page<Station> searchBy(String text, Integer limit, String... fields);

}

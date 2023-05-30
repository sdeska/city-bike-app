package fi.sdeska.citybike.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

import fi.sdeska.citybike.entity.Journey;

public class CustomJourneyRepositoryImpl implements CustomJourneyRepository {
    
    @PersistenceContext
    private EntityManager em;

    public Optional<Journey> findByKey(Journey journey) {

        var queryString = new StringBuilder().append("SELECT j ")
                                            .append("FROM Journey AS j ")
                                            .append("WHERE departureDate = :departureDate ")
                                            .append("AND returnDate = :returnDate ")
                                            .append("AND departureStationID = :departureStationID ")
                                            .append("AND returnStationID = :returnStationID ")
                                            .append("AND distance = :distance ")
                                            .append("AND duration = :duration")
                                            .toString();
        var query = em.createQuery(queryString);
        query.setParameter("departureDate", journey.getDepartureDate());
        query.setParameter("returnDate", journey.getReturnDate());
        query.setParameter("departureStationID", journey.getDepartureStationID());
        query.setParameter("returnStationID", journey.getReturnStationID());
        query.setParameter("distance", journey.getDistance());
        query.setParameter("duration", journey.getDuration());

        Optional<Journey> foundJourney = null;
        try {
            foundJourney = Optional.of((Journey) query.getSingleResult());
        } catch (Exception e) {
            foundJourney = Optional.empty();
        }
        return foundJourney;

    }

}

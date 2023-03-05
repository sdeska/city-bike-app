package fi.sdeska.citybike.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CustomStationRepositoryImpl implements CustomStationRepository {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public Long getNumberOfJourneysStartingHere(Long id) {

        var queryString = new StringBuilder().append("SELECT COUNT(*) ")
                                            .append("FROM Station AS s ")
                                            .append("INNER JOIN Journey AS j ON s.id = j.departureStationID ")
                                            .append("WHERE s.id = :id")
                                            .toString();
        var query = em.createQuery(queryString);
        query.setParameter("id", id);

        return (Long) query.getSingleResult();

    }

}

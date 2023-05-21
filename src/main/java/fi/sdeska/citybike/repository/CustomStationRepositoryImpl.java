package fi.sdeska.citybike.repository;

import org.hibernate.Session;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import fi.sdeska.citybike.entity.Station;

/**
 * This class implements the CustomStationRepository interface.
 */
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

    @Override
    public Long getNumberOfJourneysEndingHere(Long id) {

        var queryString = new StringBuilder().append("SELECT COUNT(*) ")
                                            .append("FROM Station AS s ")
                                            .append("INNER JOIN Journey AS j ON s.id = j.returnStationID ")
                                            .append("WHERE s.id = :id")
                                            .toString();
        var query = em.createQuery(queryString);
        query.setParameter("id", id);

        return (Long) query.getSingleResult();

    }

    @Override
    public Page<Station> searchBy(String text, Integer limit, String... fields) {

        SearchSession searchSession = Search.session(em.unwrap(Session.class));
        SearchResult<Station> result = null;

        result = searchSession.search(Station.class)
                    .where(search -> search.match()
                                            .fields(fields)
                                            .matching(text)
                                            .fuzzy(1))
                    .fetch(limit);

        return new PageImpl<Station>(result.hits());

    }
    
}

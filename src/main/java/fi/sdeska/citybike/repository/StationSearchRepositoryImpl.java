package fi.sdeska.citybike.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.List;

@Transactional
public class StationSearchRepositoryImpl<Station, ID extends Serializable> extends SimpleJpaRepository<Station, ID> 
                                                        implements StationSearchRepository<Station, ID> {

    private final EntityManager em;

    public StationSearchRepositoryImpl(Class<Station> stationClass, EntityManager em) {
        super(stationClass, em);
        this.em = em;
    }

    @Override
    public List<Station> searchBy(String text, int limit, String... fields) {

        SearchSession searchSession = Search.session(em.unwrap(Session.class));
        SearchResult<Station> result = null;

        result = searchSession.search(getDomainClass())
                    .where(search -> search.match()
                                            .fields(fields)
                                            .matching(text)
                                            .fuzzy(1))
                    .fetch(limit);

        return result.hits();

    }

}

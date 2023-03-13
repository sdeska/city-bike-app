package fi.sdeska.citybike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface StationSearchRepository<Station, ID> extends JpaRepository<Station, ID> {

    List<Station> searchBy(String text, int limit, String... fields);

}

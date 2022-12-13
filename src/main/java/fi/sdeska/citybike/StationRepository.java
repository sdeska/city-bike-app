package fi.sdeska.citybike;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends CrudRepository<Station, Long> {
    
}

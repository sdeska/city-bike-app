package fi.sdeska.citybike.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface encapsulates the storage, retrieval and search functions concerning stations and the database.
 */
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    
}

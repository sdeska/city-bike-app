package fi.sdeska.citybike.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface encapsulates the storage, retrieval and search functions concerning journeys and the database.
 */
@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {
    
}

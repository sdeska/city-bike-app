package fi.sdeska.citybike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.sdeska.citybike.entity.Journey;

/**
 * This interface encapsulates the storage, retrieval and search functions concerning journeys and the database.
 */
@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long>, CustomJourneyRepository {
    
}

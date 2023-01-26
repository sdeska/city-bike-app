package fi.sdeska.citybike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.sdeska.citybike.entity.Station;

/**
 * This interface encapsulates the storage, retrieval and search functions concerning stations and the database.
 */
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    
}

package fi.sdeska.citybike.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fi.sdeska.citybike.data.Journey;

public interface JourneyService {
    
    Journey saveJourney(Journey journey);
    Page<Journey> fetchAllJourneys(Pageable pageable);
    Journey updateJourney(Journey journey, Long id);
    void deleteJourneyById(Long id);
    Journey fetchJourneyById(Long id);
    Page<Journey> fetchPaginated(Pageable pageable);

}

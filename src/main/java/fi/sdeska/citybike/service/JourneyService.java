package fi.sdeska.citybike.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fi.sdeska.citybike.data.Journey;

public interface JourneyService {
    
    Journey saveJourney(Journey journey);
    List<Journey> fetchAllJourneys();
    Journey updateJourney(Journey journey, Long id);
    void deleteJourneyById(Long id);
    Journey fetchJourneyById(Long id);
    Page<Journey> findPaginated(Pageable pageable);

}

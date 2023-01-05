package fi.sdeska.citybike.service;

import java.util.List;

import fi.sdeska.citybike.data.Journey;

public interface JourneyService {
    
    Journey saveJourney(Journey journey);
    List<Journey> fetchAllJourneys();
    Journey updateJourney(Journey journey, Long id);
    void deleteJourneyById(Long id);

}

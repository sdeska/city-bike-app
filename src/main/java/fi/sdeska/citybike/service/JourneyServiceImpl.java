package fi.sdeska.citybike.service;

import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.iotanalytics.model.ResourceNotFoundException;

import fi.sdeska.citybike.data.Journey;
import fi.sdeska.citybike.data.JourneyRepository;

/**
 * This class handles any interactions with journeys in the database.
 */
@Component
public class JourneyServiceImpl implements JourneyService {

    @Autowired
    private JourneyRepository journeys;

    @Override
    public Journey saveJourney(Journey journey) {
        return journeys.save(journey);
    }

    @Override
    public List<Journey> fetchAllJourneys() {
        return (List<Journey>) journeys.findAll();
    }

    @Override
    public Journey updateJourney(Journey journey, Long id) {
        throw new NotYetImplementedException("JourneyServiceImpl.updateJourney() not implemented yet.");
    }

    @Override
    public void deleteJourneyById(Long id) {
        journeys.deleteById(id);
    }

    @Override
    public Journey fetchJourneyById(Long id) {

        var foundJourney = journeys.findById(id);
        if (foundJourney.isEmpty()) {
            throw new ResourceNotFoundException("Journey with the given ID does not exist.");
        }
        return foundJourney.get();

    }
    
}

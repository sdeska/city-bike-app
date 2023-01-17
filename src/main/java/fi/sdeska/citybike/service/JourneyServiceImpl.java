package fi.sdeska.citybike.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return journeys.findAll();
    }

    @Override
    public Journey updateJourney(Journey journey, Long id) {
        
        Optional<Journey> savedJourney = journeys.findById(id);
        if (!savedJourney.isPresent()) {
            throw new ResourceNotFoundException("Journey with the given ID does not exist.");
        }
        return journeys.save(journey);

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

    @Override
    public Page<Journey> fetchPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentIndex = pageable.getPageNumber();
        int startIndex = currentIndex * pageSize;
        var journeysList = journeys.findAll(pageable).getContent();
        
        List<Journey> pageContents = null;
        if (journeysList.size() < startIndex) {
            pageContents = Collections.emptyList();
        }
        else {
            int lastIndex = Math.min(startIndex + pageSize, journeysList.size());
            pageContents = journeysList.subList(startIndex, lastIndex);
        }

        return new PageImpl<>(pageContents, PageRequest.of(currentIndex, pageSize), journeysList.size());

    }
    
}

package fi.sdeska.citybike.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import fi.sdeska.citybike.entity.Journey;
import fi.sdeska.citybike.repository.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * This class implements the JourneyService interface.
 */
@Component
public class JourneyServiceImpl implements JourneyService {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private JourneyRepository journeys;

    @Override
    public Journey saveJourney(@NonNull Journey journey) {
        
        Optional<Journey> savedJourney = journeys.findByKey(journey);
        if (savedJourney.isPresent()) {
            LOG.info("Journey already exists. Skipping line with ID: " + journey.getId());
            return null;
        }
        return journeys.save(journey);

    }

    @Override
    public List<Journey> fetchAllJourneys() {
        return journeys.findAll();
    }

    /**
     * @throws EntityNotFoundException when a journey with the given ID cannot be found from the database.
     */
    @Override
    public Journey updateJourney(Journey journey, @NonNull Long id) {
        
        Optional<Journey> savedJourney = journeys.findById(id);
        if (!savedJourney.isPresent()) {
            throw new EntityNotFoundException("Journey with the given ID does not exist.");
        }
        return journeys.save(journey);

    }

    @Override
    public void deleteJourneyById(Long id) {
        journeys.deleteById(id);
    }

    /**
     * @throws EntityNotFoundException when a journey with the given ID cannot be found from the database.
     */
    @Override
    public Journey fetchJourneyById(Long id) {

        var foundJourney = journeys.findById(id);
        if (foundJourney.isEmpty()) {
            throw new EntityNotFoundException("Journey with the given ID does not exist.");
        }
        return foundJourney.get();

    }

    @Override
    public Page<Journey> fetchPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentIndex = pageable.getPageNumber();
        int startIndex = currentIndex * pageSize;

        var journeyPage = journeys.findAll(pageable);
        var totalJourneys = journeyPage.getTotalElements();
        
        List<Journey> pageContents = null;
        if (totalJourneys < startIndex) {
            pageContents = Collections.emptyList();
        }
        else {
            pageContents = journeyPage.getContent();
        }

        return new PageImpl<>(pageContents, PageRequest.of(currentIndex, pageSize, pageable.getSort()), totalJourneys);

    }
    
}

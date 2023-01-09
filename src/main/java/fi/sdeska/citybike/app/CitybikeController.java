package fi.sdeska.citybike.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fi.sdeska.citybike.data.Journey;
import fi.sdeska.citybike.data.Station;
import fi.sdeska.citybike.service.JourneyService;
import fi.sdeska.citybike.service.StationService;

@Controller
public class CitybikeController {

    @Autowired
    private StationService stationService;
    @Autowired
    private JourneyService journeyService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getAllStations() {
        return new ResponseEntity<>(stationService.fetchAllStations(), HttpStatus.OK);
    }

    @GetMapping("/journeys")
    public ResponseEntity<List<Journey>> getAllJourneys() {
        return new ResponseEntity<>(journeyService.fetchAllJourneys(), HttpStatus.OK);
    }

    @GetMapping("/station/{id}")
    public ResponseEntity<Station> getStationById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(stationService.fetchStationById(id), HttpStatus.OK);
    }

    @GetMapping("/journey/{id}")
    public ResponseEntity<Journey> getJourneyById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(journeyService.fetchJourneyById(id), HttpStatus.OK);
    }

}

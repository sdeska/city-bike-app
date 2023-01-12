package fi.sdeska.citybike.app;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getStations(Model model,
                              @RequestParam(required = false) Optional<Integer> page,
                              @RequestParam(required = false) Optional<Integer> size) {

        int currentIndex = page.orElse(1);
        int pageSize = size.orElse(100);
        
        Page<Station> stationPage = stationService.findPaginated(PageRequest.of(currentIndex - 1, pageSize));
        model.addAttribute("stationPage", stationPage);

        int totalPages = stationPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "stationsPage";

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

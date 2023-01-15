package fi.sdeska.citybike.app;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "100") int size,
                              @RequestParam(defaultValue = "id,asc") String[] sort) {

        var sortField = sort[0];
        var sortOrder = sort[1];

        Direction direction = sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        var order = new Order(direction, sortField);
        
        Page<Station> stationPage = stationService.fetchPaginated(PageRequest.of(page - 1, size, Sort.by(order)));
        model.addAttribute("stationPage", stationPage);

        int totalPages = stationPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "stationsPage";

    }

    @GetMapping("/journeys")
    public String getJourneys(Model model,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "100") int size,
                              @RequestParam(defaultValue = "id,asc") String[] sort) {
        
        var sortField = sort[0];
        var sortOrder = sort[1];

        Direction direction = sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        var order = new Order(direction, sortField);

        Page<Journey> journeyPage = journeyService.fetchPaginated(PageRequest.of(page - 1, size, Sort.by(order)));
        model.addAttribute("journeyPage", journeyPage);

        int totalPages = journeyPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "journeysPage";

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

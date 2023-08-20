package fi.sdeska.citybike.rest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fi.sdeska.citybike.entity.Journey;
import fi.sdeska.citybike.entity.Station;
import fi.sdeska.citybike.service.JourneyService;
import fi.sdeska.citybike.service.StationService;

/**
 * This class performs the role of a controller. Any requests sent by users' browsers are handled here.
 */
@Controller
public class CitybikeRestController {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private StationService stationService;
    @Autowired
    private JourneyService journeyService;
    @Autowired
    private RestService service;

    /**
     * Answers the GET request with the homepage index.html.
     * @return the page found in index.html.
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Gets a paginated list of stations according to the parameters provided through the URI.
     * @param model for holding model attributes.
     * @param page the requested page number of stations.
     * @param size the requested size for a page.
     * @param sortField the field according to which to order the stations.
     * @param sortOrder the order in which to sort the journeys. Can be either asc for ascending or desc for descending.
     * @return the page stationsPage.html that contains the table of stations.
     */
    @GetMapping("/stations")
    public String getStations(Model model,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "100") int size,
                            @RequestParam(defaultValue = "id") String sortField,
                            @RequestParam(defaultValue = "asc") String sortOrder,
                            @RequestParam(defaultValue = "") String keyword) {

        Direction direction = sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Order(direction, sortField);
        
        Page<Station> stationPage = stationService.fetchPaginated(PageRequest.of(page - 1, size, Sort.by(order)), keyword);
        model.addAttribute("stationPage", stationPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("reverseSortingDirection", sortOrder.equals("asc") ? "desc" : "asc");

        int totalPages = stationPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "stationsPage";

    }

    /**
     * Gets a paginated list of journeys according to the parameters provided in the URI.
     * @param model for holding model attributes.
     * @param page the requested page number of stations.
     * @param size the requested size for a page.
     * @param sortField the field according to which to order the journeys.
     * @param sortOrder the order in which to sort the journeys. Can be either asc for ascending or desc for descending.
     * @return the page journeysPage.html that contains the table of journeys.
     */
    @GetMapping("/journeys")
    public String getJourneys(Model model,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "100") int size,
                            @RequestParam(defaultValue = "id") String sortField,
                            @RequestParam(defaultValue = "asc") String sortOrder) {

        Direction direction = sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Order(direction, sortField);

        Page<Journey> journeyPage = journeyService.fetchPaginated(PageRequest.of(page - 1, size, Sort.by(order)));
        model.addAttribute("journeyPage", journeyPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("reverseSortingDirection", sortOrder.equals("asc") ? "desc" : "asc");

        int totalPages = journeyPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "journeysPage";

    }

    /**
     * Responds with a station from the database according to the given ID.
     * @param id the ID with which to search for a station.
     * @return the response containing the information about the station.
     */
    @GetMapping("/station")
    public String getStationById(Model model,
                                @RequestParam(defaultValue = "1") long id) {

        Station station = stationService.fetchStationById(id);
        Long journeysStartingHere = stationService.getNumberOfJourneysStartingHere(id);
        Long journeysEndingHere = stationService.getNumberOfJourneysEndingHere(id);
        model.addAttribute("station", station);
        model.addAttribute("journeysStartingHere", journeysStartingHere);
        model.addAttribute("journeysEndingHere", journeysEndingHere);

        return "station";

    }

    /**
     * Responds with a journey from the database according to the given ID.
     * @param id the ID with which to search for a journey.
     * @return the response containing the information about the journey.
     */
    @GetMapping("/journey")
    public String getJourneyById(Model model,
                                @RequestParam long id) {

        Journey journey = journeyService.fetchJourneyById(id);
        model.addAttribute("journey", journey);
        service.getJourneyMap(model, journey);

        return "journey";

    }

    /**
     * Responds with a page containing a static map for a journey the endpoints of which are given as station IDs.
     * @param model for injecting attributes into the Thymeleaf page.
     * @param station1 ID for the start station.
     * @param station2 ID for the end station.
     * @return the page map.html that contains the map.
     */
    @GetMapping("/map")
    public String getMap(Model model) {

        LOG.info("Processing embedded map request");
        var uri = new StringBuilder("https://www.mapquest.com/embed?q=&maptype=map");
        model.addAttribute("map", uri.toString());

        return "map";

    }

    /**
     * Mandatory 418.
     * @return
     */
    @GetMapping("/coffee")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT)
    public String coffee() {
        
        return "418 I'm a teapot";

    }

}

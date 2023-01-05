package fi.sdeska.citybike.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.sdeska.citybike.service.JourneyService;
import fi.sdeska.citybike.service.StationService;

@RestController
@RequestMapping
public class CitybikeController {

    @Autowired
    private StationService stationService;
    @Autowired
    private JourneyService journeyService;

    @RequestMapping("/")
    public String home() {
        return "No place like home";
    }

}

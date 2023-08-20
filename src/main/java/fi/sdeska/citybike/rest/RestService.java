package fi.sdeska.citybike.rest;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseStatus;

import fi.sdeska.citybike.entity.Journey;
import fi.sdeska.citybike.entity.Station;
import fi.sdeska.citybike.helper.Point2D;
import fi.sdeska.citybike.service.StationService;

@Service
public class RestService {

    private static final Logger LOG = LogManager.getLogger();
    
    @Autowired
    private StationService stationService;

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Fetching the map failed")
    public void onMapRequestFailed(Exception e) {}

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid arguments")
    public void onMapGetFailed() {}

    public void getJourneyMap(Model model, Journey journey) {

        LOG.info("Processing map request");
        
        var s1 = stationService.fetchStationById(journey.getDepartureStationID());
        var s2 = stationService.fetchStationById(journey.getReturnStationID());

        // Move this to a config file or something.
        var key = "Ok4DHGDtiGlEM7nF6gLfySOBpUg25Gyk";
        
        model.addAttribute("map", 
                            String.format("https://www.mapquestapi.com/staticmap/v5/map" +
                                        "?key=%s" +
                                        "&start=%f,%f" +
                                        "&end=%f,%f" +
                                        "&size=600,600" +
                                        "&margin=50" +
                                        "&scalebar=true|bottom", 
                                        key, s1.getY(), s1.getX(), s2.getY(), s2.getX()));

    }

    public Point2D getCenterOfStations(Station... stations) {

        var points = new ArrayList<Point2D>(stations.length);
        for (var station : stations) {
            points.add(new Point2D(station.getX(), station.getY()));
        }
        var centerX = 0D;
        var centerY = 0D;
        for (var point : points) {
            centerX += point.getX();
            centerY += point.getY();
        }
        return new Point2D(centerX / stations.length, centerY / stations.length);

    }

}

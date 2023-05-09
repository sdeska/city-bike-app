package fi.sdeska.citybike.rest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import fi.sdeska.citybike.entity.Station;
import fi.sdeska.citybike.helper.Point2D;
import fi.sdeska.citybike.service.StationService;

@Service
public class RestService {
    
    @Autowired
    private StationService stationService;

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Fetching the map failed")
    public void onMapRequestFailed(Exception e) {}

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid arguments")
    public void onMapGetFailed() {}

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

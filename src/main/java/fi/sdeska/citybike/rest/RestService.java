package fi.sdeska.citybike.rest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import fi.sdeska.citybike.service.StationService;

@Service
public class RestService {
    
    @Autowired
    private StationService stationService;

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Fetching the map failed")
    public void onMapRequestFailed(Exception e) {}

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid arguments")
    public void onMapGetFailed() {}

    public Point getCenterOfStations(long... ids) {

        var points = new ArrayList<Point>(ids.length);
        for (var id : ids) {
            var station = stationService.fetchStationById(id);
            points.add(new Point(station.getX(), station.getY()));
        }
        var centerX = 0L;
        var centerY = 0L;
        for (var point : points) {
            centerX += point.getX();
            centerY += point.getY();
        }
        return new Point(centerX / ids.length, centerY / ids.length);

    }

}

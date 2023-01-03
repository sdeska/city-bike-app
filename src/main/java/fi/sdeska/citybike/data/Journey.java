package fi.sdeska.citybike.data;

import org.joda.time.DateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "journeys", schema = "public")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private DateTime departureDate;
    private DateTime returnDate;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Station.class)
    @JoinColumn(name = "departure_station_id", referencedColumnName = "station_id")
    private int departureStationID;
    private String departureStationName;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Station.class)
    @JoinColumn(name = "return_station_id", referencedColumnName = "station_id")
    private int returnStationID;
    private String returnStationName;

    private int distance;
    private int duration;

    public Journey setId(int id) {
        this.id = id;
        return this;
    }

    public Journey setDepartureDate(DateTime departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public Journey setReturnDate(DateTime returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public Journey setDepartureStationID(int departureStationID) {
        this.departureStationID = departureStationID;
        return this;
    }

    public Journey setDepartureStationName(String departureStationName) {
        this.departureStationName = departureStationName;
        return this;
    }

    public Journey setReturnStationID(int returnStationID) {
        this.returnStationID = returnStationID;
        return this;
    }

    public Journey setReturnStationName(String returnStationName) {
        this.returnStationName = returnStationName;
        return this;
    }

    public Journey setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public Journey setDuration(int duration) {
        this.duration = duration;
        return this;
    }
    
}

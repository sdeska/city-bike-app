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

@Entity
@Table(name = "journeys")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private DateTime departureDate;
    private DateTime returnDate;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Station.class)
    @JoinColumn(name = "station_id")
    private int departureStationID;
    private String departureStationName;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Station.class)
    @JoinColumn(name = "station_id")
    private int returnStationID;
    private String returnStationName;

    private int distance;
    private int duration;

    public int getId() {
        return id;
    }

    public Journey setId(int id) {
        this.id = id;
        return this;
    }

    public DateTime getDepartureDate() {
        return departureDate;
    }

    public Journey setDepartureDate(DateTime departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public DateTime getReturnDate() {
        return returnDate;
    }

    public Journey setReturnDate(DateTime returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public int getDepartureStationID() {
        return departureStationID;
    }

    public Journey setDepartureStationID(int departureStationID) {
        this.departureStationID = departureStationID;
        return this;
    }

    public String getDepartureStationName() {
        return departureStationName;
    }

    public Journey setDepartureStationName(String departureStationName) {
        this.departureStationName = departureStationName;
        return this;
    }

    public int getReturnStationID() {
        return returnStationID;
    }

    public Journey setReturnStationID(int returnStationID) {
        this.returnStationID = returnStationID;
        return this;
    }

    public String getReturnStationName() {
        return returnStationName;
    }

    public Journey setReturnStationName(String returnStationName) {
        this.returnStationName = returnStationName;
        return this;
    }

    public int getDistance() {
        return distance;
    }

    public Journey setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public Journey setDuration(int duration) {
        this.duration = duration;
        return this;
    }
    
}

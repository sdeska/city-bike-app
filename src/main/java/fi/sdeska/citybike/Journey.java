package fi.sdeska.citybike;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "journeys")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date departureDate;
    private Date returnDate;
    private int departureStationID;
    private String departureStationName;
    private int returnStationID;
    private String returnStationName;
    private int distance;
    private int duration;

    Journey(Date dep, Date ret, int depSID, String depSName, int retSID, String retSName, int dist, int dur) {

        departureDate = dep;
        returnDate = ret;
        departureStationID = depSID;
        departureStationName = depSName;
        returnStationID = retSID;
        returnStationName = retSName;
        distance = dist;
        duration = dur;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getDepartureStationID() {
        return departureStationID;
    }

    public void setDepartureStationID(int departureStationID) {
        this.departureStationID = departureStationID;
    }

    public String getDepartureStationName() {
        return departureStationName;
    }

    public void setDepartureStationName(String departureStationName) {
        this.departureStationName = departureStationName;
    }

    public int getReturnStationID() {
        return returnStationID;
    }

    public void setReturnStationID(int returnStationID) {
        this.returnStationID = returnStationID;
    }

    public String getReturnStationName() {
        return returnStationName;
    }

    public void setReturnStationName(String returnStationName) {
        this.returnStationName = returnStationName;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
}

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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

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
    
}

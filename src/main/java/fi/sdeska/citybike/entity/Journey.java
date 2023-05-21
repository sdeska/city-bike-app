package fi.sdeska.citybike.entity;

import org.joda.time.DateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This entity class represents the 'journeys' table in the database. 
 * Each instance of this class represents a row in that database.
 * Getters, setters, constructors and a builder are auto-generated.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "journeys", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
    "departureDate", "returnDate", "departureStationID", "returnStationID", "distance", "duration"}))
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private DateTime departureDate;
    private DateTime returnDate;

    @JoinColumn(name = "departure_station_id", referencedColumnName = "station_id")
    private Long departureStationID;
    private String departureStationName;

    @JoinColumn(name = "return_station_id", referencedColumnName = "station_id")
    private Long returnStationID;
    private String returnStationName;

    private Long distance;
    private Long duration;

}

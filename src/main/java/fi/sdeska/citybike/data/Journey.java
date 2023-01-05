package fi.sdeska.citybike.data;

import org.joda.time.DateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "journeys", schema = "public")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private DateTime departureDate;
    private DateTime returnDate;

    @JoinColumn(name = "departure_station_id", referencedColumnName = "station_id")
    private Integer departureStationID;
    private String departureStationName;

    @JoinColumn(name = "return_station_id", referencedColumnName = "station_id")
    private Integer returnStationID;
    private String returnStationName;

    private Integer distance;
    private Integer duration;

}

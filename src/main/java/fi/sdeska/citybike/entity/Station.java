package fi.sdeska.citybike.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This entity class represents the 'stations' table in the database. 
 * Each instance of this class represents a row in that database.
 * Getters, setters, constructors and a builder are auto-generated.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station", schema = "public")
public class Station {
    
    private Long fId;

    @Id
    private Long id;

    private String nameFin;
    private String nameSwe;
    private String nameEng;
    private String addressFin;
    private String addressSwe;
    private String cityFin;
    private String citySwe;
    private String operator;
    private Long capacity;
    private Double x;
    private Double y;

}

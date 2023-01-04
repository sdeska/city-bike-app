package fi.sdeska.citybike.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

@Entity
@Table(name = "stations", schema = "public")
public class Station {
    
    private int fId;

    @Id
    @Column(name = "station_id", unique = true)
    private int id;

    private String nameFin;
    private String nameSwe;
    private String nameEng;
    private String addressFin;
    private String addressSwe;
    private String cityFin;
    private String citySwe;
    private String operator;
    private int capacity;
    private double x;
    private double y;

}

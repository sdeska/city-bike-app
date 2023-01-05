package fi.sdeska.citybike.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "stations", schema = "public")
public class Station {
    
    private Integer fId;

    @Id
    @Column(name = "station_id", unique = true)
    private Integer id;

    private String nameFin;
    private String nameSwe;
    private String nameEng;
    private String addressFin;
    private String addressSwe;
    private String cityFin;
    private String citySwe;
    private String operator;
    private Integer capacity;
    private Double x;
    private Double y;

}

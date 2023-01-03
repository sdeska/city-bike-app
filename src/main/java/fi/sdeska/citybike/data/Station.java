package fi.sdeska.citybike.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
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

    public Station setFID(int fID) {
        this.fId = fID;
        return this;
    }

    public Station setID(int id) {
        this.id = id;
        return this;
    }

    public Station setNameFin(String nameFin) {
        this.nameFin = nameFin;
        return this;
    }

    public Station setNameSwe(String nameSwe) {
        this.nameSwe = nameSwe;
        return this;
    }

    public Station setNameEng(String nameEng) {
        this.nameEng = nameEng;
        return this;
    }

    public Station setAddressFin(String addressFin) {
        this.addressFin = addressFin;
        return this;
    }

    public Station setAddressSwe(String addressSwe) {
        this.addressSwe = addressSwe;
        return this;
    }

    public Station setCityFin(String cityFin) {
        this.cityFin = cityFin;
        return this;
    }

    public Station setCitySwe(String citySwe) {
        this.citySwe = citySwe;
        return this;
    }

    public Station setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public Station setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Station setX(double x) {
        this.x = x;
        return this;
    }

    public Station setY(double y) {
        this.y = y;
        return this;
    }

}

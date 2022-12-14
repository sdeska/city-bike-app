package fi.sdeska.citybike.data;

import java.awt.geom.Point2D;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stations")
public class Station {
    
    @Id
    private int fId;

    @Column(name = "station_id")
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
    private Point2D coords;

    public int getFID() {
        return fId;
    }

    public void setFID(int fID) {
        this.fId = fID;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNameFin() {
        return nameFin;
    }

    public void setNameFin(String nameFin) {
        this.nameFin = nameFin;
    }

    public String getNameSwe() {
        return nameSwe;
    }

    public void setNameSwe(String nameSwe) {
        this.nameSwe = nameSwe;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getAddressFin() {
        return addressFin;
    }

    public void setAddressFin(String addressFin) {
        this.addressFin = addressFin;
    }

    public String getAddressSwe() {
        return addressSwe;
    }

    public void setAddressSwe(String addressSwe) {
        this.addressSwe = addressSwe;
    }

    public String getCityFin() {
        return cityFin;
    }

    public void setCityFin(String cityFin) {
        this.cityFin = cityFin;
    }

    public String getCitySwe() {
        return citySwe;
    }

    public void setCitySwe(String citySwe) {
        this.citySwe = citySwe;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Point2D getCoords() {
        return coords;
    }

    public void setCoords(Point2D coords) {
        this.coords = coords;
    }

}

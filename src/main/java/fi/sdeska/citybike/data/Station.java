package fi.sdeska.citybike.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public int getFID() {
        return fId;
    }

    public Station setFID(int fID) {
        this.fId = fID;
        return this;
    }

    public int getID() {
        return id;
    }

    public Station setID(int id) {
        this.id = id;
        return this;
    }

    public String getNameFin() {
        return nameFin;
    }

    public Station setNameFin(String nameFin) {
        this.nameFin = nameFin;
        return this;
    }

    public String getNameSwe() {
        return nameSwe;
    }

    public Station setNameSwe(String nameSwe) {
        this.nameSwe = nameSwe;
        return this;
    }

    public String getNameEng() {
        return nameEng;
    }

    public Station setNameEng(String nameEng) {
        this.nameEng = nameEng;
        return this;
    }

    public String getAddressFin() {
        return addressFin;
    }

    public Station setAddressFin(String addressFin) {
        this.addressFin = addressFin;
        return this;
    }

    public String getAddressSwe() {
        return addressSwe;
    }

    public Station setAddressSwe(String addressSwe) {
        this.addressSwe = addressSwe;
        return this;
    }

    public String getCityFin() {
        return cityFin;
    }

    public Station setCityFin(String cityFin) {
        this.cityFin = cityFin;
        return this;
    }

    public String getCitySwe() {
        return citySwe;
    }

    public Station setCitySwe(String citySwe) {
        this.citySwe = citySwe;
        return this;
    }

    public String getOperator() {
        return operator;
    }

    public Station setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public Station setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public double getX() {
        return x;
    }

    public Station setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Station setY(double y) {
        this.y = y;
        return this;
    }

}

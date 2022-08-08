package com.innowise.cadenceapp.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="weather")
public class Weather {


    @Id
    @GenericGenerator(name="simple" , strategy="increment")
    @GeneratedValue(generator="simple")
    private  int id;
    private String city;
    private int temperature;
    private Timestamp timest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Timestamp getTime() {
        return timest;
    }

    public void setTime(Timestamp timest) {
        this.timest = timest;
    }

    public Weather() {

    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", temperature=" + temperature +
                ", time=" + timest +
                '}';
    }
}
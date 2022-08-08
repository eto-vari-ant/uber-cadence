package com.innowise.cadenceapp.activity;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import com.innowise.cadenceapp.dbconnection.ConnectionToDB;
import com.innowise.cadenceapp.model.Weather;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Service
public class WeatherActivityImpl implements WeatherActivity{

    private final String key = "c2fdfdab4a1ec0a66b3488d48c25a68c";

    private static int count = 0;


    @Override
    public Weather getWeather(String city) {
        try {
            OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(key);
            final com.github.prominence.openweathermap.api.model.weather.Weather weatherByLibrary = openWeatherClient
                    .currentWeather()
                    .single()
                    .byCityName(city)
                    .language(Language.RUSSIAN)
                    .unitSystem(UnitSystem.METRIC)
                    .retrieve()
                    .asJava();
            Weather weather = new Weather();
            weather.setId(count++);
            weather.setCity(city);
            weather.setTemperature((int) weatherByLibrary.getTemperature().getValue());
            weather.setTime(new Timestamp(System.currentTimeMillis()));
            return weather;
        } catch (NoDataFoundException e){
            System.out.println("The city is incorrect");
        }
        return null;
    }

    @Override
    public void save(Weather weather) {
        try(Connection conn = ConnectionToDB.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO weather (id, city, temperature, timest) VALUES (?,?,?,?)")){
            preparedStatement.setInt(1, weather.getId());
            preparedStatement.setString(2,weather.getCity());
            preparedStatement.setInt(3, weather.getTemperature());
            preparedStatement.setTimestamp(4, weather.getTime());
            if(preparedStatement.executeUpdate()==1){
                System.out.println("The "+ weather.getCity()+" was added");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

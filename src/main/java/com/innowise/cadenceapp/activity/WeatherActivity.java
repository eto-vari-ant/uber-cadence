package com.innowise.cadenceapp.activity;

import com.innowise.cadenceapp.model.Weather;
import com.uber.cadence.activity.ActivityMethod;

public interface WeatherActivity {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 200)
    Weather getWeather(String city);

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 200)
    void save(Weather weather);
}

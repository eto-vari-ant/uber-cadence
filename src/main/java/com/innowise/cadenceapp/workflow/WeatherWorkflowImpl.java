package com.innowise.cadenceapp.workflow;

import com.innowise.cadenceapp.activity.WeatherActivity;
import com.innowise.cadenceapp.model.Weather;
import com.uber.cadence.workflow.Workflow;

public class WeatherWorkflowImpl implements WeatherWorkflow {

    private final WeatherActivity activities = Workflow.newActivityStub(WeatherActivity.class);


    @Override
    public Weather getWeather(String city) {
        Weather weather = activities.getWeather(city);
        if (weather != null) {
            activities.save(weather);
            return weather;
        }
        return null;
    }


}
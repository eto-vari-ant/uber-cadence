package com.innowise.cadenceapp.controller;

import com.innowise.cadenceapp.model.Weather;
import com.innowise.cadenceapp.workflow.WeatherWorkflow;
import com.uber.cadence.client.WorkflowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WorkflowClient workflowClient;


    @GetMapping("/{city}")
    public ResponseEntity<String> getWeather(@PathVariable String city) {
        WeatherWorkflow workflow = workflowClient.newWorkflowStub(WeatherWorkflow.class);
        Weather weather = workflow.getWeather(city);
        if (weather != null) {
            return new ResponseEntity<>(weather.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The city is incorrect", HttpStatus.BAD_REQUEST);
    }

}
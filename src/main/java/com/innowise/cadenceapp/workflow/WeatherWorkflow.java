package com.innowise.cadenceapp.workflow;

import com.innowise.cadenceapp.model.Weather;
import com.uber.cadence.workflow.WorkflowMethod;

public interface WeatherWorkflow {

    String TASK_LIST = "WEATHER";


    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 600, taskList = TASK_LIST)
    Weather getWeather(String city);

}

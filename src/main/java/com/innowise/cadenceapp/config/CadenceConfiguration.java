package com.innowise.cadenceapp.config;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadence.worker.WorkerFactoryOptions;
import com.uber.cadence.worker.WorkerOptions;
import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CadenceConfiguration {

    static final String DOMAIN = "weather-domain";


    @Bean
    public WorkflowClientOptions workflowClientOptions() {
        return WorkflowClientOptions.newBuilder()
                .setDomain(DOMAIN)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(WorkflowClient.class)
    public WorkflowClient workflowClient(IWorkflowService workflowService, WorkflowClientOptions workflowClientOptions) {
        return WorkflowClient.newInstance(workflowService, workflowClientOptions);
    }

    @Bean
    @ConditionalOnMissingBean(ClientOptions.class)
    public ClientOptions cadenceClientOptions() {
        return ClientOptions.newBuilder()
                .setClientAppName("testApp")
                .setHost("localhost")
                .setPort(7933)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(IWorkflowService.class)
    public IWorkflowService cadenceClient(ClientOptions clientOptions) {
        return new WorkflowServiceTChannel(clientOptions);
    }

    @Bean
    public WorkerFactoryOptions workerFactoryOptions() {
        return WorkerFactoryOptions.newBuilder().build();
    }

    @Bean
    @ConditionalOnMissingBean(WorkerFactory.class)
    public WorkerFactory workerFactory(WorkflowClient workflowClient, WorkerFactoryOptions workerFactoryOptions) {
        return new WorkerFactory(workflowClient, workerFactoryOptions);
    }

    @Bean
    public WorkerOptions workerOptions() {
        return WorkerOptions.newBuilder().build();
    }


    @Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

}


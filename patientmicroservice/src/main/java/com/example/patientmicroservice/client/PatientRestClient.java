package com.example.patientmicroservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class PatientRestClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080/api/patients";

    @Autowired
    public PatientRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}

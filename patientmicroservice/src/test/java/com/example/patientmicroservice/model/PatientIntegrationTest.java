package com.example.patientmicroservice.model;

import com.example.patientmicroservice.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PatientIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddAndGetPatient() {
        String baseUrl = "http://localhost:8080/api/patients";

        Patient patient = new Patient("Test", "Person", LocalDate.of(1990, 1, 1), "0001112222", "test@example.com", "Other");

        Patient saved = restTemplate.postForObject(baseUrl, patient, Patient.class);

        assertThat(saved).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo("Test");

        Patient retrieved = restTemplate.getForObject(baseUrl + "/" + saved.getId(), Patient.class);

        assertThat(retrieved.getEmail()).isEqualTo("test@example.com");
    }
}

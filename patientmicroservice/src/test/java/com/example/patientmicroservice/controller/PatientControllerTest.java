package com.example.patientmicroservice.controller;

import com.example.patientmicroservice.client.PatientRestClient;
import com.example.patientmicroservice.model.Patient;
import com.example.patientmicroservice.service.PatientService;
import org.junit.jupiter.api.Test;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @MockitoBean
    private PatientRestClient patientRestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPatients() throws Exception {
        Patient p1 = new Patient("Alice", "Smith", LocalDate.of(1990, 1, 1), "1234567890", "alice@example.com", "Female");
        Patient p2 = new Patient("Bob", "Jones", LocalDate.of(1985, 2, 2), "9876543210", "bob@example.com", "Male");

        when(patientService.getAllPatients()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testAddPatient() throws Exception {
        Patient p = new Patient("Alice", "Smith", LocalDate.of(1990, 1, 1), "1234567890", "alice@example.com", "Female");

        when(patientService.addPatient(any(Patient.class))).thenReturn(p);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    public void testGetPatientById() throws Exception {
        Patient p = new Patient("Alice", "Smith", LocalDate.of(1990, 1, 1), "1234567890", "alice@example.com", "Female");
        String id = "abc123";
        p.setId(id);

        when(patientService.getPatientById(id)).thenReturn(Optional.of(p));

        mockMvc.perform(get("/api/patients/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        Patient updated = new Patient("Updated", "Smith", LocalDate.of(1990, 1, 1), "0000000000", "updated@example.com", "Female");
        String id = "abc123";
        updated.setId(id);

        when(patientService.updatePatient(eq(id), any(Patient.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/api/patients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactNumber").value("0000000000"));
    }

    @Test
    public void testDeletePatient() throws Exception {
        String id = "abc123";
        when(patientService.deletePatient(id)).thenReturn(true);

        mockMvc.perform(delete("/api/patients/{id}", id))
                .andExpect(status().isNoContent());
    }
}

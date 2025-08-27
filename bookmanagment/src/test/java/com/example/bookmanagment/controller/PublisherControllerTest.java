package com.example.bookmanagment.controller;

import com.example.bookmanagment.model.Publisher;
import com.example.bookmanagment.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublisherService publisherService;

    private Publisher publisher;

    @BeforeEach
    void setup() {
        publisher = new Publisher("Pub A", "Address A");
        publisher.setId(1L);
    }

    @Test
    void testListPublishers() throws Exception {
        when(publisherService.getAllPublishers()).thenReturn(Arrays.asList(publisher));

        mockMvc.perform(get("/publishers"))
                .andExpect(status().isOk())
                .andExpect(view().name("publishers/list"))
                .andExpect(model().attributeExists("publishers"));
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/publishers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("publishers/form"))
                .andExpect(model().attributeExists("publisher"));
    }

    @Test
    void testSavePublisher() throws Exception {
        when(publisherService.savePublisher(any(Publisher.class))).thenReturn(publisher);

        mockMvc.perform(post("/publishers")
                        .param("name", "Pub A")
                        .param("address", "Address A"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/publishers"));

        verify(publisherService).savePublisher(any(Publisher.class));
    }

    @Test
    void testEditPublisher() throws Exception {
        when(publisherService.getPublisherById(1L)).thenReturn(Optional.of(publisher));

        mockMvc.perform(get("/publishers/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("publishers/form"))
                .andExpect(model().attributeExists("publisher"));
    }

    @Test
    void testDeletePublisher() throws Exception {
        mockMvc.perform(get("/publishers/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/publishers"));

        verify(publisherService).deletePublisher(1L);
    }
}

package com.example.bookmanagment.service;

import com.example.bookmanagment.model.Publisher;
import com.example.bookmanagment.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PublisherServiceTest {

    private PublisherRepository publisherRepository;
    private PublisherService publisherService;

    @BeforeEach
    void setup() {
        publisherRepository = mock(PublisherRepository.class);
        publisherService = new PublisherService(publisherRepository);
    }

    @Test
    void testGetAllPublishers() {
        List<Publisher> publishers = Arrays.asList(
                new Publisher("Pub1", "Address1"),
                new Publisher("Pub2", "Address2")
        );
        when(publisherRepository.findAll()).thenReturn(publishers);

        List<Publisher> result = publisherService.getAllPublishers();
        assertEquals(2, result.size());
    }

    @Test
    void testGetPublisherById() {
        Publisher publisher = new Publisher("Pub1", "Address1");
        publisher.setId(1L);
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        Optional<Publisher> result = publisherService.getPublisherById(1L);
        assertTrue(result.isPresent());
        assertEquals("Pub1", result.get().getName());
    }

    @Test
    void testSavePublisher() {
        Publisher publisher = new Publisher("Pub1", "Address1");
        when(publisherRepository.save(publisher)).thenReturn(publisher);

        Publisher saved = publisherService.savePublisher(publisher);
        assertEquals("Pub1", saved.getName());
    }

    @Test
    void testDeletePublisher() {
        publisherService.deletePublisher(1L);
        verify(publisherRepository).deleteById(1L);
    }
}

package com.petmanager.service;

import com.petmanager.dao.PetDao;
import com.petmanager.dto.request.PetRequest;
import com.petmanager.dto.response.PetResponse;
import com.petmanager.entity.PetEntity;
import com.petmanager.enums.Species;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetServiceTest {

    @Mock
    private PetDao petDao;

    @InjectMocks
    private PetService petService;

    private AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    public void testSavePet() {
        PetRequest petRequest = new PetRequest();
        petRequest.setName("Fluffy");
        petRequest.setSpecies(Species.DOG);
        petRequest.setAge(3);
        petRequest.setOwnerName("Alice");

        PetEntity savedEntity = new PetEntity();
        savedEntity.setId(1L);
        savedEntity.setName("Fluffy");
        savedEntity.setSpecies(Species.DOG);
        savedEntity.setAge(3);
        savedEntity.setOwnerName("Alice");

        when(petDao.save(any(PetEntity.class))).thenReturn(savedEntity);

        PetEntity result = petService.save(petRequest);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Fluffy", result.getName());
        assertEquals(Species.DOG, result.getSpecies());
        assertEquals(3, result.getAge());
        assertEquals("Alice", result.getOwnerName());

        verify(petDao).save(any(PetEntity.class));
    }

    @Test
    public void testRemovePet() {
        when(petDao.deleteById(1L)).thenReturn(true);
        petService.removePet(1L);
        verify(petDao).deleteById(1L);
    }

    @Test
    public void testGetAllPets() {
        PetEntity pet1 = new PetEntity();
        pet1.setId(1L);
        pet1.setName("Pet1");
        pet1.setSpecies(Species.CAT);

        PetEntity pet2 = new PetEntity();
        pet2.setId(2L);
        pet2.setName("Pet2");
        pet2.setSpecies(Species.DOG);

        when(petDao.findAll()).thenReturn(Arrays.asList(pet1, pet2));

        List<PetResponse> responses = petService.getAllPets();
        assertEquals(2, responses.size());
        assertTrue(responses.stream().anyMatch(p -> p.getName().equals("Pet1") && p.getSpecies() == Species.CAT));
        assertTrue(responses.stream().anyMatch(p -> p.getName().equals("Pet2") && p.getSpecies() == Species.DOG));

        verify(petDao).findAll();
    }

    @Test
    public void testUpdatePet() {
        PetEntity existingPet = new PetEntity();
        existingPet.setId(1L);
        existingPet.setName("OldName");
        existingPet.setSpecies(Species.CAT);
        existingPet.setAge(2);
        existingPet.setOwnerName("Alice");

        PetRequest updateRequest = new PetRequest();
        updateRequest.setName("NewName");
        updateRequest.setSpecies(Species.DOG);
        updateRequest.setAge(5);
        updateRequest.setOwnerName("Alice");

        when(petDao.findById(1L)).thenReturn(Optional.of(existingPet));
        when(petDao.save(existingPet)).thenReturn(existingPet);

        PetEntity updated = petService.updatePet(1L, updateRequest);

        assertEquals("NewName", updated.getName());
        assertEquals(Species.DOG, updated.getSpecies());
        assertEquals(5, updated.getAge());
        assertEquals("Alice", updated.getOwnerName());

        verify(petDao).findById(1L);
        verify(petDao).save(existingPet);
    }
}

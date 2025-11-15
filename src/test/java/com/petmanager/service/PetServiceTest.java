package com.petmanager.service;

import com.petmanager.dao.PetDao;
import com.petmanager.dto.response.PetResponse;
import com.petmanager.entity.PetEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

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
        mocks.close(); // close mocks to apublic void resource leak
    }

    @Test
    public void testSavePet() {
        PetEntity pet = new PetEntity();
        pet.setName("Fluffy");

        PetEntity savedPet = new PetEntity();
        savedPet.setId(1L);
        savedPet.setName("Fluffy");

        when(petDao.save(pet)).thenReturn(savedPet);

        PetEntity result = petService.save(pet);
        assertEquals(1L, result.getId());
        assertEquals("Fluffy", result.getName());

        verify(petDao).save(pet);
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
        PetEntity pet2 = new PetEntity();
        pet2.setId(2L);
        pet2.setName("Pet2");

        when(petDao.findAll()).thenReturn(Arrays.asList(pet1, pet2));

        List<PetResponse> responses = petService.getAllPets();
        assertEquals(2, responses.size());
        assertTrue(responses.stream().anyMatch(p -> p.getName().equals("Pet1")));
        assertTrue(responses.stream().anyMatch(p -> p.getName().equals("Pet2")));

        verify(petDao).findAll();
    }

    @Test
    public void testRemovePetsByOwnerName() {
        PetEntity pet1 = new PetEntity();
        pet1.setId(1L);
        pet1.setOwnerName("Charlie");
        PetEntity pet2 = new PetEntity();
        pet2.setId(2L);
        pet2.setOwnerName("Charlie");
        PetEntity pet3 = new PetEntity();
        pet3.setId(3L);
        pet3.setOwnerName("Delta");

        when(petDao.findAll()).thenReturn(Arrays.asList(pet1, pet2, pet3));
        when(petDao.deleteById(anyLong())).thenReturn(true);

        petService.removePetsByOwnerName("Charlie");

        verify(petDao).deleteById(1L);
        verify(petDao).deleteById(2L);
        verify(petDao, never()).deleteById(3L);
    }
}

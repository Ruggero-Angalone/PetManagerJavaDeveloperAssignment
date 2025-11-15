package com.petmanager.service;

import com.petmanager.dto.request.OwnerRequest;
import com.petmanager.dto.request.PetAndOwnerRequest;
import com.petmanager.dto.request.PetRequest;
import com.petmanager.dto.response.OwnerResponse;
import com.petmanager.dto.response.PetAndOwnerResponse;
import com.petmanager.dto.response.PetResponse;
import com.petmanager.entity.OwnerEntity;
import com.petmanager.entity.PetEntity;
import com.petmanager.exception.AmbiguousOwnerException;
import com.petmanager.exception.OwnerHasPetsException;
import com.petmanager.exception.OwnerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OwnerAndPetServiceTest {

    @Autowired
    private OwnerAndPetService ownerAndPetService;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private PetService petService;

    @Test
    public void testRemoveOwnerAndPets() {
        OwnerEntity owner = new OwnerEntity();
        owner.setOwnerId(1L);
        owner.setOwnerName("Alice");
        when(ownerService.findById(1L)).thenReturn(Optional.of(owner));
        doNothing().when(petService).removePetsByOwnerName("Alice");
        when(ownerService.deleteById(1L)).thenReturn(true);
        ownerAndPetService.removeOwnerAndPets(1L);
        verify(petService).removePetsByOwnerName("Alice");
        verify(ownerService).deleteById(1L);
    }

    @Test
    public void testRemoveOwnerAndPetsOwnerNotFound() {
        when(ownerService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class,
                () -> ownerAndPetService.removeOwnerAndPets(1L));
    }

    @Test
    public void testAddPetSuccess() {
        PetRequest petRequest = new PetRequest();
        petRequest.setName("Fluffy");
        petRequest.setOwnerName("Alice");

        OwnerEntity owner = new OwnerEntity();
        owner.setOwnerName("Alice");

        when(ownerService.findByName("Alice")).thenReturn(Collections.singletonList(owner));

        PetEntity savedPet = new PetEntity();
        savedPet.setId(1L);
        savedPet.setName("Fluffy");
        savedPet.setOwnerName("Alice");

        when(petService.save(any(PetEntity.class))).thenReturn(savedPet);

        PetResponse response = ownerAndPetService.addPet(petRequest);

        assertEquals("Fluffy", response.getName());
        assertEquals("Alice", response.getOwnerName());
        verify(petService).save(any(PetEntity.class));
    }

    @Test
    public void testAddPetOwnerNotFound() {
        PetRequest petRequest = new PetRequest();
        petRequest.setOwnerName("Alice");

        when(ownerService.findByName("Alice")).thenReturn(Collections.emptyList());

        assertThrows(OwnerNotFoundException.class,
                () -> ownerAndPetService.addPet(petRequest));
    }

    @Test
    public void testAddPetAmbiguousOwner() {
        PetRequest petRequest = new PetRequest();
        petRequest.setOwnerName("Alice");

        when(ownerService.findByName("Alice")).thenReturn(
                List.of(new OwnerEntity(), new OwnerEntity())
        );

        assertThrows(AmbiguousOwnerException.class,
                () -> ownerAndPetService.addPet(petRequest));
    }

    @Test
    public void testAddPetAndOwner() {
        PetAndOwnerRequest request = new PetAndOwnerRequest();
        request.setOwnerName("Bob");
        request.setPhoneNumber("123");
        request.setAddress("Street 1");
        request.setName("Max");
        request.setSpecies("Dog");
        request.setAge(4);

        OwnerResponse savedOwner = new OwnerResponse();
        savedOwner.setOwnerName("Bob");

        when(ownerService.addOwner(any(OwnerRequest.class))).thenReturn(savedOwner);

        PetEntity savedPet = new PetEntity();
        savedPet.setId(1L);
        savedPet.setName("Max");
        savedPet.setOwnerName("Bob");

        when(petService.save(any(PetEntity.class))).thenReturn(savedPet);

        PetAndOwnerResponse response = ownerAndPetService.addPetAndOwner(request);

        assertEquals("Max", response.getPet().getName());
        assertEquals("Bob", response.getOwner().getOwnerName());
        verify(ownerService).addOwner(any(OwnerRequest.class));
        verify(petService).save(any(PetEntity.class));
    }

    @Test
    public void testRemoveOwnerSuccess() {
        OwnerEntity owner = new OwnerEntity();
        owner.setOwnerId(1L);
        owner.setOwnerName("Alice");

        when(ownerService.findById(1L)).thenReturn(Optional.of(owner));
        when(petService.getAllPets()).thenReturn(Collections.emptyList());
        when(ownerService.deleteById(1L)).thenReturn(true);
        ownerAndPetService.removeOwner(1L);

        verify(ownerService).deleteById(1L);
    }

    @Test
    public void testRemoveOwnerThrowsOwnerHasPetsException() {
        OwnerEntity owner = new OwnerEntity();
        owner.setOwnerName("Alice");

        when(ownerService.findById(1L)).thenReturn(Optional.of(owner));

        PetResponse pet = new PetResponse();
        pet.setOwnerName("Alice");

        when(petService.getAllPets()).thenReturn(Collections.singletonList(pet));

        assertThrows(OwnerHasPetsException.class,
                () -> ownerAndPetService.removeOwner(1L));
    }

    @Test
    public void testRemoveOwnerOwnerNotFound() {
        when(ownerService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class,
                () -> ownerAndPetService.removeOwner(1L));
    }
}

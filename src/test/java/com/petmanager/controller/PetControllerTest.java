package com.petmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petmanager.dto.request.PetRequest;
import com.petmanager.dto.response.PetResponse;
import com.petmanager.entity.PetEntity;
import com.petmanager.enums.Species;
import com.petmanager.service.PetService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    // ------------------------------------------------------------------
    // POST /api/pets
    // ------------------------------------------------------------------
    @Test
    public void testSavePet() throws Exception {
        PetRequest request = new PetRequest();
        request.setName("Charlie");
        request.setSpecies(Species.DOG);
        request.setAge(3);
        request.setOwnerName("Alice");

        PetEntity saved = new PetEntity();
        saved.setId(1L);
        saved.setName("Charlie");
        saved.setSpecies(Species.DOG);
        saved.setAge(3);
        saved.setOwnerName("Alice");

        Mockito.when(petService.save(any(PetRequest.class))).thenReturn(saved);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.species").value("DOG"))
                .andExpect(jsonPath("$.ownerName").value("Alice"));
    }

    // ------------------------------------------------------------------
    // DELETE /api/pets/{id}
    // ------------------------------------------------------------------
    @Test
    public void testRemovePet() throws Exception {
        // Service returns true when deletion succeeds
        Mockito.when(petService.removePet(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/pets/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(petService).removePet(1L);
    }

    // ------------------------------------------------------------------
    // GET /api/pets
    // ------------------------------------------------------------------
    @Test
    public void testGetAllPets() throws Exception {

        PetResponse p1 = new PetResponse();
        p1.setId(1L);
        p1.setName("Milo");
        p1.setSpecies(Species.CAT);
        p1.setAge(2);
        p1.setOwnerName("John");

        PetResponse p2 = new PetResponse();
        p2.setId(2L);
        p2.setName("Bella");
        p2.setSpecies(Species.DOG);
        p2.setAge(4);
        p2.setOwnerName("Anna");

        List<PetResponse> list = Arrays.asList(p1, p2);

        Mockito.when(petService.getAllPets()).thenReturn(list);

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Milo"))
                .andExpect(jsonPath("$[1].species").value("DOG"));
    }

    // ------------------------------------------------------------------
    // GET /api/pets/{id}
    // ------------------------------------------------------------------
    @Test
    public void testGetPetById() throws Exception {

        PetEntity pet = new PetEntity();
        pet.setId(1L);
        pet.setName("Rocky");
        pet.setSpecies(Species.RABBIT);
        pet.setAge(1);
        pet.setOwnerName("Sarah");

        Mockito.when(petService.getPetById(1L)).thenReturn(pet);

        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rocky"))
                .andExpect(jsonPath("$.species").value("RABBIT"));
    }

    // ------------------------------------------------------------------
    // PUT /api/pets/{id}
    // ------------------------------------------------------------------
    @Test
    public void testUpdatePet() throws Exception {

        PetRequest update = new PetRequest();
        update.setName("Rocky");
        update.setSpecies(Species.RABBIT);
        update.setAge(2);
        update.setOwnerName("Sarah");

        PetEntity updated = new PetEntity();
        updated.setId(1L);
        updated.setName("Rocky");
        updated.setSpecies(Species.RABBIT);
        updated.setAge(2);
        updated.setOwnerName("Sarah");

        Mockito.when(petService.updatePet(eq(1L), any(PetRequest.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(2));
    }
}
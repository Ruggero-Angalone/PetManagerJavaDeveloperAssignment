package com.petmanager.controller;

import com.petmanager.dto.request.PetRequest;
import com.petmanager.dto.response.PetResponse;
import com.petmanager.entity.PetEntity;
import com.petmanager.service.PetService;
import jakarta.validation.Valid;
import com.petmanager.mapper.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping("")
    public ResponseEntity<PetResponse> save(@RequestBody @Valid PetRequest pet){
        return ResponseEntity.ok(PetMapper.toResponse(petService.save(pet)));
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> removePet(@PathVariable Long petId){
        petService.removePet(petId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<List<PetResponse>> getAllPets(){
        return ResponseEntity.ok(petService.getAllPets());
    }
    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long petId){
        return ResponseEntity.ok(PetMapper.toResponse(petService.getPetById(petId)));
    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetResponse> updatePet(
            @PathVariable Long petId,
            @RequestBody @Valid PetRequest petRequest) {
        PetEntity updatedPet = petService.updatePet(petId, petRequest);
        return ResponseEntity.ok(PetMapper.toResponse(updatedPet));
    }
}

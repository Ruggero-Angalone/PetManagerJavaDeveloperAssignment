package com.petmanager.controller;

import com.petmanager.dto.request.PetAndOwnerRequest;
import com.petmanager.dto.request.PetRequest;
import com.petmanager.dto.response.PetAndOwnerResponse;
import com.petmanager.dto.response.PetResponse;
import com.petmanager.service.OwnerAndPetService;
import com.petmanager.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    PetService petService;
    @Autowired
    OwnerAndPetService ownerAndPetService;

    @PostMapping("addPet")
    public ResponseEntity<PetResponse> addPet(@RequestBody PetRequest pet){
        return ResponseEntity.ok(ownerAndPetService.addPet(pet));
    }

    @PostMapping("addPetAndOwner")
    public ResponseEntity<PetAndOwnerResponse> addPetAndOwner(@RequestBody PetAndOwnerRequest petOwner){
        return ResponseEntity.ok(ownerAndPetService.addPetAndOwner(petOwner));
    }

    @DeleteMapping("removePet/{petId}")
    public ResponseEntity<Void> removePet(@PathVariable Long petId){
        return ResponseEntity.ok(petService.removePet(petId));
    }

    @GetMapping("getAllPets")
    public ResponseEntity<List<PetResponse>> getAllPets(){
        return ResponseEntity.ok(petService.getAllPets());
    }
}

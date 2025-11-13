package com.petmanager.controller;

import com.petmanager.entity.PetEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/controllers")
public class OwnersController {
    @PostMapping("addOwners")
    public ResponseEntity<PetEntity> addPet(@RequestBody PetEntity pet){
        return ResponseEntity.ok(pet);
    }

    @PostMapping("removeOwners")
    public ResponseEntity<String> removePet(@RequestParam Long id){
        return ResponseEntity.ok("removed pet with " + id);
    }

    @PostMapping("purgeOwners")
    public ResponseEntity<String> purgePets(){
        return ResponseEntity.ok("purged all pets.");
    }
}

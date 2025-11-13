package com.petmanager.controller;

import com.petmanager.entity.PetEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @PostMapping("addPet")
    public ResponseEntity<PetEntity> addPet(@RequestBody PetEntity pet){
        return ResponseEntity.ok(pet);
    }

    @PostMapping("removePet")
    public ResponseEntity<String> removePet(@RequestParam Long id){
        return ResponseEntity.ok("removed pet with " + id);
    }

    @PostMapping("purgePets")
    public ResponseEntity<String> purgePets(){
        return ResponseEntity.ok("purged all pets.");
    }
}

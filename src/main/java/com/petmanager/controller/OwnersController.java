package com.petmanager.controller;

import com.petmanager.dto.request.OwnerRequest;
import com.petmanager.dto.response.OwnerResponse;
import com.petmanager.entity.PetEntity;
import com.petmanager.service.OwnerAndPetService;
import com.petmanager.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/controllers")
public class OwnersController {

    @Autowired
    private OwnerService ownerService;
    @Autowired
    private OwnerAndPetService ownerAndPetService;

    @PostMapping("addOwner")
    public ResponseEntity<OwnerResponse> addOwner(@RequestBody OwnerRequest owner){

        return ResponseEntity.ok(ownerService.addOwner(owner));
    }

    @DeleteMapping("removeOwner/{ownerId}")
    public ResponseEntity<Void> removeOwner(@PathVariable Long ownerId){
        return ResponseEntity.ok(ownerAndPetService.removeOwner(ownerId));
    }

    @GetMapping("getAllOwners")
    public ResponseEntity<List<OwnerResponse>> getAllOwners(){
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @DeleteMapping("removeOwnersAndPets/{ownerId}")
    public ResponseEntity<Void> removeOwnersAndPets(@PathVariable Long ownerId){
        return ResponseEntity.ok(ownerAndPetService.removeOwnerAndPets(ownerId));
    }


}

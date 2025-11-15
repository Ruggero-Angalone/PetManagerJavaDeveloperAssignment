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
import mapper.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerAndPetService {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private PetService petService;


    public Void removeOwnerAndPets(Long id) {
        OwnerEntity owner = ownerService.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner with id " + id + " not found"));
        petService.removePetsByOwnerName(owner.getOwnerName());
        ownerService.deleteById(id);
        return null;
    }

    public PetResponse addPet(PetRequest petRequest) {
        List<OwnerEntity> owners = ownerService.findByName(petRequest.getOwnerName());
        if (owners.isEmpty()) {
            throw new OwnerNotFoundException(
                    "Owner with name '" + petRequest.getOwnerName() + "' not found"
            );
        }
        if (owners.size() > 1) {
            throw new AmbiguousOwnerException(
                    "Multiple owners found with name '" + petRequest.getOwnerName() + "'. Names must be unique."
            );
        }
        PetEntity petEntity = PetMapper.toEntity(petRequest);
        PetEntity savedPet = petService.save(petEntity);
        return PetMapper.toResponse(savedPet);
    }

    public PetAndOwnerResponse addPetAndOwner(PetAndOwnerRequest petAndOwnerRequest) {
        // create owner
        OwnerRequest ownerRequest = new OwnerRequest();
        ownerRequest.setOwnerName(petAndOwnerRequest.getOwnerName());
        ownerRequest.setPhoneNumber(petAndOwnerRequest.getPhoneNumber());
        ownerRequest.setAddress(petAndOwnerRequest.getAddress());

        OwnerResponse savedOwner = ownerService.addOwner(ownerRequest);

        //create pet
        PetRequest petRequest = new PetRequest();
        petRequest.setName(petAndOwnerRequest.getName());
        petRequest.setSpecies(petAndOwnerRequest.getSpecies());
        petRequest.setAge(petAndOwnerRequest.getAge());
        petRequest.setOwnerName(savedOwner.getOwnerName());
        PetEntity savedPet = petService.save(PetMapper.toEntity(petRequest));

        PetAndOwnerResponse response = new PetAndOwnerResponse();
        response.setPet(PetMapper.toResponse(savedPet));
        response.setOwner(savedOwner);

        return response;
    }

    public Void removeOwner(Long id) {
        OwnerEntity owner = ownerService.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner with id " + id + " not found"));
        boolean hasPets = petService.getAllPets().stream()
                .anyMatch(p -> p.getOwnerName().equalsIgnoreCase(owner.getOwnerName()));
        if (hasPets) {
            throw new OwnerHasPetsException(
                    "Cannot remove owner '" + owner.getOwnerName() + "' because they have associated pets"
            );
        }
        ownerService.deleteById(id);
        return null;
    }
}

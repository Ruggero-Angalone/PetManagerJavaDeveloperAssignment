package com.petmanager.service;

import com.petmanager.dao.PetDaoInterface;
import com.petmanager.dto.request.PetRequest;
import com.petmanager.dto.response.PetResponse;
import com.petmanager.entity.PetEntity;
import com.petmanager.mapper.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PetService {

    @Autowired
    private PetDaoInterface petDao;

    public PetEntity save(PetRequest petRequest) {
        if(validPet(petRequest)){
            return petDao.save(PetMapper.toEntity(petRequest));
        }
        return null;
    }

    public Boolean removePet(Long id){
        return petDao.deleteById(id);
    }

    public List<PetResponse> getAllPets() {
        List<PetResponse> responseList = new LinkedList<>();
        List<PetEntity> all = petDao.findAll();
        for(PetEntity entity : all){
            responseList.add(PetMapper.toResponse(entity));
        }
        return responseList;
    }

    public PetEntity updatePet(Long petId, PetRequest petRequest) {
        PetEntity existingPet = petDao.findById(petId)
                .orElseThrow(() -> new NoSuchElementException("Pet not found with id: " + petId));
        if(validPet(petRequest)){
            existingPet.setName(petRequest.getName());
            existingPet.setSpecies(petRequest.getSpecies());
            existingPet.setAge(petRequest.getAge());
            existingPet.setOwnerName(petRequest.getOwnerName());

            return petDao.save(existingPet);
        }
        return null;
    }

    private boolean validPet(PetRequest petRequest){
        if (petRequest.getName() == null || petRequest.getName().isBlank()) {
            throw new IllegalArgumentException("Pet name is required");
        }

        if (petRequest.getSpecies() == null) {
            throw new IllegalArgumentException("Pet species is required");
        }

        if (petRequest.getAge() != null && petRequest.getAge() < 0) {
            throw new IllegalArgumentException("Pet age must be zero or positive");
        }
        return true;
    }

    public PetEntity getPetById(Long petId) {
        return petDao.findById(petId).orElse(null);
    }
}

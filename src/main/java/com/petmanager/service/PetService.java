package com.petmanager.service;

import com.petmanager.dao.PetDao;
import com.petmanager.dto.request.OwnerRequest;
import com.petmanager.dto.request.PetAndOwnerRequest;
import com.petmanager.dto.request.PetRequest;
import com.petmanager.dto.response.OwnerResponse;
import com.petmanager.dto.response.PetAndOwnerResponse;
import com.petmanager.dto.response.PetResponse;
import com.petmanager.entity.OwnerEntity;
import com.petmanager.entity.PetEntity;
import com.petmanager.exception.AmbiguousOwnerException;
import com.petmanager.exception.OwnerNotFoundException;
import mapper.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetDao petDao;

    public PetEntity save(PetEntity pet){
        return petDao.save(pet);
    }

    public Void removePet(Long id){
        petDao.deleteById(id);
        return null;
    }

    public List<PetResponse> getAllPets() {
        List<PetResponse> responseList = new LinkedList<>();
        List<PetEntity> all = petDao.findAll();
        for(PetEntity entity : all){
            responseList.add(PetMapper.toResponse(entity));
        }
        return responseList;
    }

    public Void removePetsByOwnerName(String ownerName) {
        List<PetEntity> petsToRemove = petDao.findAll().stream()
                .filter(p -> p.getOwnerName().equalsIgnoreCase(ownerName))
                .toList();
        for (PetEntity pet : petsToRemove) {
            petDao.deleteById(pet.getId());
        }
        return null;
    }
}

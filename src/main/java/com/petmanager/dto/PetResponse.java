package com.petmanager.dto;

import com.petmanager.entity.PetEntity;
import com.petmanager.entity.PetOwnerEntity;
import lombok.Data;

@Data
public class PetResponse {
    PetEntity pet;
    PetOwnerEntity petOwner;
}

package com.petmanager.dto.response;

import lombok.Data;

@Data
public class PetAndOwnerResponse {
    private PetResponse pet;
    private OwnerResponse owner;
}

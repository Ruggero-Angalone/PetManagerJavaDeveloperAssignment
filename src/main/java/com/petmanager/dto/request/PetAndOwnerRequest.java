package com.petmanager.dto.request;

import lombok.Data;

@Data
public class PetAndOwnerRequest {

    // Pet
    private String name;
    private String species;
    private Integer age;

    // Owner
    private String ownerName;
    private String surname;
    private String phoneNumber;
    private String address;
}

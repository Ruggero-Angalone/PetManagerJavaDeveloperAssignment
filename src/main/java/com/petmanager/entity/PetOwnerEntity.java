package com.petmanager.entity;

import lombok.Data;

@Data
public class PetOwnerEntity {
    private Long ownerId;
    private String ownerName;
    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
}

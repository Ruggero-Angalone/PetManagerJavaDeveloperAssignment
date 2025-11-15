package com.petmanager.entity;

import lombok.Data;

@Data
public class OwnerEntity {
    private Long ownerId;
    private String ownerName;
    private String phoneNumber;
    private String address;
}

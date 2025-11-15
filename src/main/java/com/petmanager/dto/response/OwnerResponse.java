package com.petmanager.dto.response;

import lombok.Data;

@Data
public class OwnerResponse {
    private Long ownerId;
    private String ownerName;
    private String phoneNumber;
    private String address;
}
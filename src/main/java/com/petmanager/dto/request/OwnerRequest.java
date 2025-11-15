package com.petmanager.dto.request;

import lombok.Data;

@Data
public class OwnerRequest {
    private String ownerName;
    private String phoneNumber;
    private String address;
}
package com.petmanager.dto.request;

import lombok.Data;

@Data
public class PetRequest {
    private String name;
    private String species;
    private Integer age;
    private String ownerName;
}
package com.petmanager.entity;

import lombok.Data;

@Data
public class PetEntity {
    private Long id;
    private String name;
    private String species;
    private Integer age;
    private String ownerName;   // This should be changed to be the owner id, I'm not changing it since it's a given requirement
}

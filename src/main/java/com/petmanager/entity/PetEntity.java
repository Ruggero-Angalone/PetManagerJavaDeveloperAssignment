package com.petmanager.entity;

import com.petmanager.enums.Species;
import lombok.Data;

@Data
public class PetEntity {
    private Long id;
    private String name;
    private Species species;
    private Integer age;
    private String ownerName;
}

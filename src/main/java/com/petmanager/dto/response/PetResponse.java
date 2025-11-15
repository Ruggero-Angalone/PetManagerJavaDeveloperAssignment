package com.petmanager.dto.response;

import com.petmanager.enums.Species;
import lombok.Data;

@Data
public class PetResponse {
    private Long id;
    private String name;
    private Species species;
    private Integer age;
    private String ownerName;
}

package com.petmanager.dto.request;

import com.petmanager.enums.Species;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PetRequest {
    @NotBlank(message = "Pet name is required")
    private String name;
    @NotNull(message = "Pet species is required")
    private Species species;
    @PositiveOrZero(message = "Pet age must be positive")
    @Nullable
    private Integer age;
    private String ownerName;
}
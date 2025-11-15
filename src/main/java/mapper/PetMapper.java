package mapper;

import com.petmanager.dto.request.PetRequest;
import com.petmanager.dto.response.PetResponse;
import com.petmanager.entity.PetEntity;

public class PetMapper {

    public static PetEntity toEntity(PetRequest request) {
        PetEntity entity = new PetEntity();
        entity.setName(request.getName());
        entity.setSpecies(request.getSpecies());
        entity.setAge(request.getAge());
        entity.setOwnerName(request.getOwnerName()); // required by your schema
        return entity;
    }

    public static PetResponse toResponse(PetEntity entity) {
        PetResponse response = new PetResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSpecies(entity.getSpecies());
        response.setAge(entity.getAge());
        response.setOwnerName(entity.getOwnerName());
        return response;
    }
}

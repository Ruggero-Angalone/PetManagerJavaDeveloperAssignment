package mapper;

import com.petmanager.dto.request.OwnerRequest;
import com.petmanager.dto.response.OwnerResponse;
import com.petmanager.entity.OwnerEntity;

public class OwnerMapper {

    public static OwnerEntity toEntity(OwnerRequest request) {
        OwnerEntity entity = new OwnerEntity();
        entity.setOwnerName(request.getOwnerName());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setAddress(request.getAddress());
        return entity;
    }

    public static OwnerResponse toResponse(OwnerEntity entity) {
        OwnerResponse response = new OwnerResponse();
        response.setOwnerId(entity.getOwnerId());
        response.setOwnerName(entity.getOwnerName());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setAddress(entity.getAddress());
        return response;
    }
}

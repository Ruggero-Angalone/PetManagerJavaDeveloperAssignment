package com.petmanager.service;

import com.petmanager.dao.OwnerDao;
import com.petmanager.dto.request.OwnerRequest;
import com.petmanager.dto.response.OwnerResponse;
import com.petmanager.entity.OwnerEntity;
import com.petmanager.exception.AmbiguousOwnerException;
import mapper.OwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerDao ownerDao;

    public OwnerResponse addOwner(OwnerRequest ownerRequest) {
        List<OwnerEntity> existingOwners = ownerDao.findByName(ownerRequest.getOwnerName());
        if (!existingOwners.isEmpty()) {
            throw new AmbiguousOwnerException(
                    "Owner with name '" + ownerRequest.getOwnerName() + "' already exists"
            );
        }
        OwnerEntity entity = OwnerMapper.toEntity(ownerRequest);
        OwnerEntity savedEntity = ownerDao.save(entity);
        return OwnerMapper.toResponse(savedEntity);
    }

    public List<OwnerResponse> getAllOwners() {
        List<OwnerResponse> responseList = new LinkedList<>();
        List<OwnerEntity> all = ownerDao.findAll();
        for(OwnerEntity entity : all){
            responseList.add(OwnerMapper.toResponse(entity));
        }
        return responseList;
    }

    public List<OwnerEntity> findByName(String name) {
        return ownerDao.findByName(name);
    }

    public Optional<OwnerEntity> findById(Long id){
        return ownerDao.findById(id);
    }

    public void deleteById(Long id){
        ownerDao.deleteById(id);
    }

}

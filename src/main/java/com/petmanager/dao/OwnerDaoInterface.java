package com.petmanager.dao;

import com.petmanager.entity.OwnerEntity;

import java.util.List;

public interface OwnerDaoInterface extends GenericDao<OwnerEntity, Long>{
    public List<OwnerEntity> findByName(String ownerName);
}

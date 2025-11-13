package com.petmanager.dao;

import com.petmanager.entity.PetOwnerEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PetOwnerDao implements GenericDao<PetOwnerEntity, Long> {

    private static final Map<Long, PetOwnerEntity> allOwners = new ConcurrentHashMap<>();
    private static final AtomicLong lastId = new AtomicLong(0);

    @Override
    public PetOwnerEntity save(PetOwnerEntity entity) {
        if (entity.getOwnerId() == null) {
            entity.setOwnerId(lastId.incrementAndGet());
        }
        allOwners.put(entity.getOwnerId(), entity);
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        allOwners.remove(id);
    }

    @Override
    public Optional<PetOwnerEntity> findById(Long id) {
        return Optional.ofNullable(allOwners.get(id));
    }

    @Override
    public List<PetOwnerEntity> findAll() {
        return new ArrayList<>(allOwners.values());
    }
}

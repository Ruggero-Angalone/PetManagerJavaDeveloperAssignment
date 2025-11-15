package com.petmanager.dao;

import com.petmanager.entity.PetEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PetDao implements PetDaoInterface{
    private static final ConcurrentHashMap<Long, PetEntity> allPets = new ConcurrentHashMap<>();
    private static final AtomicLong lastId = new AtomicLong(0);

    @Override
    public PetEntity save(PetEntity entity) {
        if (entity.getId() == null) {
            entity.setId(lastId.incrementAndGet());
        }
        allPets.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Boolean deleteById(Long id) {
        return allPets.remove(id) != null;
    }

    @Override
    public Optional<PetEntity> findById(Long id) {
        return Optional.ofNullable(allPets.get(id));
    }

    @Override
    public List<PetEntity> findAll() {
        return new ArrayList<>(allPets.values());
    }
}

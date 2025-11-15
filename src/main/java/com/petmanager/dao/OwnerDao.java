package com.petmanager.dao;

import com.petmanager.entity.OwnerEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class OwnerDao implements GenericDao<OwnerEntity, Long> {

    private static final Map<Long, OwnerEntity> allOwners = new ConcurrentHashMap<>();
    private static final AtomicLong lastId = new AtomicLong(0);

    @Override
    public OwnerEntity save(OwnerEntity entity) {
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
    public Optional<OwnerEntity> findById(Long id) {
        return Optional.ofNullable(allOwners.get(id));
    }

    @Override
    public List<OwnerEntity> findAll() {
        return new ArrayList<>(allOwners.values());
    }

    public List<OwnerEntity> findByName(String ownerName) {
        return allOwners.values().stream()
                .filter(o -> o.getOwnerName().equalsIgnoreCase(ownerName))
                .collect(Collectors.toList());
    }
}

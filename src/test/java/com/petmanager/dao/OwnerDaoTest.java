package com.petmanager.dao;

import com.petmanager.entity.OwnerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OwnerDaoTest {

    @Autowired
    private OwnerDao ownerDao;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        try {
            Field allOwnersField = OwnerDao.class.getDeclaredField("allOwners");
            allOwnersField.setAccessible(true);
            Map<Long, OwnerEntity> allOwners = (Map<Long, OwnerEntity>) allOwnersField.get(null);
            allOwners.clear();

            Field lastIdField = OwnerDao.class.getDeclaredField("lastId");
            lastIdField.setAccessible(true);
            AtomicLong lastId = (AtomicLong) lastIdField.get(null);
            lastId.set(0);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to clear OwnerDao static fields", e);
        }
    }

    @Test
    public void testSaveAndFindById() {
        OwnerEntity owner = new OwnerEntity();
        owner.setOwnerName("Alice");
        owner.setPhoneNumber("123456789");
        owner.setAddress("Street 1");

        OwnerEntity saved = ownerDao.save(owner);

        assertNotNull(saved.getOwnerId());
        Optional<OwnerEntity> found = ownerDao.findById(saved.getOwnerId());
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getOwnerName());
    }

    @Test
    public void testDeleteById() {
        OwnerEntity owner = new OwnerEntity();
        owner.setOwnerName("Bob");
        OwnerEntity saved = ownerDao.save(owner);

        ownerDao.deleteById(saved.getOwnerId());
        assertFalse(ownerDao.findById(saved.getOwnerId()).isPresent());
    }

    @Test
    public void testFindAll() {
        OwnerEntity owner1 = new OwnerEntity();
        owner1.setOwnerName("Owner1");
        OwnerEntity owner2 = new OwnerEntity();
        owner2.setOwnerName("Owner2");

        ownerDao.save(owner1);
        ownerDao.save(owner2);

        List<OwnerEntity> all = ownerDao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    public void testFindByName() {
        OwnerEntity owner1 = new OwnerEntity();
        owner1.setOwnerName("Charlie");
        OwnerEntity owner2 = new OwnerEntity();
        owner2.setOwnerName("Charlie");
        OwnerEntity owner3 = new OwnerEntity();
        owner3.setOwnerName("Delta");

        ownerDao.save(owner1);
        ownerDao.save(owner2);
        ownerDao.save(owner3);

        List<OwnerEntity> found = ownerDao.findByName("Charlie");
        assertEquals(2, found.size());

        List<OwnerEntity> notFound = ownerDao.findByName("Echo");
        assertTrue(notFound.isEmpty());
    }
}
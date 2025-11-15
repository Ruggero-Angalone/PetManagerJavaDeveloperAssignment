package com.petmanager.dao;

import com.petmanager.entity.PetEntity;
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
public class PetDaoTest {

    @Autowired
    private PetDao petDao;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        try {
            // Clear the static map
            Field allPetsField = PetDao.class.getDeclaredField("allPets");
            allPetsField.setAccessible(true);
            Map<Long, PetEntity> allPets = (Map<Long, PetEntity>) allPetsField.get(null);
            allPets.clear();

            // Reset the counter
            Field lastIdField = PetDao.class.getDeclaredField("lastId");
            lastIdField.setAccessible(true);
            AtomicLong lastId = (AtomicLong) lastIdField.get(null);
            lastId.set(0);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to clear PetDao static fields", e);
        }
    }
    @Test
    public void testSaveAndFindById() {
        PetEntity pet = new PetEntity();
        pet.setName("Fluffy");
        pet.setSpecies("Cat");
        pet.setAge(3);
        pet.setOwnerName("Alice");

        PetEntity saved = petDao.save(pet);
        assertNotNull(saved.getId());

        Optional<PetEntity> found = petDao.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Fluffy", found.get().getName());
        assertEquals("Alice", found.get().getOwnerName());
    }

    @Test
    public void testDeleteById() {
        PetEntity pet = new PetEntity();
        pet.setName("Rex");
        PetEntity saved = petDao.save(pet);

        petDao.deleteById(saved.getId());
        assertFalse(petDao.findById(saved.getId()).isPresent());
    }

    @Test
    public void testFindAll() {
        PetEntity pet1 = new PetEntity();
        pet1.setName("Pet1");
        PetEntity pet2 = new PetEntity();
        pet2.setName("Pet2");

        petDao.save(pet1);
        petDao.save(pet2);

        List<PetEntity> all = petDao.findAll();
        assertEquals(2, all.size());
    }
}
package com.petmanager.service;

import com.petmanager.dao.OwnerDao;
import com.petmanager.dto.request.OwnerRequest;
import com.petmanager.dto.response.OwnerResponse;
import com.petmanager.entity.OwnerEntity;
import com.petmanager.exception.AmbiguousOwnerException;
import mapper.OwnerMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OwnerServiceTest {

    @Mock
    private OwnerDao ownerDao;

    @InjectMocks
    private OwnerService ownerService;

    private AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this); // initialize mocks
    }

    @AfterEach
    public void tearDown() throws Exception {
        mocks.close(); // close resources
    }

    @Test
    public void testAddOwnerSuccess() {
        OwnerRequest request = new OwnerRequest();
        request.setOwnerName("Alice");
        request.setPhoneNumber("123456789");
        request.setAddress("Street 1");

        when(ownerDao.findByName("Alice")).thenReturn(Collections.emptyList());

        OwnerEntity savedEntity = OwnerMapper.toEntity(request);
        savedEntity.setOwnerId(1L);

        when(ownerDao.save(any(OwnerEntity.class))).thenReturn(savedEntity);

        OwnerResponse response = ownerService.addOwner(request);

        assertNotNull(response);
        assertEquals(1L, response.getOwnerId());
        assertEquals("Alice", response.getOwnerName());

        verify(ownerDao).findByName("Alice");
        verify(ownerDao).save(any(OwnerEntity.class));
    }

    @Test
    public void testAddOwnerThrowsAmbiguousOwnerException() {
        OwnerRequest request = new OwnerRequest();
        request.setOwnerName("Bob");

        when(ownerDao.findByName("Bob")).thenReturn(
                Arrays.asList(new OwnerEntity(), new OwnerEntity())
        );

        assertThrows(AmbiguousOwnerException.class, () -> ownerService.addOwner(request));

        verify(ownerDao).findByName("Bob");
        verify(ownerDao, never()).save(any());
    }

    @Test
    public void testGetAllOwners() {
        OwnerEntity owner1 = new OwnerEntity();
        owner1.setOwnerId(1L);
        owner1.setOwnerName("Alice");

        OwnerEntity owner2 = new OwnerEntity();
        owner2.setOwnerId(2L);
        owner2.setOwnerName("Bob");

        when(ownerDao.findAll()).thenReturn(Arrays.asList(owner1, owner2));

        List<OwnerResponse> allOwners = ownerService.getAllOwners();
        assertEquals(2, allOwners.size());
        assertTrue(allOwners.stream().anyMatch(o -> o.getOwnerName().equals("Alice")));
        assertTrue(allOwners.stream().anyMatch(o -> o.getOwnerName().equals("Bob")));

        verify(ownerDao).findAll();
    }

    @Test
    public void testFindByName() {
        OwnerEntity owner = new OwnerEntity();
        owner.setOwnerId(1L);
        owner.setOwnerName("Charlie");

        when(ownerDao.findByName("Charlie")).thenReturn(Collections.singletonList(owner));

        List<OwnerEntity> result = ownerService.findByName("Charlie");
        assertEquals(1, result.size());
        assertEquals("Charlie", result.get(0).getOwnerName());

        verify(ownerDao).findByName("Charlie");
    }

    @Test
    public void testFindById() {
        OwnerEntity owner = new OwnerEntity();
        owner.setOwnerId(1L);

        when(ownerDao.findById(1L)).thenReturn(Optional.of(owner));

        Optional<OwnerEntity> result = ownerService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getOwnerId());

        verify(ownerDao).findById(1L);
    }

    @Test
    public void testDeleteById() {
        when(ownerDao.deleteById(1L)).thenReturn(true);
        Boolean result = ownerService.deleteById(1L);
        verify(ownerDao).deleteById(1L);
        assertTrue(result);
    }
}
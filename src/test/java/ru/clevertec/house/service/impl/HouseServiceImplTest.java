package ru.clevertec.house.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.mapper.HouseMapper;
import ru.clevertec.house.model.House;
import ru.clevertec.house.repository.HouseRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private HouseMapper houseMapper;

    @InjectMocks
    private HouseServiceImpl houseService;

    @Test
    public void testCreate() {
        // Arrange
        HouseRequestDto requestDto = new HouseRequestDto();
        House house = new House().setUuid(UUID.randomUUID()).setCreateDate(LocalDateTime.now());
        when(houseMapper.toEntity(requestDto)).thenReturn(house);
        when(houseRepository.save(any(House.class))).thenReturn(true);

        // Act
        boolean result = houseService.create(requestDto);

        // Assert
        Assertions.assertTrue(result);
        verify(houseMapper).toEntity(requestDto);
        verify(houseRepository).save(house);
    }

    @Test
    public void testCreateFailsOnSave() {
        // Arrange
        HouseRequestDto requestDto = new HouseRequestDto();
        House house = new House().setUuid(UUID.randomUUID()).setCreateDate(LocalDateTime.now());
        when(houseMapper.toEntity(requestDto)).thenReturn(house);
        when(houseRepository.save(any(House.class))).thenReturn(false);

        // Act
        boolean result = houseService.create(requestDto);

        // Assert
        Assertions.assertFalse(result);
        verify(houseMapper).toEntity(requestDto);
        verify(houseRepository).save(house);
    }

    @Test
    public void testFindById() {
        // Arrange
        Long houseId = 1L;
        House house = new House();
        HouseResponseDto responseDto = new HouseResponseDto();
        when(houseRepository.findById(houseId)).thenReturn(house);
        when(houseMapper.toDto(house)).thenReturn(responseDto);

        // Act
        HouseResponseDto result = houseService.findById(houseId);

        // Assert
        Assertions.assertEquals(responseDto, result);
        verify(houseRepository).findById(houseId);
        verify(houseMapper).toDto(house);
    }


    @Test
    public void testFindByOwnerId() {
        // Arrange
        Long ownerId = 1L;
        List<House> houses = Collections.singletonList(new House());
        List<HouseResponseDto> responseDtoList = Collections.singletonList(new HouseResponseDto());
        when(houseRepository.findByOwnerId(ownerId)).thenReturn(houses);
        when(houseMapper.toDtoList(houses)).thenReturn(responseDtoList);

        // Act
        List<HouseResponseDto> result = houseService.findByOwnerId(ownerId);

        // Assert
        Assertions.assertEquals(responseDtoList, result);
        verify(houseRepository).findByOwnerId(ownerId);
        verify(houseMapper).toDtoList(houses);
    }

    @Test
    public void testFindAll() {
        // Arrange
        int limit = 10;
        int offset = 0;
        List<House> houses = Collections.singletonList(new House());
        List<HouseResponseDto> responseDtoList = Collections.singletonList(new HouseResponseDto());
        when(houseRepository.findAll(limit, offset)).thenReturn(houses);
        when(houseMapper.toDtoList(houses)).thenReturn(responseDtoList);

        // Act
        List<HouseResponseDto> result = houseService.findAll(limit, offset);

        // Assert
        Assertions.assertEquals(responseDtoList, result);
        verify(houseRepository).findAll(limit, offset);
        verify(houseMapper).toDtoList(houses);
    }


    @Test
    public void testUpdateById() {
        // Arrange
        Long houseId = 1L;
        HouseRequestDto requestDto = new HouseRequestDto();
        House house = new House();
        when(houseMapper.toEntity(requestDto)).thenReturn(house);
        when(houseRepository.updateById(eq(houseId), any(House.class))).thenReturn(true);

        // Act
        boolean result = houseService.updateById(houseId, requestDto);

        // Assert
        Assertions.assertTrue(true);
        verify(houseMapper).toEntity(requestDto);
        verify(houseRepository).updateById(houseId, house);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Long houseId = 1L;
        when(houseRepository.deleteById(houseId)).thenReturn(true);

        // Act
        boolean result = houseService.deleteById(houseId);

        // Assert
        Assertions.assertTrue(result);
        verify(houseRepository).deleteById(houseId);
    }

    @Test
    public void testDeleteByIdFails() {
        // Arrange
        Long houseId = 1L;
        when(houseRepository.deleteById(houseId)).thenReturn(false);

        // Act
        boolean result = houseService.deleteById(houseId);

        // Assert
        Assertions.assertFalse(result);
        verify(houseRepository).deleteById(houseId);
    }

}
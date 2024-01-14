package ru.clevertec.house.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.mapper.HouseMapper;
import ru.clevertec.house.model.House;
import ru.clevertec.house.repository.HouseRepository;
import ru.clevertec.house.service.HouseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final HouseMapper houseMapper;

    @Override
    public boolean create(HouseRequestDto houseRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        House house = houseMapper.toEntity(houseRequestDto)
                .setUuid(UUID.randomUUID())
                .setCreateDate(now)
                .setUpdateDate(now);
        return houseRepository.save(house);
    }

    @Override
    public HouseResponseDto findById(Long id) {
        House house = houseRepository.findById(id);
        return houseMapper.toDto(house);
    }

    @Override
    public List<HouseResponseDto> findByOwnerId(Long ownerId) {
        List<House> houses = houseRepository.findByOwnerId(ownerId);
        return houseMapper.toDtoList(houses);
    }

    @Override
    public List<HouseResponseDto> findAll(int limit, int offset) {
        List<House> houses = houseRepository.findAll(limit, offset);
        return houseMapper.toDtoList(houses);
    }

    @Override
    public boolean updateById(Long id, HouseRequestDto houseRequestDto) {
        House house = houseMapper.toEntity(houseRequestDto);
        return houseRepository.updateById(id, house);
    }

    @Override
    public boolean deleteById(Long id) {
        return houseRepository.deleteById(id);
    }
}

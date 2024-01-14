package ru.clevertec.house.service;

import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;

import java.util.List;

public interface HouseService {

    boolean create(HouseRequestDto houseRequestDto);

    HouseResponseDto findById(Long id);

    List<HouseResponseDto> findByOwnerId(Long ownerId);

    List<HouseResponseDto> findAll(int limit, int offset);

    boolean updateById(Long id, HouseRequestDto houseRequestDto);

    boolean deleteById(Long id);

}

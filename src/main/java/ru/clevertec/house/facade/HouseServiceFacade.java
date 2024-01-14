package ru.clevertec.house.facade;

import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;

import java.util.List;

public interface HouseServiceFacade {
    boolean create(HouseRequestDto houseRequestDto);

    HouseResponseDto findById(Long id);

    List<PersonResponseDto> findHouseResidents(Long id);

    List<HouseResponseDto> findAll(int limit, int offset);

    boolean updateById(Long id, HouseRequestDto houseRequestDto);

    boolean deleteById(Long id);

}

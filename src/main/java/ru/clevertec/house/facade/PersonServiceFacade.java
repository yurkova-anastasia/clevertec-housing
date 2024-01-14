package ru.clevertec.house.facade;

import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;

import java.util.List;

public interface PersonServiceFacade {

    boolean create(PersonRequestDto personRequestDto);

    PersonResponseDto findById(Long id);

    List<HouseResponseDto> findOwnedHouses(Long id);

    List<PersonResponseDto> findAll(int limit, int offset);

    boolean updateById(Long id, PersonRequestDto personRequestDto);

    boolean deleteById(Long id);

}

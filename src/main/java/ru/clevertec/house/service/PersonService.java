package ru.clevertec.house.service;

import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.PersonResponseDto;

import java.util.List;

public interface PersonService {

    boolean create(PersonRequestDto personRequestDto);

    PersonResponseDto findById(Long id);

    List<PersonResponseDto> findByHouseId(Long houseId);

    List<PersonResponseDto> findAll(int limit, int offset);

    boolean updateById(Long id, PersonRequestDto personRequestDto);

    boolean deleteById(Long id);

}

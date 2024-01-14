package ru.clevertec.house.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.PersonServiceFacade;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.service.PersonService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonServiceFacadeImpl implements PersonServiceFacade {

    private final PersonService personService;
    private final HouseService houseService;

    @Override
    public boolean create(PersonRequestDto personRequestDto) {
        return personService.create(personRequestDto);
    }

    @Override
    public PersonResponseDto findById(Long id) {
        return personService.findById(id);
    }

    @Override
    public List<HouseResponseDto> findOwnedHouses(Long id) {
        return houseService.findByOwnerId(id);
    }

    @Override
    public List<PersonResponseDto> findAll(int limit, int offset) {
        return personService.findAll(limit, offset);
    }

    @Override
    public boolean updateById(Long id, PersonRequestDto personRequestDto) {
        return personService.updateById(id, personRequestDto);
    }

    @Override
    public boolean deleteById(Long id) {
        return personService.deleteById(id);
    }
}

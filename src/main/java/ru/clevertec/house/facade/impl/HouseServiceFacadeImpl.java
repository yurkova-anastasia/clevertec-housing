package ru.clevertec.house.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.HouseServiceFacade;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.service.PersonService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HouseServiceFacadeImpl implements HouseServiceFacade {

    private final HouseService houseService;
    private final PersonService personService;

    @Override
    public boolean create(HouseRequestDto houseRequestDto) {
        return houseService.create(houseRequestDto);
    }

    @Override
    public HouseResponseDto findById(Long id) {
        return houseService.findById(id);
    }

    @Override
    public List<PersonResponseDto> findHouseResidents(Long id) {
        return personService.findByHouseId(id);
    }

    @Override
    public List<HouseResponseDto> findAll(int limit, int offset) {
        return houseService.findAll(limit, offset);
    }

    @Override
    public boolean updateById(Long id, HouseRequestDto houseRequestDto) {
        return houseService.updateById(id, houseRequestDto);
    }

    @Override
    public boolean deleteById(Long id) {
        return houseService.deleteById(id);
    }
}

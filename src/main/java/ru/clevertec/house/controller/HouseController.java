package ru.clevertec.house.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.HouseServiceFacade;

import java.util.List;

@RestController
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseServiceFacade houseServiceFacade;

    @PostMapping
    public boolean create(@RequestBody HouseRequestDto houseRequestDto) {
        return houseServiceFacade.create(houseRequestDto);
    }

    @GetMapping("/{id}")
    public HouseResponseDto findById(@PathVariable("id") Long id) {
        return houseServiceFacade.findById(id);
    }

    @GetMapping("/{id}/residents")
    public List<PersonResponseDto> findHouseResidents(@PathVariable("id") Long id) {
        return houseServiceFacade.findHouseResidents(id);
    }

    @GetMapping()
    public List<HouseResponseDto> findAll(@RequestParam(required = false, defaultValue = "15") int limit,
                                          @RequestParam(required = false, defaultValue = "0") int offset) {
        return houseServiceFacade.findAll(limit, offset);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable("id") Long id,
                          @RequestBody HouseRequestDto houseRequestDto) {
        return houseServiceFacade.updateById(id, houseRequestDto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return houseServiceFacade.deleteById(id);
    }

}

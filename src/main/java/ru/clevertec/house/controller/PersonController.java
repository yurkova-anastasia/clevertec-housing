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
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.PersonServiceFacade;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonServiceFacade personServiceFacade;

    @PostMapping
    public boolean create(@RequestBody @Valid PersonRequestDto personRequestDto) {
        return personServiceFacade.create(personRequestDto);
    }

    @GetMapping("/{id}")
    public PersonResponseDto findById(@PathVariable("id") Long id) {
        return personServiceFacade.findById(id);
    }


    @GetMapping("/{id}/owned-houses")
    public List<HouseResponseDto> findOwnedHouses(@PathVariable("id") Long id) {
        return personServiceFacade.findOwnedHouses(id);
    }

    @GetMapping()
    public List<PersonResponseDto> findAll(@RequestParam(required = false, defaultValue = "15") int limit,
                                           @RequestParam(required = false, defaultValue = "0") int offset) {
        return personServiceFacade.findAll(limit, offset);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable("id") Long id,
                          @RequestBody @Valid PersonRequestDto personRequestDto) {
        return personServiceFacade.updateById(id, personRequestDto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return personServiceFacade.deleteById(id);
    }
}

package ru.clevertec.house.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.model.House;
import ru.clevertec.house.model.Person;
import ru.clevertec.house.repository.HouseRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    @Autowired
    private HouseRepository houseRepository;

    public abstract  PersonResponseDto toDto(Person person);

    public abstract  List<PersonResponseDto> toDtoList(List<Person> people);

    @Mapping(target = "residency", source = "residencyId")
    public abstract Person toEntity(PersonRequestDto personRequestDto);

    protected  House fromLongToHouse(Long residencyId) throws EntityNotFoundException {
        return houseRepository.findById(residencyId);
    }
}

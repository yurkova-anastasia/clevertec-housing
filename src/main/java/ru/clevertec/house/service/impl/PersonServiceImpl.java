package ru.clevertec.house.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.model.Person;
import ru.clevertec.house.repository.PersonRepository;
import ru.clevertec.house.service.PersonService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public boolean create(PersonRequestDto personRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        Person person = personMapper.toEntity(personRequestDto)
                .setUuid(UUID.randomUUID())
                .setCreateDate(now)
                .setUpdateDate(now);
        return personRepository.save(person);
    }

    @Override
    public PersonResponseDto findById(Long id) {
        Person person = personRepository.findById(id);
        return personMapper.toDto(person);
    }

    @Override
    public List<PersonResponseDto> findByHouseId(Long houseId) {
        List<Person> people = personRepository.findByHouseId(houseId);
        return personMapper.toDtoList(people);
    }

    @Override
    public List<PersonResponseDto> findAll(int limit, int offset) {
        List<Person> people = personRepository.findAll(limit, offset);
        return personMapper.toDtoList(people);
    }

    @Override
    public boolean updateById(Long id, PersonRequestDto personRequestDto) {
        Person person = personMapper.toEntity(personRequestDto);
        return personRepository.updateById(id, person);
    }

    @Override
    public boolean deleteById(Long id) {
        return personRepository.deleteById(id);
    }
}

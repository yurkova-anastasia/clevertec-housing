package ru.clevertec.house.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.model.Person;
import ru.clevertec.house.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void testCreate() {
        // Arrange
        PersonRequestDto requestDto = new PersonRequestDto();
        Person person = new Person().setUuid(UUID.randomUUID()).setCreateDate(LocalDateTime.now());
        when(personMapper.toEntity(requestDto)).thenReturn(person);
        when(personRepository.save(any(Person.class))).thenReturn(true);

        // Act
        boolean result = personService.create(requestDto);

        // Assert
        Assertions.assertTrue(result);
        verify(personMapper).toEntity(requestDto);
        verify(personRepository).save(person);
    }

    @Test
    public void testFindByHouseId() {
        // Arrange
        Long houseId = 1L;
        List<Person> people = Collections.singletonList(new Person());
        List<PersonResponseDto> responseDtoList = Collections.singletonList(new PersonResponseDto());
        when(personRepository.findByHouseId(houseId)).thenReturn(people);
        when(personMapper.toDtoList(people)).thenReturn(responseDtoList);

        // Act
        List<PersonResponseDto> result = personService.findByHouseId(houseId);

        // Assert
        Assertions.assertEquals(responseDtoList, result);
        verify(personRepository).findByHouseId(houseId);
        verify(personMapper).toDtoList(people);
    }

    @Test
    public void testFindByHouseIdEmptyList() {
        // Arrange
        Long houseId = 1L;
        when(personRepository.findByHouseId(houseId)).thenReturn(Collections.emptyList());

        // Act
        List<PersonResponseDto> result = personService.findByHouseId(houseId);

        // Assert
        assertEquals(Collections.emptyList(), result);
        verify(personRepository).findByHouseId(houseId);
    }


    @Test
    public void testFindById() {
        // Arrange
        Long personId = 1L;
        Person person = new Person();
        PersonResponseDto responseDto = new PersonResponseDto();
        when(personRepository.findById(personId)).thenReturn(person);
        when(personMapper.toDto(person)).thenReturn(responseDto);

        // Act
        PersonResponseDto result = personService.findById(personId);

        // Assert
        Assertions.assertEquals(responseDto, result);
        verify(personRepository).findById(personId);
        verify(personMapper).toDto(person);
    }

    @Test
    public void testFindByIdFails() {
        // Arrange
        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(null);

        // Act
        PersonResponseDto result = personService.findById(personId);

        // Assert
        Assertions.assertNull(result);
        verify(personRepository).findById(personId);
        verify(personMapper).toDto(null);
    }

    @Test
    public void testFindAll() {
        // Arrange
        int limit = 10;
        int offset = 0;
        List<Person> people = Collections.singletonList(new Person());
        List<PersonResponseDto> responseDtoList = Collections.singletonList(new PersonResponseDto());
        when(personRepository.findAll(limit, offset)).thenReturn(people);
        when(personMapper.toDtoList(people)).thenReturn(responseDtoList);

        // Act
        List<PersonResponseDto> result = personService.findAll(limit, offset);

        // Assert
        Assertions.assertEquals(responseDtoList, result);
        verify(personRepository).findAll(limit, offset);
        verify(personMapper).toDtoList(people);
    }

    @Test
    public void testUpdateById() {
        // Arrange
        Long personId = 1L;
        PersonRequestDto requestDto = new PersonRequestDto();
        Person person = new Person();
        when(personMapper.toEntity(requestDto)).thenReturn(person);
        when(personRepository.updateById(personId, person)).thenReturn(true);

        // Act
        boolean result = personService.updateById(personId, requestDto);

        // Assert
        Assertions.assertTrue(result);
        verify(personMapper).toEntity(requestDto);
        verify(personRepository).updateById(personId, person);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Long personId = 1L;
        when(personRepository.deleteById(personId)).thenReturn(true);

        // Act
        boolean result = personService.deleteById(personId);

        // Assert
        Assertions.assertTrue(result);
        verify(personRepository).deleteById(personId);
    }

    @Test
    public void testDeleteByIdFails() {
        // Arrange
        Long personId = 1L;
        when(personRepository.deleteById(personId)).thenReturn(false);

        // Act
        boolean result = personService.deleteById(personId);

        // Assert
        Assertions.assertFalse(result);
        verify(personRepository).deleteById(personId);
    }
}

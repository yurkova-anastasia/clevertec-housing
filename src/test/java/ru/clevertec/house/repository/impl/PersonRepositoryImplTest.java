package ru.clevertec.house.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.house.model.House;
import ru.clevertec.house.model.Person;
import ru.clevertec.house.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class PersonRepositoryImplTest extends TestContainer {

    private final PersonRepository personRepository = new PersonRepositoryImpl(sessionFactory, namedParameterJdbcTemplate);

    @Test
    public void testSaveAndFindById() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        House house = new House()
                .setId(3L)
                .setUuid(UUID.fromString("49475f3e-bc1e-4c71-8a9d-4b0fcace530f"))
                .setArea("Area3")
                .setNumber("Number3")
                .setStreet("Street3")
                .setCountry("Country3")
                .setCity("City3")
                .setCreateDate(LocalDateTime.parse("2022-01-12T10:00:00"))
                .setUpdateDate(LocalDateTime.parse("2022-01-12T10:00:00"));

        Person person = new Person()
                .setUuid(UUID.randomUUID())
                .setName("TestName")
                .setSurname("TestSurname")
                .setSex("Male")
                .setPassportSeries("QW")
                .setPassportNumber("123456")
                .setCreateDate(now)
                .setUpdateDate(now)
                .setResidency(house);

        // Act
        boolean saveResult = personRepository.save(person);
        Person foundPerson = personRepository.findById(person.getId());

        // Assert
        Assertions.assertTrue(saveResult);
        Assertions.assertNotNull(foundPerson);
        Assertions.assertEquals(person.getId(), foundPerson.getId());
        Assertions.assertEquals(person.getUuid(), foundPerson.getUuid());
    }

    @Test
    public void testFindById_whenEntityDoesNotExist() {
        // Arrange
        Long id = 100L;

        // Act
        Person person = personRepository.findById(id);

        // Assert
        Assertions.assertNull(person);
    }

    @Test
    void findAllTest_shouldReturnPeopleWithLimitAndOffset() {
        // Arrange
        Person person1 = new Person()
                .setId(3L)
                .setUuid(UUID.fromString("05d56950-9c7b-438c-8c3a-531290834e88"))
                .setName("Alexander")
                .setSurname("Sidorov")
                .setSex("Male")
                .setPassportSeries("EF")
                .setPassportNumber("345678")
                .setCreateDate(LocalDateTime.parse("2022-11-12T10:00:00"))
                .setUpdateDate(LocalDateTime.parse("2022-11-12T10:00:00"));

        Person person2 = new Person()
                .setId(4L)
                .setUuid(UUID.fromString("dee20bf9-0358-440b-8dd0-72e4f1eb66bf"))
                .setName("Elena")
                .setSurname("Kozlova")
                .setSex("Female")
                .setPassportSeries("GH")
                .setPassportNumber("901234")
                .setCreateDate(LocalDateTime.parse("2022-01-12T11:12:00"))
                .setUpdateDate(LocalDateTime.parse("2022-01-12T11:12:00"));

        List<Person> expected = new ArrayList<>() {{
            add(person1);
            add(person2);
        }};

        // Act
        List<Person> actual = personRepository.findAll(2, 2);

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFindByHouseId() {
        // Arrange
        Long houseId = 3L;
        Person person1 = new Person()
                .setId(3L)
                .setUuid(UUID.fromString("05d56950-9c7b-438c-8c3a-531290834e88"))
                .setName("Alexander")
                .setSurname("Sidorov")
                .setSex("Male")
                .setPassportNumber("345678")
                .setPassportSeries("EF")
                .setCreateDate(LocalDateTime.parse("2022-11-12T10:00:00"))
                .setUpdateDate(LocalDateTime.parse("2022-11-12T10:00:00"));

        Person person2 = new Person()
                .setId(8L)
                .setUuid(UUID.fromString("c10b4210-bfba-46ba-a184-6043c7e24caf"))
                .setName("Anna")
                .setSurname("Kovaleva")
                .setSex("Female")
                .setPassportNumber("678901")
                .setPassportSeries("OP")
                .setCreateDate(LocalDateTime.parse("2003-01-12T15:00:00"))
                .setUpdateDate(LocalDateTime.parse("2003-01-12T15:00:00"));
        List<Person> expected = new ArrayList<>() {{
            add(person1);
            add(person2);
        }};

        // Act
        List<Person> actual = personRepository.findByHouseId(houseId);

        // Assert
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateById() {
        // Arrange
        Long personId = 1L;

        Person expected = new Person()
                .setName("UpdatedName")
                .setSurname("UpdatedSurname")
                .setSex("Female")
                .setPassportSeries("XY")
                .setPassportNumber("987654");

        // Act
        boolean updateResult = personRepository.updateById(personId, expected);
        Person actual = personRepository.findById(personId);

        // Assert
        Assertions.assertTrue(updateResult);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getSurname(), actual.getSurname());
        Assertions.assertEquals(expected.getSex(), actual.getSex());
        Assertions.assertEquals(expected.getPassportSeries(), actual.getPassportSeries());
        Assertions.assertEquals(expected.getPassportNumber(), actual.getPassportNumber());
    }

}
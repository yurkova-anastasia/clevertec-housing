package ru.clevertec.house.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.house.model.House;
import ru.clevertec.house.repository.HouseRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class HouseRepositoryImplTest extends TestContainer {

    private final HouseRepository houseRepository = new HouseRepositoryImpl(sessionFactory, namedParameterJdbcTemplate);

    @Test
    public void testSaveAndFindById() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        House house = new House()
                .setUuid(UUID.randomUUID())
                .setArea("Test Area")
                .setCountry("Test Country")
                .setCity("Test City")
                .setStreet("Test Street")
                .setNumber("123")
                .setCreateDate(now)
                .setUpdateDate(now);

        // Act
        boolean saveResult = houseRepository.save(house);
        House foundHouse = houseRepository.findById(house.getId());

        // Assert
        Assertions.assertTrue(saveResult);
        Assertions.assertNotNull(foundHouse);
        Assertions.assertEquals(house.getId(), foundHouse.getId());
        Assertions.assertEquals(house.getUuid(), foundHouse.getUuid());
    }

    @Test
    public void testFindById_whenEntityDoesNotExist() {
        // Arrange
        Long id = 100L;

        // Act
        House house = houseRepository.findById(id);

        // Assert
        Assertions.assertNull(house);
    }

    @Test
    public void testFindByOwnerId() {
        // Arrange
        Long ownerId = 1L;
        House house1 = new House()
                .setId(1L)
                .setUuid(UUID.fromString("59c89acc-62a4-4cbc-87ed-78f632996c08"))
                .setArea("Area1")
                .setCountry("Country1")
                .setCity("City1")
                .setStreet("Street1")
                .setNumber("Number1")
                .setCreateDate(LocalDateTime.parse("2022-01-12T08:00:00"))
                .setUpdateDate(LocalDateTime.parse("2022-01-12T08:00:00"));

        House house2 = new House()
                .setId(4L)
                .setUuid(UUID.fromString("af33aecd-1cdc-4af1-afa3-cbec5b2cdf7b"))
                .setArea("Area4")
                .setCountry("Country4")
                .setCity("City4")
                .setStreet("Street4")
                .setNumber("Number4")
                .setCreateDate(LocalDateTime.parse("2022-01-12T11:00:00"))
                .setUpdateDate(LocalDateTime.parse("2022-01-12T11:00:00"));

        List<House> expected = new ArrayList<>() {{
            add(house1);
            add(house2);
        }};

        // Act
        List<House> actual = houseRepository.findByOwnerId(ownerId);

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateById() {
        // Arrange
        Long houseId = 3L;

        House expected = new House()
                .setId(null)
                .setArea("Updated Area")
                .setNumber("Updated Number")
                .setStreet("Updated Street")
                .setCountry("Updated Country")
                .setCity("City3")
                .setCreateDate(null)
                .setUpdateDate(null);

        // Act
        boolean updateResult = houseRepository.updateById(houseId, expected);
        House actual = houseRepository.findById(houseId);

        // Assert
        Assertions.assertTrue(updateResult);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getArea(), actual.getArea());
        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
        Assertions.assertEquals(expected.getStreet(), actual.getStreet());
        Assertions.assertEquals(expected.getCountry(), actual.getCountry());
        Assertions.assertEquals(expected.getCity(), actual.getCity());
    }

}
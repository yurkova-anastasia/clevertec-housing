package ru.clevertec.house.repository;

import ru.clevertec.house.model.Person;

import java.util.List;

public interface PersonRepository {

    boolean save(Person person);

    Person findById(Long id);

    List<Person> findByHouseId(Long houseId);

    List<Person> findAll(int limit, int offset);

    boolean updateById(Long id, Person person);

    boolean deleteById(Long id);

}

package ru.clevertec.house.repository;

import ru.clevertec.house.model.House;

import java.util.List;

public interface HouseRepository {

    boolean save(House house);

    House findById(Long id);

    List<House> findByOwnerId(Long id);

    List<House> findAll(int limit, int offset);

    boolean updateById(Long id, House house);

    boolean deleteById(Long id);

}

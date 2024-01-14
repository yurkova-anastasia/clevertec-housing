package ru.clevertec.house.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.model.House;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseMapper {

    HouseResponseDto toDto(House house);

    List<HouseResponseDto> toDtoList(List<House> houses);

    House toEntity(HouseRequestDto houseRequestDto);
}

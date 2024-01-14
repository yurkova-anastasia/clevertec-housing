package ru.clevertec.house.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseRequestDto {

    private String area;
    private String country;
    private String city;
    private String street;
    private String number;

}

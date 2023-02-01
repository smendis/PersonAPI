package com.example.sampleapi.mappers;

import com.example.sampleapi.dtos.PersonDto;
import com.example.sampleapi.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PersonMapper {


    @Mapping(source = "name", target = "fullname", defaultValue = "")
    @Mapping(source = "id", target = "pid")
    PersonDto toDto(Person person);

    @Mapping(source = "fullname", target = "name", defaultValue = "")
    @Mapping(source = "pid", target = "id")
    Person toEntity(PersonDto dto);
}

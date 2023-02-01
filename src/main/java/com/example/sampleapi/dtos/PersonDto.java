package com.example.sampleapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private UUID pid;

    @NotNull(message = "Name cannot be empty")
    private String fullname;

    public PersonDto(String fullname) {
        this.fullname = fullname;
    }
}

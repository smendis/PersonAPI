package com.example.sampleapi.controllers;

import com.example.sampleapi.dtos.PersonDto;
import com.example.sampleapi.exceptions.InvalidArgumentException;
import com.example.sampleapi.mappers.PersonMapper;
import com.example.sampleapi.models.Person;
import com.example.sampleapi.services.PersonService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/persons")
public class PersonController {

    private PersonService service;

    private PersonMapper mapper;

    public PersonController(PersonService service, PersonMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(tags = {"Person"}, summary = "This is the summary")
    public List<Person> getAllPeople(){
        return service.getAllPeople();
    }


    @GetMapping("/{id}")
    @Operation(tags = {"Person"}, summary = "This is the summary")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found person",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Person.class))}
            ),
            @ApiResponse(responseCode = "400", description = "Invalid id", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InvalidArgumentException.class))}),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content)
    })
    public Person getPersonById(@PathVariable("id") UUID id){
        return service.getPersonById(id);
    }

    @PostMapping
    @Operation(tags = {"Person"}, summary = "This is the summary")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Person Created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonDto.class))}
            ),
            @ApiResponse(responseCode = "400", description = "fullname can not be null or empty", content = @Content)
    })
    public ResponseEntity<PersonDto> createPerson(@Valid @RequestBody PersonDto newPerson){
        Person inputPerson = mapper.toEntity(newPerson);

        Person savedPerson = service.createPerson(inputPerson);

        PersonDto outPerson = mapper.toDto(savedPerson);
        return new ResponseEntity<>(outPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(tags = {"Person"}, summary = "This is the summary")
    public void updatePerson(@PathVariable("id") UUID id, @RequestBody Person person){
        service.updatePerson(id, person);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = {"Person"}, summary = "This is the summary")
    public void deletePerson(@PathVariable("id") UUID id){
        service.removePerson(id);
    }
}

package com.example.sampleapi.controllers;

import com.example.sampleapi.dtos.PersonDto;
import com.example.sampleapi.mappers.PersonMapper;
import com.example.sampleapi.mappers.PersonMapperImpl;
import com.example.sampleapi.models.Person;
import com.example.sampleapi.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;
import java.util.regex.Matcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService service;

    private PersonController personController;
    @Autowired
    private PersonMapper personMapper;

    private Gson gson;

    @BeforeEach
    public void beforeEach() {
        this.gson = new Gson();
        this.personController = new PersonController(this.service, this.personMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.personController).build();
    }

    @Test
    void getAllPeople() throws Exception {
        this.mockMvc.perform(
                get("/api/v1/persons")
                        .header("Authorization", "XYZ")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(0)));
    }

    @Test
    void getPersonById() {
    }

    @Test
    void createPerson() throws Exception {
        String name = "Ann Mary";
        Person person = new Person(name);
        PersonDto personDto = new PersonDto(name);
        Person savedPerson = new Person(UUID.randomUUID(), name);
        String personContent = this.gson.toJson(personDto);
        System.out.println(personContent);
        Mockito.when(service.createPerson(person)).thenReturn(savedPerson);

        this.mockMvc.perform(
                post("/api/v1/persons")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(personContent)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullname",Matchers .equalTo(name)))
                .andExpect(jsonPath("$.pid", Matchers.notNullValue()));

    }

    @Test
    void updatePerson() {
    }

    @Test
    void deletePerson() {
    }
}
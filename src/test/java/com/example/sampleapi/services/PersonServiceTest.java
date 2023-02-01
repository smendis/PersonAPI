package com.example.sampleapi.services;

import com.example.sampleapi.exceptions.InvalidArgumentException;
import com.example.sampleapi.config.SampleApi;
import com.example.sampleapi.config.WorkCore;
import com.example.sampleapi.logic.ImageValidator;
import com.example.sampleapi.logic.ProfileImageDownloder;
import com.example.sampleapi.models.Person;
import com.example.sampleapi.repositories.iPersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;

import java.util.UUID;

@SpringBootTest
@TestPropertySources({
        @TestPropertySource("classpath:application-test.properties"),
        @TestPropertySource("classpath:work-test.properties")
})
class PersonServiceTest {

    private PersonService personService;

    @Mock
    private iPersonRepository repository;

    @Autowired
    private SampleApi sampleApi;

    @Autowired
    private WorkCore workCore;

    @Mock
    private ImageValidator validator;

    @Mock
    ProfileImageDownloder downloader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.personService = new PersonService(repository, sampleApi, workCore, validator, downloader);
    }

    @Test
    void getAllPeople_should_call_repository_and_validator() {
        //arrange
        //act
        personService.getAllPeople();
        //assert
        Mockito.verify(repository).getAllPeople();
        Mockito.verify(validator).validate();
    }

    @Test
    void getPersonById_should_return_person_object_with_same_id_if_successful() {
        //arrange
        UUID id = UUID.randomUUID();
        String name = "Harry Potter";
        Person person = new Person(id,name);
        Mockito.when(repository.getPersonById(id)).thenReturn(person);
        //act
        Person result = personService.getPersonById(id);
        //assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    void getPersonById_should_throw_if_id_does_not_exist() {
        //arrange
        UUID id = UUID.randomUUID();
        String name = "Harry Potter";
        Person person = new Person(id,name);
        Mockito.when(repository.getPersonById(id)).thenThrow(new RuntimeException());
        //act & assert
        Assertions.assertThatException().isThrownBy(() -> personService.getPersonById(id));
    }

    @Test
    void createPerson_should_throw_if_name_is_empty() {
        //arrange
        Person person = new Person("");
        //act
        Assertions.assertThatThrownBy(() -> {
            personService.createPerson(person);
        }).isInstanceOf(InvalidArgumentException.class);
    }

    @Test
    void createPerson_should_not_throw_if_name_is_not_empty() {
        //arrange
        Person person = new Person("Hendry Brown");
        //act
        Assertions.assertThatNoException().isThrownBy(() -> {
            personService.createPerson(person);
        });
    }

    @Test
    void createPerson_should_add_id_to_person_object() {
        //arrange
        UUID id = UUID.randomUUID();
        String name = "Harry Potter";
        Person person = new Person(name);
        Mockito.when(repository.savePerson(person)).thenReturn(new Person(id, name));
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        //act
        Person savedPerson = personService.createPerson(person);
        //assert
        Mockito.verify(repository).savePerson(personArgumentCaptor.capture());
        Assertions.assertThat(personArgumentCaptor.getValue().getId()).isNull();
        Assertions.assertThat(savedPerson.getId()).isNotNull();
        Assertions.assertThat(person.getName()).isEqualTo(savedPerson.getName());
    }

    @Test
    void updatePerson_should_call_repository_with_same_args() {
        //arrange
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "Rahul John");
        //act
        personService.updatePerson(id, person);
        //assert
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        ArgumentCaptor<Person> argumentCaptor2 = ArgumentCaptor.forClass(Person.class);

        Mockito.verify(repository).updatePerson(argumentCaptor.capture(), argumentCaptor2.capture());
        Assertions.assertThat(argumentCaptor.getValue()).isEqualTo(id);
        Assertions.assertThat(argumentCaptor2.getValue()).isEqualTo(person);
    }

    @Test
    void removePerson_should_call_repository_only_1_time_with_the_same_id_called() {
        //arrange
        UUID id = UUID.randomUUID();
        //act
        personService.removePerson(id);
        //assert
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        ///This verifies the deletePerson on repository was called only once with the id same as passed into the service
        Mockito.verify(repository, Mockito.times(1)).deletePerson(argumentCaptor.capture());
        Assertions.assertThat(argumentCaptor.getValue()).isEqualTo(id);
    }
}
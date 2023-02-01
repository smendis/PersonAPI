package com.example.sampleapi.repositories;

import com.example.sampleapi.models.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.UUID;

@DataJpaTest
@EnableJpaRepositories(basePackages = "com.example.sampleapi.repositories.jpa") // crucial: detect JPA repositories with a base package; it was in the main config file, I copy it here
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryH2Test {

    @Autowired
    @Qualifier("h2")
    private iPersonRepository repository;

    @Test
    @Order(1)
    public void checkForInitialUserCount(){
        List<Person> people = repository.getAllPeople();
        Assertions.assertThat(people).hasSize(0);
    }
    @Test
    @Order(2)
    @Rollback(value = false)
    public void savePerson(){
        Person person = new Person("Harry Porter");
        Person createdPerson = repository.savePerson(person);
        Assertions.assertThat(createdPerson.getId()).isNotNull();
        Assertions.assertThat(createdPerson.getName()).isEqualTo(person.getName());
    }

    @Test
    @Order(3)
    public void getPersonById(){
        Person person = repository.getAllPeople().stream().findFirst().get();

        Person returnedPerson = repository.getPersonById(person.getId());
        Assertions.assertThat(returnedPerson.getName()).isEqualTo(person.getName());
    }

    @Test
    @Order(4)
    public void updatePerson(){
        Person person = repository.getAllPeople().stream().findFirst().get();
        person.setName("Harry Smith");
        repository.updatePerson(person.getId(), person);

        Assertions.assertThat(repository.getAllPeople().stream().findFirst().get().getName()).isEqualTo("Harry Smith");
    }

    @Test
    @Order(5)
    void deletePerson() {
        UUID id = repository.getAllPeople().stream().findFirst().get().getId();
        repository.deletePerson(id);
        Assertions.assertThat(repository.getAllPeople().size()).isEqualTo(0);
    }
}

package com.example.sampleapi.repositories;

import com.example.sampleapi.models.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataMongoTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
@EnableMongoRepositories(basePackages = "com.example.sampleapi.repositories.mongodb")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryMongoTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    @Qualifier("mongodb")
    private iPersonRepository repository;

    @Test
    @Order(1)
    void getAllPeople() {
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
    void updatePerson() {
    }

    @Test
    void deletePerson() {
    }
}
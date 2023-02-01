package com.example.sampleapi.repositories.jpa;

import com.example.sampleapi.models.Person;
import com.example.sampleapi.repositories.iPersonRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("postgres")
public interface PersonRepositoryPostgres extends CrudRepository<Person, UUID>, iPersonRepository {
    @Override
    default List<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        findAll().forEach(p->people.add(p));
        return people;
    }

    @Override
    default Person getPersonById(UUID id) {
        return findById(id).orElseThrow();
    }

    @Override
    default Person savePerson(Person person) {
        person.setId(UUID.randomUUID());
        Person savedPerson = save(person);
        return savedPerson;
    }

    @Override
    default void updatePerson(UUID id, Person person) {
        person.setId(id);
        save(person);
    }

    @Override
    default void deletePerson(UUID id) {
        deleteById(id);
    }
}

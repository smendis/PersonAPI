package com.example.sampleapi.repositories.mongodb;

import com.example.sampleapi.models.Person;
import com.example.sampleapi.repositories.iPersonRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("mongodb")
public interface PersonRepositoryMongo extends MongoRepository<Person, UUID>, iPersonRepository {
    @Override
    default List<Person> getAllPeople(){
        return findAll();
    }

    @Override
    default Person getPersonById(UUID id){
        return findById(id).orElseThrow();
    }

    @Override
    default Person savePerson(Person person){
        person.setId(UUID.randomUUID());
        return save(person);
    }

    @Override
    default void updatePerson(UUID id, Person person){
        person.setId(id);
        save(person);
    }

    @Override
    default void deletePerson(UUID id){
        deleteById(id);
    }
}

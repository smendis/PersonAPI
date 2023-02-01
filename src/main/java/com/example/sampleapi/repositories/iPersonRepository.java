package com.example.sampleapi.repositories;

import com.example.sampleapi.models.Person;

import java.util.List;
import java.util.UUID;

public interface iPersonRepository {

    public List<Person> getAllPeople();

    public Person getPersonById(UUID id);

    public Person savePerson(Person person);

    public void updatePerson(UUID id, Person person);

    public void deletePerson(UUID id);
}

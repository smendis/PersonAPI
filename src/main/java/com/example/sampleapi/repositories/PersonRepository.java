package com.example.sampleapi.repositories;

import com.example.sampleapi.models.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("inmemory")
public class PersonRepository implements iPersonRepository {

    private List<Person> db = new ArrayList<Person>();

    public PersonRepository() {
        Person ann = new Person(UUID.randomUUID(), "Ann Mary");
        Person john = new Person(UUID.randomUUID(), "John Doe");
        Person smith = new Person(UUID.randomUUID(), "Micheal Smith");
        db.add(ann);
        db.add(john);
        db.add(smith);
    }

    public List<Person> getAllPeople(){
        return db;
    }

    public Person getPersonById(UUID id){
        return db.stream().filter(p->p.getId().equals(id)).findAny().orElse(null);
    }

    public Person savePerson(Person person){
        person.setId(UUID.randomUUID());
        db.add(person);
        return person;
    }

    public void updatePerson(UUID id, Person person){
        person.setId(id);
        Person existingPerson =  getPersonById(id);
        int existingIndex = db.indexOf(existingPerson);
        db.add(existingIndex, person);
    }

    public void deletePerson(UUID id){
        Person existingPerson =  getPersonById(id);
        int existingIndex = db.indexOf(existingPerson);
        db.remove(existingIndex);
    }
}

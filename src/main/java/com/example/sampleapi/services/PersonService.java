package com.example.sampleapi.services;

import com.example.sampleapi.exceptions.InvalidArgumentException;
import com.example.sampleapi.config.SampleApi;
import com.example.sampleapi.config.WorkCore;
import com.example.sampleapi.exceptions.PersonNotFoundException;
import com.example.sampleapi.logic.ImageValidator;
import com.example.sampleapi.logic.ProfileImageDownloder;
import com.example.sampleapi.models.Person;
import com.example.sampleapi.repositories.iPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    private iPersonRepository repository;

    private SampleApi properties;

    private WorkCore workProperties;

    @Autowired
    @Lazy
    private ImageValidator imageValidator;

    @Autowired
    @Lazy
    private ProfileImageDownloder imageDownloder;

    @Autowired
    public PersonService(@Qualifier("inmemory") iPersonRepository repository, SampleApi properties, WorkCore workProperties) {
        System.out.println(repository);
        this.repository = repository;
        this.properties = properties;
        this.workProperties = workProperties;
    }

    public PersonService(iPersonRepository repository, SampleApi properties, WorkCore workProperties, ImageValidator validator, ProfileImageDownloder downloader) {
        this.repository = repository;
        this.properties = properties;
        this.workProperties = workProperties;
        this.imageValidator = validator;
        this.imageDownloder = downloader;
    }

    public List<Person> getAllPeople(){

        System.out.println(this.repository);
        System.out.println(properties.getName()+ "@" + properties.getIp());
        System.out.println(workProperties.getEmail() + "~" + workProperties.getPlaceholderImage());
        imageValidator.validate();
        return repository.getAllPeople();
    }

    public Person getPersonById(UUID id){
        return repository.getPersonById(id);
    }

    public Person createPerson(Person newPerson){
        if(newPerson.getName().isBlank()){
            throw new InvalidArgumentException();
        }
        return repository.savePerson(newPerson);
    }

    public void updatePerson(UUID id, Person person){
        repository.updatePerson(id, person);
    }

    public void removePerson(UUID id){
        repository.deletePerson(id);
    }
}

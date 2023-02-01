package com.example.sampleapi.controllers;

import com.example.sampleapi.models.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private List<Student> students = Arrays.asList(
            new Student(1L, "Sonali Mendis"),
            new Student(2L, "Nethmi Vimansa"),
            new Student(3L, "Sithumi Malinsa")
    );
    @GetMapping
    public List<Student> getAllStudent(){
        return students;
    }
    @GetMapping("{id}")
    public Student getStudent(@PathVariable("id") Long id) throws Exception{
        return students.stream()
                .filter(s -> id.equals(s.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Stident "+id+" not found"));
    }
}

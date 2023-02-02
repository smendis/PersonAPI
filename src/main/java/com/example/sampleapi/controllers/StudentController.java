package com.example.sampleapi.controllers;

import com.example.sampleapi.models.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private List<Student> students;

    public StudentController() {
        this.students = new ArrayList<>();
        this.students.add(new Student(1L, "Sonali Mendis"));
        this.students.add(new Student(2L, "Nethmi Vimansa"));
        this.students.add(new Student(3L, "Sithumi Malinsa"));
    }

    @GetMapping
    public List<Student> getAllStudent(){
        return students;
    }
    @GetMapping("{id}")
    public Student getStudent(@PathVariable("id") Long id) throws Exception{
        return this.students.stream()
                .filter(s -> id.equals(s.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Stident "+id+" not found"));
    }
    
    @PostMapping
    public Student createStudent(@RequestBody Student student){
        student.setStudentId(this.students.size()+1L);
        boolean status = this.students.add(student);
        if(status){
            return student;
        }else{
            throw new IllegalStateException("Unable to create a new student");
        }
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student){
        Student existingStudent = this.students.stream().filter(s -> id.equals(s.getStudentId())).findFirst().orElseThrow(() -> new IllegalStateException("Student " + id + " not found"));
        int index = this.students.indexOf(existingStudent);
        this.students.add(index, student);
        return student;
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id){
        Student student = this.students.stream().filter(s -> id.equals(s.getStudentId())).findFirst().orElseThrow(() -> new IllegalStateException("Student " + id + " not found"));
        boolean status = this.students.remove(student);
        if(!status){
            throw new IllegalStateException("Unable to delete the student "+id);
        }
    }
}

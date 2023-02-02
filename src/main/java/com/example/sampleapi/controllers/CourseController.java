package com.example.sampleapi.controllers;

import com.example.sampleapi.models.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private List<Course> courses = Arrays.asList(
            new Course(1L, "Java Spring Boot"),
            new Course(2L, "Mongo DB"),
            new Course(3L, "Kafka")
    );
    @GetMapping
    public List<Course> getAllCourses(){
        return courses;
    }

    @GetMapping("{id}")
    public Course getCourse(@PathVariable("id") Long id) throws Exception{
        return courses.stream()
                .filter(c -> id.equals(c.getCourseId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Course "+id+" not found"));
    }
}

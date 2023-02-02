package com.example.sampleapi.controllers;

import com.example.sampleapi.models.Course;
import com.example.sampleapi.models.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private List<Course> courses;

    public CourseController() {
        this.courses =  new ArrayList<>();
        courses.add(new Course(1L, "Java Spring Boot"));
        courses.add(new Course(2L, "Mongo DB"));
        courses.add(new Course(3L, "Kafka"));
    }

    @GetMapping
    public List<Course> getAllCourses(){
        return courses;
    }

    @GetMapping("{id}")
    public Course getCourse(@PathVariable("id") Long id) throws Exception{
        return this.courses.stream()
                .filter(c -> id.equals(c.getCourseId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Course "+id+" not found"));
    }
    @PostMapping
    public Course createCourse(@RequestBody Course course){
        course.setCourseId(courses.size()+1L);
        boolean status = this.courses.add(course);
        if(status){
            return course;
        }else{
            throw new IllegalStateException("Unable to create a new course");
        }
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable("id") Long id, @RequestBody Course course){
        Course existingCourse = this.courses.stream().filter(c -> id.equals(c.getCourseId())).findFirst().orElseThrow(() -> new IllegalStateException("Course " + id + " not found"));
        int index = this.courses.indexOf(existingCourse);
        this.courses.add(index, course);
        return course;
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Long id){
        Course course = this.courses.stream().filter(c -> id.equals(c.getCourseId())).findFirst().orElseThrow(() -> new IllegalStateException("Course " + id + " not found"));
        boolean status = this.courses.remove(course);
        if(!status){
            throw new IllegalStateException("Unable to delete the course "+id);
        }
    }
}

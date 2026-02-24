package vn.edu.hcmut.cse.adse.lab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService service;

    // GET http://localhost:8080/api/students
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAll();
    }

    // GET http://localhost:8080/api/students/{id}
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id) {
        return service.getById(id);
    }

}
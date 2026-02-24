package vn.edu.hcmut.cse.adse.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentWebController {

    @Autowired
    private StudentService service;

    // GET http://localhost:8080/students?keyword=abc
    @GetMapping
    public String getAllStudents(
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        List<Student> students;

        if (keyword != null && !keyword.trim().isEmpty()) {
            students = service.searchByName(keyword.trim());
        } else {
            students = service.getAll();
        }

        model.addAttribute("dsSinhVien", students);
        model.addAttribute("keyword", keyword);
        return "students"; // templates/students.html
    }
}
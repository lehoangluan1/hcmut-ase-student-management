package vn.edu.hcmut.cse.adse.lab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentWebController {

    @Autowired
    private StudentService service;

    // Regex for numeric id: only digits
    private static final String ID_NUM = "\\d+";

    // =========================
    // LIST (default page): /students?keyword=abc
    // =========================
    @GetMapping
    public String getAllStudents(
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        String kw = (keyword == null) ? "" : keyword.trim();

        List<Student> students;
        if (!kw.isEmpty()) {
            students = service.searchByName(kw);
        } else {
            students = service.getAll();
        }

        model.addAttribute("dsSinhVien", students);
        model.addAttribute("keyword", kw);
        return "students"; // templates/students.html
    }

    // =========================
    // LIST page: /students/list?keyword=abc
    // =========================
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        String kw = (keyword == null) ? "" : keyword.trim();

        List<Student> students;
        if (!kw.isEmpty()) {
            students = service.searchByName(kw);
        } else {
            students = service.getAll();
        }

        model.addAttribute("dsSinhVien", students);
        model.addAttribute("keyword", kw);
        return "students/list";
    }

    // =========================
    // DETAIL: /students/{id}
    // =========================
    @GetMapping("/{id:" + ID_NUM + "}")
    public String detail(@PathVariable String id, Model model) {
        Student s = service.getByIdOrThrow(id);
        model.addAttribute("student", s);
        return "students/detail";
    }

    // =========================
    // CREATE: show form
    // =========================
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("mode", "create");
        return "students/form";
    }

    // =========================
    // CREATE: submit
    // =========================
    @PostMapping
    public String create(@ModelAttribute Student student, RedirectAttributes ra) {
        service.create(student);
        ra.addFlashAttribute("msg", "Tạo sinh viên thành công!");
        return "redirect:/students/list";
    }

    // =========================
    // EDIT: show form
    // =========================
    @GetMapping("/{id:" + ID_NUM + "}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Student s = service.getByIdOrThrow(id);
        model.addAttribute("student", s);
        model.addAttribute("mode", "edit");
        return "students/form";
    }

    // =========================
    // EDIT: submit
    // =========================
    @PostMapping("/{id:" + ID_NUM + "}")
    public String update(@PathVariable String id, @ModelAttribute Student student, RedirectAttributes ra) {
        service.update(id, student);
        ra.addFlashAttribute("msg", "Cập nhật thành công!");
        return "redirect:/students/" + id;
    }

    // =========================
    // DELETE
    // =========================
    @PostMapping("/{id:" + ID_NUM + "}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        service.delete(id);
        ra.addFlashAttribute("msg", "Xóa thành công!");
        return "redirect:/students/list";
    }
}
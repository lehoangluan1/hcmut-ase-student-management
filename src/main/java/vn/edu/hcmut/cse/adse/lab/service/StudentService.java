package vn.edu.hcmut.cse.adse.lab.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return repo.findAll();
        return repo.findByNameContainingIgnoreCase(keyword.trim());
    }

    public Student getByIdOrThrow(String id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found: " + id));
    }

    @Transactional
    public Student create(Student s) {
        // id nhập tay -> đảm bảo không trùng
        if (repo.existsById(s.getId())) {
            throw new RuntimeException("Student id already exists: " + s.getId());
        }
        return repo.save(s);
    }

    @Transactional
    public Student update(String id, Student form) {
        Student existing = getByIdOrThrow(id);
        // No change id when update
        existing.setName(form.getName());
        existing.setEmail(form.getEmail());
        existing.setAge(form.getAge());
        return repo.save(existing);
    }

    @Transactional
    public void delete(String id) {
        if (!repo.existsById(id)) throw new RuntimeException("Student not found: " + id);
        repo.deleteById(id);
    }
}
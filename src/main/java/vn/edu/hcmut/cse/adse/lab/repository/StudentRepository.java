package vn.edu.hcmut.cse.adse.lab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hcmut.cse.adse.lab.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findByNameContainingIgnoreCase(String keyword);
}

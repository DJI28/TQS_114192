package com.tqs.lab6_1.repositories;

import com.tqs.lab6_1.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByName(String name);
    Student findByNumber(int number);
    Student findByEmail(String email);
    Student findByBirthdate(LocalDate birthdate);
}

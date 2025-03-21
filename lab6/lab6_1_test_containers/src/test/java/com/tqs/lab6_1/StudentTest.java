package com.tqs.lab6_1;

import com.tqs.lab6_1.repositories.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import org.springframework.test.context.DynamicPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.DynamicPropertyRegistry;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import com.tqs.lab6_1.entities.Student;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentTest {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withDatabaseName("student_test")
            .withUsername("user001")
            .withPassword("password001");

    @Autowired
    private StudentRepository studentRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Test
    @DisplayName("Add student")
    @Order(1)
    public void addStudent() {
        Student student = new Student( 123456, "John Doe", "john@student.com", LocalDate.of(2000, 1, 1), 'M');
        studentRepository.save(student);
    }

    @Test
    @DisplayName("Retrieve student by number")
    @Order(2)
    public void retrieveStudentByNumber() {
        Student student = studentRepository.findByNumber(123456);
        Student compare = new Student( 123456, "John Doe", "john@student.com", LocalDate.of(2000, 1, 1), 'M');
        assertThat(student.toString()).isEqualTo(compare.toString());
    }

    @Test
    @DisplayName("Update and retrieve student")
    @Order(3)
    public void updateStudent() {
        Student student = studentRepository.findByNumber(123456);
        student.setName("Jane Doe");
        student.setEmail("jane@student.com");
        student.setSex('F');
        studentRepository.save(student);
        student = studentRepository.findByNumber(123456);
        Student compare = new Student( 123456, "Jane Doe", "jane@student.com", LocalDate.of(2000, 1, 1), 'F');
        assertThat(student.toString()).isEqualTo(compare.toString());
    }

    @Test
    @DisplayName("Delete student")
    @Order(4)
    public void deleteStudent() {
        Student student = studentRepository.findByNumber(123456);
        assertThat(student).isNotNull();
        studentRepository.delete(student);
        assertThat(studentRepository.findByNumber(123456)).isNull();
    }
}

package com.tqs.lab6_1;

import com.tqs.lab6_1.entities.Student;
import com.tqs.lab6_1.repositories.StudentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentMigrationTest {

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
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Test
    @DisplayName("Verify migration")
    @Order(0)
    public void verifyMigration() {
        assertThat(studentRepository.count()).isEqualTo(10);
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
        Student student1 = studentRepository.findByNumber(123456);
        Student compare1 = new Student( 123456, "John Doe", "john@student.com", LocalDate.of(2000, 1, 1), 'M');
        Student student2 = studentRepository.findByNumber(10);
        Student compare2 = new Student(10, "Jack Wilson", "jack@student.com", LocalDate.of(2000, 10, 1), 'M');
        assertThat(student1.toString()).isEqualTo(compare1.toString());
        assertThat(student2.toString()).isEqualTo(compare2.toString());
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
        Student student1 = studentRepository.findByNumber(123456);
        assertThat(student1.toString()).isNotNull();
        Student student2 = studentRepository.findByNumber(1);
        assertThat(student2.toString()).isNotNull();
        studentRepository.delete(student1);
        studentRepository.delete(student2);
        assertThat(studentRepository.findByNumber(123456)).isNull();
        assertThat(studentRepository.findByNumber(1)).isNull();
    }
}

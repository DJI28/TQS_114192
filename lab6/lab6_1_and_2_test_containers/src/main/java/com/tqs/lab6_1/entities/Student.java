package com.tqs.lab6_1.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Student {

    @Column(name = "student_number", nullable = false)
    @Id
    private int number;

    @Column(name = "student_name", nullable = false)
    private String name;

    @Column(name = "student_email", nullable = false)
    private String email;

    @Column(name = "student_birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "student_sex", nullable = false)
    private char sex;

    public Student() {
    }

    public Student(int number, String name, String email, LocalDate birthdate, char sex) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.birthdate = birthdate;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public char getSex() {
        return sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthdate=" + birthdate +
                ", sex=" + sex +
                '}';
    }
}

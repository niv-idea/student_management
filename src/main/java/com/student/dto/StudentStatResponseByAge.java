package com.student.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentStatResponseByAge {
    private Integer age;
    private List<StudentDetails> students;
    public StudentStatResponseByAge(Integer age, List<StudentDetails> details) {
        this.age =age;
        this.students = details;
    }
}

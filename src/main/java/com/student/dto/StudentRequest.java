package com.student.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentRequest {
    private String name;
    private Integer age;
    private String marks;
    private String city;
    private Integer studentClass;
}

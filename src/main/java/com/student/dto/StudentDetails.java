package com.student.dto;

import lombok.Data;

@Data
public class StudentDetails {
    private Integer id;
    private String name;
    private String marks;
    private String city;
}
//we are not showing or direct expose our main fields they just copies which really needed
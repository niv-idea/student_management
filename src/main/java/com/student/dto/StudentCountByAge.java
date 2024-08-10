package com.student.dto;

import lombok.Data;

@Data

public class StudentCountByAge {
    private Integer age;
    private Long count;
    public StudentCountByAge(Integer age, Long count){
        this.age=age;
        this.count=count;
    }

}

package com.student.service;


import com.student.dto.*;
import com.student.entity.Student;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;

import java.util.List;
import java.util.Map;

public interface StudentService {
//now create here CRUD method for implementation
    Integer addStudent(StudentRequest request);

    Integer updateStudent(StudentRequest request, int id);

    Integer deleteStudentById(int id);

    StudentResponce getStudent(int id);

    List<StudentResponce > getAllStudents();

    List<StudentResponce> getStudentsByAge(Integer age);

    // write api that will get stat of student by age;
    List<StudentStatResponseByAge> getAllStudentsByAge();

    List<StudentResponce> findStudentsByMarks(String marks);

    List<Map<String, Object>> countOfStudentByAgeWise();

    // @Nivruttee ->write API that will count avg of all age
     Double getAverageAge();

     List<StudentResponce> findStudentsByCity(String city);

     List<GroupOfStudentsClassVise> studentsByClassViseAvg();

     String updateGenderOfStudent(Integer studentId);
    //@Chandradip -> write below API

    //    {
//        "data": [
//        {
//            "studentClass": 3,
//                "studentCount": 10,
//                "avgAge": 8.5
//        },
//        {
//            "studentClass": 2,
//                "studentCount": 10,
//                "avgAge": 8.5
//        }
//    ]
//    }
    StudentCountByAge countStudentsByAge(Integer age);
}

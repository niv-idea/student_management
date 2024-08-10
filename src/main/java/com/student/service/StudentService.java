package com.student.service;


import com.student.dto.StudentCountByAge;
import com.student.dto.StudentRequest;
import com.student.dto.StudentResponce;
import com.student.dto.StudentStatResponseByAge;
import com.student.entity.Student;

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

    // write api that will get stat of studnet by age;
    List<StudentStatResponseByAge> getAllStudentsByAge();

    List<StudentResponce> findStudentsByMarks(String marks);

    List<Map<String, Object>> countOfStudentByAgeWise();

    StudentCountByAge countStudentsByAge(Integer age);
}

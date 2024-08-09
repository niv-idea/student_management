package com.student.controller;

import com.student.dto.ResponseWrapper;
import com.student.dto.StudentRequest;
import com.student.dto.StudentResponce;
import com.student.dto.StudentStatResponseByAge;
import com.student.entity.Student;
import com.student.enums.Status;
import com.student.service.StudentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student/v1")
public class StudentController {
    @Autowired
    StudentService service;

    //let's create here the endpoints of applications means final crud operations in restApi

    @PostMapping("/addStudent")

    public ResponseEntity<ResponseWrapper> addStudent(@RequestBody StudentRequest request){
        System.out.println("request received...");
        System.out.println(request.toString());
        Integer id= service.addStudent(request);
        return new ResponseEntity<>(new ResponseWrapper(Status.SUCCESS,id), HttpStatus.OK);

    }
    @PostMapping("/{id}/update")
    public ResponseEntity<ResponseWrapper> updateStudent(@RequestBody StudentRequest request, @PathVariable(value = "id")Integer id){
      Integer updateId=  service.updateStudent(request,id);
      return new ResponseEntity<>(new ResponseWrapper(Status.SUCCESS,updateId),HttpStatus.OK);
    }
    @GetMapping(value = "/get")
    public ResponseEntity<ResponseWrapper> getStudent(@RequestParam(value = "id") Integer id){
        return new  ResponseEntity<>(new ResponseWrapper(Status.SUCCESS,service.getStudent(id)), HttpStatus.OK);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<ResponseWrapper> fetchAllStudents(){
        List<StudentResponce> students=service.getAllStudents();
        ResponseWrapper responseWrapper=new ResponseWrapper(Status.SUCCESS,students);
        return new ResponseEntity<>(responseWrapper,HttpStatus.OK);
       // return new ResponseEntity<>(new ResponseWrapper(Status.SUCCESS,service.getAllStudents()),HttpStatus.OK);
    }
    //it will show us similar age students list
    @GetMapping(value = "/age")
    public ResponseEntity<ResponseWrapper> fetchAllStudentsByAge(@RequestParam(value = "age") Integer age){
        List<StudentResponce> students= service.getStudentsByAge(age);
        ResponseWrapper responseWrapper=new ResponseWrapper(Status.SUCCESS,students);
        return new ResponseEntity<>(responseWrapper,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<ResponseWrapper> deleteStudentDetails(@RequestParam(value = "id")Integer id){
        Integer studentId=service.deleteStudentById(id);
        ResponseWrapper responseWrapper=new ResponseWrapper(Status.SUCCESS,studentId);
        return new ResponseEntity<>(responseWrapper,HttpStatus.OK);
        //return new ResponseEntity<>(new ResponseWrapper(Status.SUCCESS, service.deleteStudentById(id)),HttpStatus.OK);
    }

    @GetMapping("/statistic/age")
    public ResponseEntity<ResponseWrapper> getStatByAge() {
        List<StudentStatResponseByAge> allStudentsByAge = service.getAllStudentsByAge();
        return ResponseEntity.ok().body(new ResponseWrapper(Status.SUCCESS, allStudentsByAge));
    }

    @GetMapping(value = "/get/marks/{marks}")
    public ResponseEntity<ResponseWrapper> getStudentsByMarks(@PathVariable String marks) {
        List<StudentResponce> responces = service.findStudentsByMarks(marks);
        return ResponseEntity.ok(new ResponseWrapper(Status.SUCCESS, responces));
    }
    @GetMapping(value = "/stat/age")
    public ResponseEntity<ResponseWrapper> getStudentsCountByAge() {
        List<Map<String, Object>> stringObjectMap = service.countOfStudentByAgeWise();
        return ResponseEntity.ok().body(new ResponseWrapper(Status.SUCCESS, stringObjectMap));
    }
}

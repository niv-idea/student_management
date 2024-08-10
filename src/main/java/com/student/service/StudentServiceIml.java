package com.student.service;

import com.student.dto.*;
import com.student.entity.Student;
import com.student.exception.StudentException;
import com.student.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class StudentServiceIml implements StudentService{
    @Autowired
     private StudentRepository studentRepository;


    //first of all we will define some rules for operation with data
    private Student convertStudent(StudentRequest request){
        Student student= new Student();
        if(request.getName()==null){
            throw new StudentException("please provide name ");
        }
        student.setName(request.getName());
        if(request.getAge()==null){
            throw new StudentException("please provide age of student ");
        }
        student.setAge(request.getAge());
        if(request.getMarks()!=null){
            student.setMarks(request.getMarks());
        }
        if(request.getCity()!=null){
          student.setCity(request.getCity());
        }
        return student;
    }

    @Override
    public Integer addStudent(StudentRequest request) {
        Student student=convertStudent(request);
        Student saveStudent=studentRepository.save(student);
       return saveStudent.getId();
    }


    @Override
    public Integer updateStudent(StudentRequest request,int id) {
       Student student= studentRepository.findById(id)
               .orElseThrow(()->new StudentException("student is not found"));
        //if student is present of particular if then we can perform operation
        if(request.getName()!=null){
            student.setName(request.getName());
        }
        if(request.getAge()!=null){
            student.setAge(request.getAge());
        }
        if(request.getMarks()!=null){
            student.setMarks(request.getMarks());
        }
        if(request.getCity()!=null){
            student.setCity(request.getCity());
        }
        if(request.getStudentClass()!=null){
            student.setStudentClass(request.getStudentClass());
        }
        //now lastly  we have to save this changes
        studentRepository.save(student);
        return student.getId();
    }

    @Override
    public Integer deleteStudentById(int id) {
       Student student= studentRepository.findById(id).orElseThrow(()->new StudentException("Student not found"));
        //if it is present then delete that student details
        studentRepository.delete(student);
        return  student.getId();
    }

    @Override
    public StudentResponce getStudent(int id) {
Student student=studentRepository.findById(id).orElseThrow(()->new StudentException("student is not found"));
        return getStudentResponse(student);
    }

    @Override
    public List<StudentResponce> getAllStudents() {
        //this is java 8 feature /lambda expression
        //return  studentRepository.findAll().stream().map(this::getStudentResponse).collect(Collectors.toList());
        List<StudentResponce> responses=new ArrayList<>();
       List<Student> student = studentRepository.findAll();
       for(Student student1 :student){
           StudentResponce studentResponce=getStudentResponse(student1);
           responses.add(studentResponce);
       }
        return responses;
    }

    @Override
    public List<StudentResponce> getStudentsByAge(Integer age) {
        List<StudentResponce> responses=new ArrayList<>();
       List<Student> students=studentRepository.findByAge(age);
        for(Student student:students){
            StudentResponce studentResponce=getStudentResponse(student);
            responses.add(studentResponce);
        }
        return responses;
    }

    @Override
    public List<StudentStatResponseByAge> getAllStudentsByAge() {
        List<StudentStatResponseByAge> response = new ArrayList<>();
        Map<Integer, List<Student>> ageVsStudentMap = studentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Student::getAge,
                        Collectors.mapping(student -> student, Collectors.toList())));
        ageVsStudentMap.forEach((k,v )-> {
            response.add(new StudentStatResponseByAge(k, v.stream().map(this::getStudentDetailsFromEntity).collect(Collectors.toList())) );
        });
        //we have to convert in java  7
        return response;
    }

    @Override
    public List<StudentResponce> findStudentsByMarks(String marks) {
        return studentRepository.findByMarks(marks)
                .stream()
                .map(this::getStudentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> countOfStudentByAgeWise() {
        Map<Integer, Integer> ageVsCountMap = studentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Student::getAge,
                        Collectors.collectingAndThen(Collectors.toList(), List::size)));

        List<Map<String, Object>> response = new ArrayList<>();
        ageVsCountMap.forEach((age, count) -> {
            Map<String, Object> responseMap =  new HashMap<>();
            responseMap.put("age", age);
            responseMap.put("count", count);
            response.add(responseMap);
        });
        return response;
    }

    @Override
    public Double getAverageAge() {
        List<Student> students = studentRepository.findAll();
            double totalAge = students.stream().mapToInt(Student::getAge).sum();
            return totalAge / students.size();

    }

    @Override
    public List<StudentResponce> findStudentsByCity(String city) {
        return studentRepository.findByCity(city)
                .stream()
                .map(this::getStudentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupOfStudentsClassVise> studentsByClassViseAvg() {
         List<GroupOfStudentsClassVise> listOfStudent=new ArrayList<>();
//         List<Student> students = studentRepository.findAll();
//         Map<Integer,List<Student>> classVsStudentMap= new HashMap<>();
//         for(Student student : students){
////            classVsStudentMap.getOrDefault(student.getStudentClass(), new ArrayList<>());
//             //above line and below if -else block are same
//             if (classVsStudentMap.containsKey(student.getStudentClass())) { //1 //2
//                 List<Student> st = classVsStudentMap.get(student.getStudentClass());
//                 st.add(student);
//                 classVsStudentMap.put(student.getStudentClass(), st);
//             } else {
//                 List<Student> st = new ArrayList<>();
//                 st.add(student);
//                 classVsStudentMap.put(student.getStudentClass(), st);
//             }
//         }
//
//         for (Map.Entry<Integer, List<Student>> entry : classVsStudentMap.entrySet()) {
//             GroupOfStudentsClassVise studentsClassVise = new GroupOfStudentsClassVise();
//             studentsClassVise.setStudentClass(entry.getKey());
//             studentsClassVise.setStudentCount(entry.getValue().size());
//             int totalAge = 0;
//             for (Student student : entry.getValue()) {
//                 totalAge +=student.getAge();
//             }
//             Double avgAge = (double) totalAge /entry.getValue().size();
//             studentsClassVise.setAvgAge(avgAge);
//             listOfStudent.add(studentsClassVise);
//         }
//        return  listOfStudent;

        // java 8 => solution
        Map<Integer, GroupOfStudentsClassVise> collect = studentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Student::getStudentClass, Collectors.collectingAndThen(Collectors.toList(), students -> {
                    GroupOfStudentsClassVise studentsClassVise = new GroupOfStudentsClassVise();
                    Optional<Integer> sum = students.stream().map(Student::getAge).reduce(Integer::sum);
                    sum.ifPresent(integer -> studentsClassVise.setAvgAge((double) (integer / students.size())));
                    studentsClassVise.setStudentClass(students.get(0).getStudentClass());
                    studentsClassVise.setStudentCount(students.size());
                    return studentsClassVise;
                })));

        return new ArrayList<>(collect.values());

    }

    @Override
    public StudentCountByAge countStudentsByAge(Integer age) {
        //int count = studentRepository.findByAge(age).size();
        //return new StudentCountByAge(age, (long) count);
        long count=   studentRepository.countByAge(age);
         return new StudentCountByAge(age,count);
    }

    private StudentDetails getStudentDetailsFromEntity(Student student) {
        StudentDetails details = new StudentDetails();
        details.setId(student.getId());
        details.setName(student.getName());
        details.setCity(student.getCity());
        details.setMarks(student.getMarks());
        return details;
    }

    //firstly you have to provide all the details related to that particular student to response class then that class can fulfill request for client
    private StudentResponce getStudentResponse(Student student){
        StudentResponce studentResponce=new StudentResponce();
        studentResponce.setId(student.getId());
        studentResponce.setName(student.getName());
        studentResponce.setAge(student.getAge());
        studentResponce.setMarks(student.getMarks());
        studentResponce.setCity(student.getCity());
        studentResponce.setStudentClass(student.getStudentClass());
        return studentResponce;
     }
}

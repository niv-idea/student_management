package com.student.repo;

import com.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    List<Student> findByAge(Integer age);

    @Query(value = "SELECT * FROM student where marks = :marks", nativeQuery = true)
    List<Student> findByMarks(@Param("marks") String marks);

    long countByAge(Integer age);

    @Query(value = "SELECT * FROM student where city = :city" , nativeQuery = true)
    List<Student> findByCity(@Param("city")String city);
}

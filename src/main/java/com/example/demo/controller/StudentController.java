package com.example.demo.controller;

import java.util.List;

import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {


    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }




    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }


    @PostMapping("/student")
    public ResponseEntity<StudentDto> getAllStudents(@RequestBody StudentDto studentDto) {
        StudentDto std = studentService.addStudent(studentDto);
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }


    @PutMapping("/student/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable(name = "id") Integer id,
                                                    @RequestBody StudentDto student) {
        StudentDto std = studentService.updateStudent(id, student);
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }


    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable(name = "id") Integer studentId) {
        String message = studentService.deleteStudent(studentId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
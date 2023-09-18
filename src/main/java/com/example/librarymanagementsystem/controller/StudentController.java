package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.requestDTO.StudentRequest;
import com.example.librarymanagementsystem.dto.responseDTO.StudentResponse;
import com.example.librarymanagementsystem.service.impl.StudentServiceImpl;
import com.example.librarymanagementsystem.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentServiceImpl studentServiceImpl;

    @PostMapping("/add")
    public ResponseEntity addStudent(@RequestBody StudentRequest studentRequest) {    //we use input dto here

        StudentResponse response = studentServiceImpl.addStudent(studentRequest);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity getStudent(@RequestParam("id") int regNo) {
        Student student = studentServiceImpl.getStudent(regNo);
        if(student != null) {
            return new ResponseEntity(student, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Invalid Id", HttpStatus.BAD_REQUEST);
    }

    //  delete a student  ---> regNo

    // update the age of the student   --> regNO, age

    // get all the students in the Db

    // get list of all male students in the Db

    @GetMapping("/get-males")
    public List<String> getAllMales() {
        List<String> males = studentServiceImpl.getAllMales();
        return males;
    }
}


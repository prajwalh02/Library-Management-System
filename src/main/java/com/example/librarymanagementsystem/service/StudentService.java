package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.requestDTO.StudentRequest;
import com.example.librarymanagementsystem.dto.responseDTO.StudentResponse;
import com.example.librarymanagementsystem.model.Student;

import java.util.List;

public interface StudentService {

    public StudentResponse addStudent(StudentRequest studentRequest);

    public Student getStudent(int regNo);

    public List<String> getAllMales();
}

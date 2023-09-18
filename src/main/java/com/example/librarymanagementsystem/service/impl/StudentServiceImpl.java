package com.example.librarymanagementsystem.service.impl;

import com.example.librarymanagementsystem.Enum.Gender;
import com.example.librarymanagementsystem.dto.requestDTO.StudentRequest;
import com.example.librarymanagementsystem.dto.responseDTO.StudentResponse;
import com.example.librarymanagementsystem.model.LibraryCard;
import com.example.librarymanagementsystem.repository.StudentRepository;
import com.example.librarymanagementsystem.model.Student;
import com.example.librarymanagementsystem.service.StudentService;
import com.example.librarymanagementsystem.transformer.LibraryCardTransformer;
import com.example.librarymanagementsystem.transformer.StudentTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    public StudentResponse addStudent(StudentRequest studentRequest) {

        // convert DTO ---> model
//        Student student = new Student();
//        student.setName(studentRequest.getName());
//        student.setAge(studentRequest.getAge());
//        student.setGender(studentRequest.getGender());
//        student.setEmail(studentRequest.getEmail());

        // calling transformer
        Student student = StudentTransformer.StudentRequestToStudent(studentRequest);
        LibraryCard card = LibraryCardTransformer.prepareLibraryCard();

        card.setStudent(student);
        student.setLibraryCard(card);
        Student saveStudent = studentRepository.save(student);    //it will save both the student and library card in th Db

        //saved model to response dto, i.e convert model to dto
        StudentResponse studentResponse = StudentTransformer.StudentToStudentResponse(saveStudent);

        return studentResponse;

    }

    public Student getStudent(int regNo) {
        Optional<Student> studentOptional = studentRepository.findById(regNo);

        if(studentOptional.isPresent()) {
            return studentOptional.get();
        }
        return null;
    }

    public List<String> getAllMales() {

        List<String> names = new ArrayList<>();
        List<Student> students = studentRepository.findByGender(Gender.MALE);
        for(Student s : students) {
            names.add(s.getName());
        }
        return names;
    }


}

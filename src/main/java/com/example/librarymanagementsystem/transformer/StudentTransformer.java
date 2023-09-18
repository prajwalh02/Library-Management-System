package com.example.librarymanagementsystem.transformer;

import com.example.librarymanagementsystem.dto.requestDTO.StudentRequest;
import com.example.librarymanagementsystem.dto.responseDTO.LibraryCardResponse;
import com.example.librarymanagementsystem.dto.responseDTO.StudentResponse;
import com.example.librarymanagementsystem.model.Student;

public class StudentTransformer {

    //convert input Dto --> model
    public static Student StudentRequestToStudent(StudentRequest studentRequest) {
        return Student.builder()
                .name(studentRequest.getName())
                .age(studentRequest.getAge())
                .gender(studentRequest.getGender())
                .email(studentRequest.getEmail()).build();
    }


    //convert model --> output Dto
    public static StudentResponse StudentToStudentResponse(Student student) {

        LibraryCardResponse cardResponse = LibraryCardResponse.builder()
                .cardNo(student.getLibraryCard().getCardNo())
                .cardStatus(student.getLibraryCard().getCardStatus())
                .issueDate(student.getLibraryCard().getIssueDate())
                .build();

        return StudentResponse.builder()
                .name(student.getName())
                .email(student.getEmail())
                .libraryCardResponse(cardResponse)
                .build();
    }

}

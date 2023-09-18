package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.Enum.Gender;
import com.example.librarymanagementsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {  //entity, datatype of primary key of table

    List<Student> findByGender(Gender gender);

    //sequence will matter in this GenderAndEmail, so 1st gender then email
    Student findByGenderAndEmail(Gender gender, String email);
}

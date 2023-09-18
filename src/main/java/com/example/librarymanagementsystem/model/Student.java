package com.example.librarymanagementsystem.model;

import com.example.librarymanagementsystem.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity       //to tell the JPA that it is a model class
//@Table(name = "student_info")          //for giving new name for a column instead of student --> student_info
@Builder
public class Student {

    @Id            //for to telling that this is a Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int regNo;

    String name;

    int age;

    @Column(unique = true, nullable = false)   //unique means email should be unique & false means it cannot be null
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender;        //enum

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    LibraryCard libraryCard;

}

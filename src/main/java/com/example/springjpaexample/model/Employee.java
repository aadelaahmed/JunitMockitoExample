package com.example.springjpaexample.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.web.bind.annotation.PostMapping;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    @Max(50)
    @Positive
    private int age;

    @Column(name = "salary")
    @Positive
    private Long salary;

    @Column(name = "email")
    @Email
    private String email;

}

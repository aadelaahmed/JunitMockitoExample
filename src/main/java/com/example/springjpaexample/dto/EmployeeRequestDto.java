package com.example.springjpaexample.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployeeRequestDto {
    private String name;

    private int age;

    private Long salary;

    private String email;
}

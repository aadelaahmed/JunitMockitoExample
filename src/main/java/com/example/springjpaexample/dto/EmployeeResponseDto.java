package com.example.springjpaexample.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String email;
    private int age;
    private Long salary;
}

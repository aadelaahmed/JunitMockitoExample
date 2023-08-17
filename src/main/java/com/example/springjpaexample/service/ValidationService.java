package com.example.springjpaexample.service;

import com.example.springjpaexample.dto.EmployeeRequestDto;
import com.example.springjpaexample.model.Employee;
import com.example.springjpaexample.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidationService {
    @Autowired
    EmployeeRepository employeeRepository;
    public void validateEmployeeName(EmployeeRequestDto employeeRequestDto){
        Employee emp = employeeRepository.findEmployeeByName(employeeRequestDto.getName());
        if (emp != null)
            throw new RuntimeException("Employee is already existed with same name");
    }

    public void validateEmployee(EmployeeRequestDto employeeRequestDto) {
        validateEmployeeName(employeeRequestDto);
        validateEmployeeAge(employeeRequestDto.getAge());
        validateEmployeeSalary(employeeRequestDto.getSalary());
    }

    public void validateEmployeeSalary(Long salary) {
        if (salary <= 0)
            throw new RuntimeException("Employee's salary should be greater than 0");
    }

    public void validateEmployeeAge(int age) {
        if (age <= 0 || age > 50)
            throw new RuntimeException("Employee's age should be between 0 and 50");
    }

    public Employee validateExistEmployee(Long employeeId){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty())
            throw new EntityNotFoundException("Can't find employee with thid id: " + employeeId);
        else
            return optionalEmployee.get();
    }

}

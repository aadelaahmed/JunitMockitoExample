package com.example.springjpaexample.service;

import com.example.springjpaexample.dto.EmployeeRequestDto;
import com.example.springjpaexample.dto.EmployeeResponseDto;
import com.example.springjpaexample.model.Employee;
import com.example.springjpaexample.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ValidationService validationService;
    ModelMapper modelMapper = new ModelMapper();
    public EmployeeResponseDto addNewEmployee(EmployeeRequestDto employeeRequestDto) {
        //firstly,we need to validate the employee.
        validationService.validateEmployee(employeeRequestDto);
        Employee employee = modelMapper.map(employeeRequestDto,Employee.class);
        return modelMapper.map(employeeRepository.save(employee),EmployeeResponseDto.class);
    }
    public List<EmployeeRequestDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeRequestDto> employeeRequestDtos = modelMapper.map(employees,new TypeToken<List<EmployeeRequestDto>>() {}.getType());
        return employeeRequestDtos;
    }
    public EmployeeResponseDto getEmployeeById(Long employeeId){
        Employee employee = validationService.validateExistEmployee(employeeId);
        //map here from employee to employeedto
        EmployeeResponseDto employeeResponseDto = modelMapper.map(employee, EmployeeResponseDto.class);
        return employeeResponseDto;
    }
    @Transactional
    public void removeEmployeeById(Long id) {
        validationService.validateExistEmployee(id);
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void updateEmployeeSalary(Long id, Long newSalary) {
        validationService.validateEmployeeSalary(newSalary);
        validationService.validateExistEmployee(id);
        employeeRepository.updateEmployeeSalaryById(id,newSalary);
    }
}

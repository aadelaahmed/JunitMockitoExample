package com.example.springjpaexample;

import com.example.springjpaexample.dto.EmployeeRequestDto;
import com.example.springjpaexample.dto.EmployeeResponseDto;
import com.example.springjpaexample.model.Employee;
import com.example.springjpaexample.repository.EmployeeRepository;
import com.example.springjpaexample.service.ValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.bridge.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestValidationService {
    @InjectMocks
    ValidationService validationService;
    @Mock
    EmployeeRepository employeeRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Test
    public void test_employee_name_is_already_exist() {
        //Arrange
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setName("mohamed");
        //Act
        when(employeeRepository.findEmployeeByName(anyString())).thenReturn(
                //modelMapper.map(employeeRequestDto,Employee.class)
                new Employee()
        );
        //Assert
        Assertions.assertThrows(
                RuntimeException.class, () -> {
                    validationService.validateEmployeeName(employeeRequestDto);
                }
        );
    }

    @Test
    public void test_employee_age_less_than_50() {
        //ARRANGE
        int age = 52;
        //ACT,ASSERT
        Assertions.assertThrows(
                RuntimeException.class, () -> {
                    validationService.validateEmployeeAge(age);
                }
        );
    }

    /*@Test
    public void test_employee_salary_greater_than_0() {
        //ARRANGE
        long salary = 600;
        //ACT,ASSERT
        Assertions.assertThrows(
                RuntimeException.class,
                () -> {
                    validationService.validateEmployeeSalary(salary);
                }
        );
    }*/

    @Test
    public void test_employee_is_already_exist() {
        //ARRANGE
        long empId = 20;
        //ACT
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //ASSERT
        Assertions.assertThrows(
                EntityNotFoundException.class,
                ()->{
                    validationService.validateExistEmployee(empId);
                }
        );
    }

}

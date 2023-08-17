package com.example.springjpaexample;

import com.example.springjpaexample.dto.EmployeeRequestDto;
import com.example.springjpaexample.dto.EmployeeResponseDto;
import com.example.springjpaexample.model.Employee;
import com.example.springjpaexample.repository.EmployeeRepository;
import com.example.springjpaexample.service.EmployeeService;
import com.example.springjpaexample.service.ValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestEmployeeService {
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeRepository employeeRepositoryMock;
    @Mock
    ValidationService validationService;
    ModelMapper modelMapper = new ModelMapper();
    @Test
    public void test_new_employee_is_added() {
        //ARRANGE
        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder()
                .name("mohamed").build();
        Employee expectedEmployee = Employee.builder()
                .name("mohamed")
                .id(1L)
                .build();
        doNothing().when(validationService).validateEmployee(any(EmployeeRequestDto.class));
        //ACT
        doReturn(expectedEmployee).when(employeeRepositoryMock).save(any(Employee.class));
        EmployeeResponseDto actualEmployee = employeeService.addNewEmployee(
                employeeRequestDto
        );
        //ASSERT
        Assertions.assertEquals(
                actualEmployee.getName(), expectedEmployee.getName()
        );
        /*Assertions.assertDoesNotThrow(
                () -> {
                    employeeService.addNewEmployee(employeeRequestDto);
                }
        );*/
    }

    @Test
    public void test_find_all_employees() {
        //ARRANGE
        List<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(Employee.builder().name("moemen").build());
        expectedEmployees.add(Employee.builder().name("ahmed").build());
        //ACT
        when(employeeRepositoryMock.findAll()).thenReturn(expectedEmployees);
        int expectedSize = employeeService.getAllEmployees().size();
        //ASSERT
        Assertions.assertEquals(
                expectedSize, expectedEmployees.size()
        );
    }

    @Test
    public void test_find_employee_by_id() {
        //ARRANGE
        long empId = 90;
        Employee expectedEmployeeResponse = Employee.builder()
                .id(empId).build();
        //ACT
        //because we need to validate first if employee with this id is exist or not.
        when(validationService.validateExistEmployee(empId)).thenReturn(expectedEmployeeResponse);
        EmployeeResponseDto actualEmployeeResponse = employeeService.getEmployeeById(empId);
        //ASSERT
        Assertions.assertEquals(
                actualEmployeeResponse.getId(), expectedEmployeeResponse.getId()
        );
        //TODO -> use verify or not use to complete the mocking?
    }

    @Test
    public void test_update_employee_salary() {
        Long employeeId = 1L;
        Long newSalary = 600L;
        //verify(validationService).validateEmployeeSalary(newSalary);
        when(validationService.validateExistEmployee(employeeId)).thenReturn(
                Employee.builder()
                        .id(employeeId)
                        .salary(newSalary)
                        .build()
        );
        doNothing().when(validationService).validateEmployeeSalary(newSalary);
        employeeService.updateEmployeeSalary(employeeId, newSalary);
        verify(validationService).validateEmployeeSalary(newSalary);
        verify(validationService).validateExistEmployee(employeeId);
        verify(employeeRepositoryMock).updateEmployeeSalaryById(employeeId, newSalary);
    }

    @Test
    public void test_delete_employee_by_id() {
        //ARRANGE
        long empId = 80;
        Employee expectedEmployee = Employee.builder().id(empId).build();
        /*TODO -> why is it executed successfully without creating stubbing for
        the method findbyid?
        */
        //create mocking for repository when using findbyid because this method is invoked
        //in the implementation of deleteById() method of JpaRepository.
        //doReturn(Optional.of(expectedEmployee)).when(employeeRepositoryMock).findById(empId);

        //doNothing().when(validationService.validateExistEmployee(empId));
        when(validationService.validateExistEmployee(empId)).thenReturn(expectedEmployee);
        //ASSERT
        Assertions.assertDoesNotThrow(
                () -> {
                    employeeService.removeEmployeeById(empId);
                }
        );

    }
}

package com.example.springjpaexample.controller;

import com.example.springjpaexample.dto.EmployeeRequestDto;
import com.example.springjpaexample.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid EmployeeRequestDto employeeRequestDto, BindingResult bindingResult){
            employeeService.addNewEmployee(employeeRequestDto);
            return ResponseEntity.ok("Employee's created successfully");
    }
    @GetMapping("{id}")
    public ResponseEntity getEmployeeById(@PathVariable("id") Long employeeId){
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }
    @GetMapping("/all")
    public ResponseEntity getAllEmployees(){
        List<EmployeeRequestDto> employeeRequestDtos = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeRequestDtos);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeEmployee(@PathVariable("id") Long id){
        //employeeService.getEmployeeById(id);
        employeeService.removeEmployeeById(id);
        return ResponseEntity.ok("Employee with id: "+id+" was deleted successfully");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployeeSalary(
            @PathVariable("id") Long id,@RequestParam Long newSalary
    ){
        employeeService.updateEmployeeSalary(id,newSalary);
        return ResponseEntity.ok("Employee with id: "+id+" was updated with new salary");
//          return ResponseEntity.ok(employee);
    }
}

package com.example.springjpaexample.repository;

import com.example.springjpaexample.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findEmployeeByName(String name);
    @Modifying
    @Query("UPDATE Employee e SET e.salary = :newSalary WHERE e.id = :employeeId")
    void updateEmployeeSalaryById(Long employeeId, Long newSalary);
}

package com.example.cruddemo.repository_new;

import com.example.cruddemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

//@RepositoryRestController(path = "employee")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}

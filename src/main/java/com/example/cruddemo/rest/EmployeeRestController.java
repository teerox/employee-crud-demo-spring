package com.example.cruddemo.rest;

import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// we using the spring data rest, so we don't need to use @ResponseBody
// @RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    //@Autowired
    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    // expose "/employees" and return list of employees
    @GetMapping("/employee")
    public List<Employee> findAll() {
            return employeeService.findAll();
    }

    // add mapping for GET /employees/{employeeId}
    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable  int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);
        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        return theEmployee;
    }

    // add mapping for POST /employees - add new employee
    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee theEmployee) {
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        theEmployee.setId(0);
        employeeService.save(theEmployee);
        return theEmployee;
    }

    // add mapping for PUT /employees - update existing employee
    @PutMapping("/employee")
    public Employee updateEmployee(@RequestBody Employee theEmployee) {
        employeeService.update(theEmployee);
        return theEmployee;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee
    @DeleteMapping("/employee/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee tempEmployee = employeeService.findById(employeeId);
        // throw exception if null
        if (tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        employeeService.deleteById(employeeId);
        return "Deleted employee id - " + employeeId;
    }
}

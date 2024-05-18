package com.example.cruddemo.dao;

import com.example.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {


    public List<Employee> findAll();
    public Employee findById(int theId);
    public Employee save(Employee theEmployee);
    public void deleteById(int theId);

    public void update(Employee theEmployee);
}

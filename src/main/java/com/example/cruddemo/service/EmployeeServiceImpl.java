package com.example.cruddemo.service;

import com.example.cruddemo.dao_old.EmployeeDAO;
import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.repository_new.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    // old DAO
    private final EmployeeDAO employeeDAO;

    private final EmployeeRepository employeeRepository;


    EmployeeServiceImpl(EmployeeDAO theEmployeeDAO, EmployeeRepository employeeRepository) {
        employeeDAO = theEmployeeDAO;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = employeeRepository.findById(theId);
        Employee theEmployee = null;

        if (result.isPresent()) {
            theEmployee = result.get();
        } else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }
        return theEmployee;
    }

    @Transactional
    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepository.save(theEmployee);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        employeeRepository.deleteById(theId);
    }

    @Transactional
    @Override
    public void update(Employee theEmployee) {
        employeeRepository.save(theEmployee);
    }
}

package com.example.cruddemo.controller;

import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
//@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    //@Autowired
    public EmployeeController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    @GetMapping("/home")
    public String showHome() {
        return "/home/home";
    }

    @GetMapping("/leaders")
    public String showLeaders() {
        return "/home/leaders";
    }

    @GetMapping("/systems")
    public String showSystem() {
        return "/home/systems";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "/home/access-denied";
    }

    @GetMapping("/list")
    public String showAllEmployee(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String processForm(@Valid @ModelAttribute("employee") Employee employee,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employees/employee-form";
        }
        employeeService.save(employee);
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showEditEmployeeForm(@RequestParam("employeeId") int id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            // Handle the case when the employee is not found
            return "redirect:/employees/list-employees";
        }
        model.addAttribute("employee", employee);
        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int id) {
        employeeService.deleteById(id);
        return "redirect:/employees/list";
    }


    // Add an initbinder to convert trim input strings
    // Remove leading and trailing whitespace
    // Resolve issue for our validation
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}

package com.ohgiraffers.semiproject.employee.controller;

import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTO;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("sidemenu/employee")
    public String empAll(Model model) {

        List<EmployeeDTO> emp = employeeService.empAll();

        model.addAttribute("emp", emp);

        return "sidemenu/employee/employee";
    }
}

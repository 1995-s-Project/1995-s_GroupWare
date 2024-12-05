package com.ohgiraffers.semiproject.employee.controller;

import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTO;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 직원정보 페이지로 이동
    @GetMapping("/sidemenu/employee")
    public String employee(Model model) {

        List<EmployeeDTO> test = employeeService.selectemp();

        for (EmployeeDTO test2 : test)
            System.out.println("test2 = " + test2);

        model.addAttribute("test", test);

        return "sidemenu/employee/employee";
    }

}

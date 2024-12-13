package com.ohgiraffers.semiproject.employee.controller;

import com.ohgiraffers.semiproject.employee.model.dto.CommentDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTOJOB;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    // 직원 전체 조회
    @GetMapping("sidemenu/employee")
    public String empAll(Model model) {

        List<EmployeeDTOJOB> emp = employeeService.empAll();

        model.addAttribute("emp", emp);

        return "sidemenu/employee/employee";
    }

//    @GetMapping("/employee/{empCode}/details")
//    public String getEmployeeDetails(@PathVariable Integer empCode, Model model) {
//        // 직원 정보 조회
//        EmployeeDTOJOB employee = employeeService.empSelect(empCode);
//        System.out.println("Employee Object: " + employee);  // EmployeeDTOJOB 객체 확인
//        model.addAttribute("emp", employee);  // 직원 객체 전달
//
//        // 해당 직원의 댓글 목록 가져오기
//        List<CommentDTO> commentList = employeeService.comment(empCode);
//        System.out.println("Comment List: " + commentList);  // 댓글 목록 확인
//        model.addAttribute("comment", commentList);  // 댓글 목록 전달
//
//        return "sidemenu/employee/empdetail";  // 상세 페이지로 이동
//    }
//
//    @PostMapping("/comments/add")
//    public String addComment(@RequestParam Integer empCode, @RequestParam String text) {
//
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setEmpCode(empCode);
//        commentDTO.setText(text);
//
//        employeeService.saveComment(commentDTO);
//
//        return "redirect:/employee/" + empCode + "/details";
//    }
}

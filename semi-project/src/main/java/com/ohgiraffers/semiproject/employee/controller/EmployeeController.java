package com.ohgiraffers.semiproject.employee.controller;

import com.ohgiraffers.semiproject.board.model.dto.PageDTO;
import com.ohgiraffers.semiproject.employee.model.dto.CommentDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTOJOB;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserInfoService userInfoService;

    public EmployeeController(EmployeeService employeeService,UserInfoService userInfoService) {
        this.employeeService = employeeService;
        this.userInfoService = userInfoService;
    }

    // 직원 전체 조회
    @GetMapping("/employee")
    public String empAll(Model model, @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "15") int size) {

        int offset = page * size;

        List<EmployeeDTOJOB> employee = employeeService.empAll(offset, size);

        List<EmployeeDTOJOB> emp = employeeService.empAllList();

        long totalProducts = employeeService.getTotalProducts();

        int totalPages = (int) Math.ceil((double) totalProducts / size);

        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        model.addAttribute("pageInfo", pageInfo);

        model.addAttribute("employee", employee);

        model.addAttribute("emp", emp);

        return "sidemenu/employee/employee";
    }

    // 경영부 조회
    @GetMapping("/employee/management")
    public String managementDept(Model model) {

        List<EmployeeDTOJOB> management = employeeService.managementDept();

        List<EmployeeDTOJOB> emp = employeeService.empAllList();

        model.addAttribute("management", management);

        model.addAttribute("emp", emp);

        return "sidemenu/employee/management";
    }

    // 애견담당부 조회
    @GetMapping("/employee/dog")
    public String dogDept(Model model) {

        List<EmployeeDTOJOB> dog = employeeService.dogDept();

        List<EmployeeDTOJOB> emp = employeeService.empAllList();

        model.addAttribute("dog", dog);

        model.addAttribute("emp", emp);

        return "sidemenu/employee/dog";
    }

    // 마케팅부 조회
    @GetMapping("/employee/marketing")
    public String marketingDept(Model model) {

        List<EmployeeDTOJOB> marketing = employeeService.marketingDept();

        List<EmployeeDTOJOB> emp = employeeService.empAllList();

        model.addAttribute("marketing", marketing);

        model.addAttribute("emp", emp);

        return "sidemenu/employee/marketing";
    }

    // 영업부 조회
    @GetMapping("/employee/sales")
    public String salesDept(Model model) {

        List<EmployeeDTOJOB> sales = employeeService.salesDept();

        List<EmployeeDTOJOB> emp = employeeService.empAllList();

        model.addAttribute("sales", sales);

        model.addAttribute("emp", emp);

        return "sidemenu/employee/sales";
    }

    // 물류부 조회
    @GetMapping("/employee/logistics")
    public String logisticsDept(Model model) {

        List<EmployeeDTOJOB> logistics = employeeService.logisticsDept();

        List<EmployeeDTOJOB> emp = employeeService.empAllList();

        model.addAttribute("logistics", logistics);

        model.addAttribute("emp", emp);

        return "sidemenu/employee/logistics";
    }

    // cs부 조회
    @GetMapping("/employee/customer")
    public String customerDept(Model model) {

        List<EmployeeDTOJOB> customer = employeeService.customerDept();

        List<EmployeeDTOJOB> emp = employeeService.empAllList();

        model.addAttribute("customer", customer);

        model.addAttribute("emp", emp);

        return "sidemenu/employee/customer";
    }

    // 직원 상세페이지
    @GetMapping("/employee/details/{empCode}")
    public String getEmployeeDetails(@PathVariable String empCode, Model model) {


        EmployeeDTOJOB employee = employeeService.empSelect(empCode);

        List<CommentDTO> comment = employeeService.comment(empCode);

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        if (comment == null) {
            comment = new ArrayList<>();
        }

        String name = (userInfo != null) ? userInfo.getName() : "익명 사용자";

        model.addAttribute("name", name);

        model.addAttribute("comment", comment);

        model.addAttribute("emp", employee);

        return "sidemenu/employee/empdetail";
    }

    // 상세페이지에서 댓글쓰기
    @PostMapping("/comments/add")
    public String addComment(@RequestParam String empCode, @RequestParam String text) {
        
        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setEmpCode(empCode);

        commentDTO.setText(text);

        commentDTO.setCommentEmpCode(code);

        LocalDateTime createdDate = LocalDateTime.now();

        commentDTO.setCreatedDate(createdDate);

        employeeService.saveComment(commentDTO);

        return "redirect:/employee/details/" + empCode;
    }

    // 댓글삭제
    @GetMapping("/employee/{empCode}/comment/{id}/{commentEmpCode}/delete")
    public String commentDelete(@PathVariable String empCode, @PathVariable Integer id,
                                @PathVariable String commentEmpCode) {

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setEmpCode(empCode);

        commentDTO.setId(id);

        if (commentEmpCode.equals(code)) {
            employeeService.commentDelete(commentDTO);
        }

        return "redirect:/employee/details/" + empCode;
    }

    // 메인으로 돌아가기
    @GetMapping("/employee/employee")
    public void employee() {
    }
}


package com.ohgiraffers.semiproject.employee.controller;

import com.ohgiraffers.semiproject.board.model.dto.PageDTO;
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
    public String empAll(Model model, @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "15") int size) {

        int offset = page * size;

        List<EmployeeDTOJOB> emp = employeeService.empAll(offset, size);

        long totalProducts = employeeService.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / size);

        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("emp", emp);

        return "sidemenu/employee/employee";
    }

    @GetMapping("/sidemenu/employee/search")
    public String empSearch(@RequestParam String query, Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "15") int size) {
        int offset = page * size;

        // 데이터 조회
        List<EmployeeDTOJOB> emp = employeeService.empSearch(query, offset, size);

        // 전체 데이터 개수 조회
        long totalProducts = employeeService.getTotalProducts1();
        int totalPages = (int) Math.ceil((double) totalProducts / size);

        // totalPages가 0일 경우 1페이지로 설정
        if (totalPages == 0) {
            totalPages = 1;
        }

        // PageDTO 객체에 페이지 정보 설정
        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        // 모델에 페이지 정보, 검색 결과, 쿼리 파라미터 전달
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("emp", emp);
        model.addAttribute("query", query);

        System.out.println("Total Products: " + totalProducts);
        System.out.println("Total Pages: " + totalPages);
        System.out.println("Offset: " + offset);

        return "/sidemenu/employee/search";
    }

    @GetMapping("/employee/details/{empCode}")
    public String getEmployeeDetails(@PathVariable Integer empCode, Model model) {
        // 직원 정보 조회
        EmployeeDTOJOB employee = employeeService.empSelect(empCode);
        System.out.println("Employee Object: " + employee);  // EmployeeDTOJOB 객체 확인
        model.addAttribute("emp", employee);  // 직원 객체 전달

        // 해당 직원의 댓글 목록 가져오기
        List<CommentDTO> commentList = employeeService.comment(empCode);
        System.out.println("Comment List: " + commentList);  // 댓글 목록 확인
        model.addAttribute("comment", commentList);  // 댓글 목록 전달

        return "sidemenu/employee/empdetail";  // 상세 페이지로 이동
    }

    @PostMapping("/comments/add")
    public String addComment(@RequestParam Integer empCode, @RequestParam String text) {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setEmpCode(empCode);
        commentDTO.setText(text);

        employeeService.saveComment(commentDTO);

        return "redirect:/employee/" + empCode + "/details";
    }
}

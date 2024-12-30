package com.ohgiraffers.semiproject.employee.model.dao;

import com.ohgiraffers.semiproject.employee.model.dto.CommentDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTOJOB;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeScheduleInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<EmployeeDTOJOB> empAll(@Param("offset") int offset, @Param("size") int size);

    long countAll();

    List<EmployeeDTOJOB> empSearch(String query, @Param("offset") int offset,
                                                 @Param("size") int size);

    long countAll1(String query);

    EmployeeDTOJOB empSelect(String empCode);

    void saveComment(CommentDTO commentDTO);

    List<CommentDTO> comment(String empCode);

    List<EmployeeJoinListDTO> empAllSelect();

    void commentDelete(CommentDTO commentDTO);

    List<EmployeeJoinListDTO> mailAllSelect(String code);

    // 마이페이지 - 내 댓글 조회 메소드
    List<CommentDTO> getUserComment(String code);
    // 마이페이지 - 비밀번호 변경
    void updatePasswordByCode(String encryptedPassword, String code);
    // 마이페이지 - 회원정보 수정
    EmployeeJoinListDTO empInfoSelect(String empCode);

    // 직원별 사번코드, 부서명, 직급명, 당일 출근시간, 퇴근시간 조회해오기
    List<EmployeeScheduleInfoDTO> getEmployeeList();
    // 마이페이지 - 회원정보 이미지 수정
    void changeProfileImage(String fileName, String empCode);
    // 마이페이지 - 회원정보 이미지 삭제
    void deleteProfileImage(String empCode);
    // 회원정보수정 - 주소 수정
    void changeAddress(String newAddress, String empCode);
    // 회원정보수정 - 휴대전화번호 수정
    void changePhone(String newPhone, String empCode);
    // 직원 사번코드 가져오기
    List<EmployeeScheduleInfoDTO> getEmployeeCode();
    //  부서별 전체조회(경영부)
    List<EmployeeDTOJOB> managementDept();
    //  부서별 전체조회(애견담당부)
    List<EmployeeDTOJOB> dogDept();
    //  부서별 전체조회(마케팅부)
    List<EmployeeDTOJOB> marketingDept();
    //  부서별 전체조회(영업부)
    List<EmployeeDTOJOB> salesDept();
    //  부서별 전체조회(물류부)
    List<EmployeeDTOJOB> logisticsDept();
    //  부서별 전체조회(고객업무/cs부)
    List<EmployeeDTOJOB> customerDept();

    List<EmployeeDTOJOB> getEmployeesPresident(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesVicePresident(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesTeamLeader(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesChief(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesStaff(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesTeamLeaderPet(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesChiefPet(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesStaffPet(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesTeamLeaderMarketing(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesChiefMarketing(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesStaffMarketing(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesTeamLeaderSales(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesChiefSales(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesStaffSales(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesTeamLeaderLogistics(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesChiefLogistics(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesStaffLogistics(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesTeamLeaderCustomer(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesChiefCustomer(@Param("jobCode") String jobCode);

    List<EmployeeDTOJOB> getEmployeesStaffCustomer(@Param("jobCode") String jobCode);
}

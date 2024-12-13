package com.ohgiraffers.semiproject.employee.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_comment")
public class CommentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "emp_code")
    private Integer empCode;

    @Column(name = "comment_contents")  // 컬럼 이름이 "comment_contents"
    private String text;

    private Date createdDate = new Date();
}
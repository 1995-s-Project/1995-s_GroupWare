package com.ohgiraffers.semiproject.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 Not Found 예외 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(NoHandlerFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        modelAndView.addObject("message", "페이지를 찾을 수 없습니다😢");
        return modelAndView;
    }

    // 400 Bad Request 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleBadRequest(IllegalArgumentException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.addObject("message", "잘못된 요청입니다😢 " + ex.getMessage());
        return modelAndView;
    }

    // 403 Forbidden 예외 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleForbidden(AccessDeniedException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        modelAndView.addObject("message", "접근이 거부되었습니다😢");
        return modelAndView;
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("message", "😢서버 오류가 발생했습니다😢");
        return modelAndView;
    }

    // 템플릿 처리 예외 처리
    @ExceptionHandler(TemplateProcessingException.class)
    public ModelAndView handleTemplateProcessingException(TemplateProcessingException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("message", "템플릿 처리 오류가 발생했습니다😢 " + ex.getMessage());
        return modelAndView;
    }

    // 템플릿 입력 예외 처리
    @ExceptionHandler(TemplateInputException.class)
    public ModelAndView handleTemplateInputException(TemplateInputException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("message", "템플릿 입력 오류가 발생했습니다😢 " + ex.getMessage());
        return modelAndView;
    }

    // NullPointerException 처리
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException(NullPointerException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("message", "서버 오류가 발생했습니다😢 " + ex.getMessage());
        return modelAndView;
    }
}

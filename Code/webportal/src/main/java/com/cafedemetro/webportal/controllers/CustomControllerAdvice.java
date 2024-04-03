package com.cafedemetro.webportal.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import com.cafedemetro.webportal.models.CustomException;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(CustomException.class)
    @SuppressWarnings("all")
    public ModelAndView handle(CustomException e) {
        return new ModelAndView("error", "errObj", e);
    }

    @ExceptionHandler
    @SuppressWarnings("all")
    public ModelAndView handle(Exception ex) {
        CustomException e = new CustomException();
        e.setErrCode("-1");
        e.setErrMsg(e.getMessage());
        return new ModelAndView("error", "errObj", e);
    }
}
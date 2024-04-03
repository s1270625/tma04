package com.cafedemetro.webportal.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.cafedemetro.webportal.models.CustomException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/customError")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        CustomException err = new CustomException();

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                err = new CustomException("404", "Page not found!", "/");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                err = new CustomException("500", "Server error!", "/");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                err = new CustomException("403", "Not Authorized!", "/");
            }
        }

        if (err.getErrMsg() == null || err.getErrMsg().isEmpty()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                Exception ex = (Exception) attributes.getAttribute("javax.servlet.error.exception", 0);
                if (ex != null) {
                    err = new CustomException("-1", ex.getMessage(), "/");
                }
            }
            err = new CustomException("-1", "Unknown Error Occur!", "/");
        }
        return new ModelAndView("error", "errObj", err);
    }
}
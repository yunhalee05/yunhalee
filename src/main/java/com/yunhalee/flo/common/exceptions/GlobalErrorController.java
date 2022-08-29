package com.yunhalee.flo.common.exceptions;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

    private static final String NOT_FOUND_PAGE = "404";
    private static final String INTERNAL_SERVER_ERROR_PAGE = "500";
    private static final String ERROR_PAGE = "error";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(status.toString()));
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase());
        model.addAttribute("response", errorResponse);
        if (httpStatus == HttpStatus.NOT_FOUND) {
            return NOT_FOUND_PAGE;
        }
        else  if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            return INTERNAL_SERVER_ERROR_PAGE;
        }
        return ERROR_PAGE;
    }
}

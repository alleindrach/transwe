package com.transwe.web.controller;

import com.transwe.web.dto.BaseOutput;
import com.transwe.exception.CommonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandlingController {
    private static final Logger logger = LogManager.getLogger();
    public static final String DEFAULT_ERROR_VIEW = "error";


    // Convert a predefined exception to an HTTP Status code
    @ResponseStatus(value= HttpStatus.CONFLICT,
            reason="Data integrity violation")  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {
        // Nothing to do
    }

    // Specify name of a specific view that will be used to display the error:
    @ExceptionHandler({SQLException.class,DataAccessException.class})
    public String databaseError() {
        // Nothing to do.  Returns the logical view name of an error page, passed
        // to the view-resolver(s) in usual way.
        // Note that the exception is NOT available to this view (it is not added
        // to the model) but see "Extending ExceptionHandlerExceptionResolver"
        // below.
        return "databaseError";
    }
    @ExceptionHandler(CommonException.class)
    public @ResponseBody
    BaseOutput handleCommonError(HttpServletRequest req, CommonException ex) {
        logger.error("Request: " + req.getRequestURL() ,  ex);

        BaseOutput baseOutput=new BaseOutput();
        baseOutput.setState(BaseOutput.FAILED);
        baseOutput.setCode(ex.getErrorCode().getRetcode());
        baseOutput.setData(ex.getErrorCode().getMessage());
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", ex);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("error");
        return baseOutput;
    }
    // Total control - setup a model and return the view name yourself. Or
    // consider subclassing ExceptionHandlerExceptionResolver (see below).
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    BaseOutput handleError(HttpServletRequest req, Exception ex) {
        logger.error("Request: " + req.getRequestURL() ,  ex);

        BaseOutput baseOutput=new BaseOutput();
        baseOutput.setState(BaseOutput.FAILED);
        baseOutput.setData(ex.getMessage()==null?ex.getCause():ex.getMessage());
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", ex);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("error");
        return baseOutput;
    }



}

package com.digital.code.globalexceptionhandling.handlers;

import com.digital.code.globalexceptionhandling.customexceptions.EmployeeNotFoundException;
import com.digital.code.globalexceptionhandling.models.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlers {
    Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * Remember when you provide message attribute in Exception Handler only that's returned because it has priority.
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class,EmployeeNotFoundException.class})
    public ResponseEntity<Object> GlobalExceptionHandler(HttpServletRequest request, Exception ex){
        GlobalException globalException = new GlobalException();
        globalException.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        globalException.status = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        globalException.message = ex.getMessage();
        globalException.url = request.getRequestURL().toString();
        List<String> errors = new ArrayList<>();
        for(StackTraceElement stackTraceElement : ex.getStackTrace()){
            errors.add(stackTraceElement.getFileName() + "("+stackTraceElement.getMethodName()+") : "+stackTraceElement.getLineNumber());
        }
        globalException.errors = errors;
        logger.error("Exception handled");
        return new ResponseEntity<>(globalException,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

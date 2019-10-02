package com.digital.code.globalexceptionhandling.handlers;

import com.digital.code.globalexceptionhandling.customexceptions.EmployeeNotFoundException;
import com.digital.code.globalexceptionhandling.models.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlers {
    Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * Remember when you provide message attribute in @ExceptionHandler only that's returned because it has priority.
     *
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, EmployeeNotFoundException.class})
    public ResponseEntity<Object> GlobalExceptionHandler(HttpServletRequest request, Exception ex) {
        GlobalException globalException = generateExceptionObject(request, ex, HttpStatus.INTERNAL_SERVER_ERROR, true);
        logger.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(globalException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleRequiredParamsException(HttpServletRequest request,MissingServletRequestParameterException ex){
        GlobalException globalException = generateExceptionObject(request, ex, HttpStatus.BAD_REQUEST, false);
        logger.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(globalException,HttpStatus.BAD_REQUEST);
    }

    private GlobalException generateExceptionObject(HttpServletRequest request, Exception e, HttpStatus httpStatus, boolean sendErros) {
        GlobalException globalException = new GlobalException();
        globalException.code = httpStatus.value();
        globalException.status = httpStatus.getReasonPhrase();
        globalException.message = e.getMessage();
        globalException.url = request.getRequestURL().toString();
        if (sendErros) {
            List<String> errors = new ArrayList<>();
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                errors.add(stackTraceElement.getFileName() + "(" + stackTraceElement.getMethodName() + ") : " + stackTraceElement.getLineNumber());
            }
            globalException.errors = errors;
        }
        return globalException;
    }
}

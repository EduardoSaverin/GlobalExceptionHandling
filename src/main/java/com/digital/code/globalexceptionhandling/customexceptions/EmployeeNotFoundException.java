package com.digital.code.globalexceptionhandling.customexceptions;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(Integer id) {
        super("Employee not found with id : " + id);
    }
}

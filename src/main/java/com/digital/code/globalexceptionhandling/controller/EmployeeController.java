package com.digital.code.globalexceptionhandling.controller;

import com.digital.code.globalexceptionhandling.customexceptions.EmployeeNotFoundException;
import com.digital.code.globalexceptionhandling.models.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EmployeeController {
    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) throws Exception {
        if (id == 1) {
            Employee employee = new Employee();
            employee.id = 1;
            employee.name = "EduardoSaverin";
            employee.designation = "Engineer";
            return employee;
        }else if (id == 2) {
            throw new EmployeeNotFoundException(id);
        }
        throw new Exception("Generic Exception");
    }
}
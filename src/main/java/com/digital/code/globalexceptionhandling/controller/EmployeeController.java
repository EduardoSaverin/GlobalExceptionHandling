package com.digital.code.globalexceptionhandling.controller;

import com.digital.code.globalexceptionhandling.customexceptions.EmployeeNotFoundException;
import com.digital.code.globalexceptionhandling.models.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
class EmployeeController {
    @GetMapping("/emp")
    public Employee getEmployee(@RequestParam("id") @NotNull Integer id) throws Exception {
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
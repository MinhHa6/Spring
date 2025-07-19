package Devmaster_Lesson4.controller;

import Devmaster_Lesson4.dto.EmploeeDTO;
import Devmaster_Lesson4.dto.KhoaDTO;
import Devmaster_Lesson4.entity.Employee;
import Devmaster_Lesson4.entity.Khoa;
import Devmaster_Lesson4.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @GetMapping("/employee-list")
    public List<Employee> getAllEmployee()
    {
        return employeeService.findAll();
    }
    @PostMapping("/employee-add")
    public ResponseEntity<String> addUser(@Valid @RequestBody EmploeeDTO emploee)
    {
        employeeService.create(emploee);
        return ResponseEntity.badRequest().body("Employe created successfully");
    }
    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable String id)
    {
        Long par=Long.parseLong(id);
        return employeeService.getEmployee(par);
    }
    @DeleteMapping("/employee/{id}")
    public Boolean deleteEmployee(@PathVariable String id)
    {
        Long par=Long.parseLong(id);
        return employeeService.deleteEmployee(par);
    }
}

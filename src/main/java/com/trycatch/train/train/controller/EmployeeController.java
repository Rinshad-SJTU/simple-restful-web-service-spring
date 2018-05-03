package com.trycatch.train.train.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trycatch.train.train.model.Employee;
import com.trycatch.train.train.model.Response;
import com.trycatch.train.train.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository repository;

    @PersistenceContext
    EntityManager manager;

    @GetMapping("/employees")
    public ResponseEntity<?> listEmployees() {
        List<Employee> employees = repository.findAll();
        if (employees.isEmpty()) {
            return ResponseEntity.ok(new Response(404, "No employee record"));
        }
        else return ResponseEntity.ok(employees);
    }

    @GetMapping("/employee")
    public ResponseEntity<?> getEmployee(@RequestParam Long id) {
        Optional<Employee> employee = repository.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(repository.findById(id));
        }
        else return ResponseEntity.ok(new Response(404, "No employee with the ID " + id + " found"));
    }

    @PostMapping("/employee")
    public Response addEmployee(@RequestBody Employee employee) {
        repository.save(employee);
        return new Response(200, "Success");
    }

    @PostMapping("/update")
    public Response updateEmployee(@RequestBody Employee emp) {
        Employee employee = repository.getOne(emp.getId());
        if (emp.getName() != null) {
            employee.setName(emp.getName());
        }
        if (emp.getPosition() != null) {
            employee.setPosition(emp.getPosition());
        }
        repository.save(employee);
        return new Response(200, "Success");
    }

    @PostMapping("/delete")
    public Response deleteEmployee(@RequestParam long id) {
        repository.deleteById(id);
        return new Response(200, "Success");
    }

}

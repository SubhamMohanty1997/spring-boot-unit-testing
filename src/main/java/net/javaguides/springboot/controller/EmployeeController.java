package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/getAll")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId,@RequestBody Employee employee){
        return employeeService.getEmployeeById(employeeId)
                .map(savedEmployee->{
                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());

                    Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);

                    return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
                })
                .orElseGet(()->ResponseEntity.notFound().build());

//        Employee savedEmployee = employeeService.getEmployeeById(employeeId).orElseGet((Supplier<? extends Employee>) ResponseEntity.notFound());
//        savedEmployee.setFirstName(employee.getFirstName());
//        savedEmployee.setLastName(employee.getLastName());
//        savedEmployee.setEmail(employee.getEmail());
//
//        Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);
//        return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);


    }
}

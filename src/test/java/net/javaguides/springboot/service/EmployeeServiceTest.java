package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class); we can use @Mock instead of this line
        //employeeService=new EmployeeServiceImpl(employeeRepository); we can use @InjectMocks instead of this line

         employee = Employee.builder()
                .id(1L)
                .firstName("Subham")
                .lastName("Mohanty")
                .email("subham087mohanty@gmail.com")
                .build();
    }

    @DisplayName("Junit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given - precondition or setup

//        Employee employee = Employee.builder()
//                .id(1L)
//                .firstName("Subham")
//                .lastName("Mohanty")
//                .email("subham087mohanty@gmail.com")
//                .build();

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee))
                .willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or behaviour that we are going to test

        Employee savedEmployee = employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    @DisplayName("Junit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        //given - precondition or setup

//        Employee employee = Employee.builder()
//                .id(1L)
//                .firstName("Subham")
//                .lastName("Mohanty")
//                .email("subham087mohanty@gmail.com")
//                .build();

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

//        given(employeeRepository.save(employee))
//                .willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or behaviour that we are going to test

        assertThrows(ResourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });

//        Employee savedEmployee = employeeService.saveEmployee(employee);
//        System.out.println(savedEmployee);

        //then - verify the output
        //assertThat(savedEmployee).isNotNull();
        verify(employeeRepository,never()).save(any(Employee.class));

    }
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public void givenEmployeeList_whenGetAllEmployee_thenReturnEmployeeList(){
            //given - precondition or setup

        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Simran")
                .lastName("Mohanty")
                .email("simran.mohanty501@gmail.com")
                .build();
        Employee employee3 = Employee.builder()
                .id(3L)
                .firstName("Barsha")
                .lastName("Pattanayak")
                .email("pattanayakbarsha666@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee,employee2,employee3));

            //when - action or behaviour that we are going to test

        List<Employee> employees = employeeService.getAllEmployees();

            //then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(3);
        }

    @DisplayName("Junit test for getAllEmployees method (Negative Scenario)")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployee_thenReturnEmptyEmployeeList(){
        //given - precondition or setup

        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Simran")
                .lastName("Mohanty")
                .email("simran.mohanty501@gmail.com")
                .build();
        Employee employee3 = Employee.builder()
                .id(3L)
                .firstName("Barsha")
                .lastName("Pattanayak")
                .email("pattanayakbarsha666@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or behaviour that we are going to test

        List<Employee> employees = employeeService.getAllEmployees();

        //then - verify the output
        Assertions.assertThat(employees).isEmpty();
        assertThat(employees.size()).isEqualTo(0);
    }

    @DisplayName("Junit test for getEmployeeById method (Negative Scenario)")
    @Test
    public void givenEmployeeObject_whenGetEmployeeById_thenReturnEmployeeObject(){
        //given - precondition or setup


        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when - action or behaviour that we are going to test

        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output

        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("Junit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
            //given - precondition or setup
             given(employeeRepository.save(employee)).willReturn(employee);
             employee.setEmail("mohantysubham076@gmail.com");
             employee.setLastName("Pattanayak");

            //when - action or behaviour that we are going to test

        Employee updatedEmployee = employeeService.updateEmployee(employee);

            //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("mohantysubham076@gmail.com");
        }

        @DisplayName("Junit test for deleteEmployee method")
        @Test
        public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
                //given - precondition or setup
            willDoNothing().given(employeeRepository).deleteById(1L);

                //when - action or behaviour that we are going to test
            employeeService.deleteEmployee(1L);

                //then - verify the output
            verify(employeeRepository,times(1)).deleteById(1L);
            }
}

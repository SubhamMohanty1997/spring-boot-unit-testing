package net.javaguides.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Subham")
                .lastName("Mohanty")
                .email("subham087mohanty@gmail.com")
                .build();

        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocationOnMock)->invocationOnMock.getArgument(0));

        //when - action or behavior that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(employee.getLastName())))
                .andExpect(jsonPath("$.email",is(employee.getEmail())));
    }

    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {

        //given
        Employee employee1 = Employee.builder()
                .firstName("Subham")
                .lastName("Mohanty")
                .email("subham087mohanty@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Barsha")
                .lastName("Pattanayak")
                .email("pattanayakbarsha666@gmail.com")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("Simran")
                .lastName("Mohanty")
                .email("simi.mohanty501@gmail.com")
                .build();

        List<Employee> employees = List.of(employee1,employee2,employee3);
        given(employeeService.getAllEmployees()).willReturn(employees);

        //when
        ResultActions response = mockMvc.perform(get("/api/employees/getAll"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(employees.size())));

    }


     // +ve scenario(valid employee id
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given
        long employeeId =1;

        Employee employee = Employee.builder()
                .firstName("Subham")
                .lastName("Mohanty")
                .email("subham087mohanty@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //when
        ResultActions response = mockMvc.perform(get("/api/employees/get/{id}",employeeId));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName",is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(employee.getLastName())))
                .andExpect(jsonPath("$.email",is(employee.getEmail())));

    }

//    // -ve scenario(valid employee id)
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        //given
        long employeeId =1;

        Employee employee = Employee.builder()
                .firstName("Subham")
                .lastName("Mohanty")
                .email("subham087mohanty@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/api/employees/get/{id}",employeeId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //update employee +ve scenario
    @Test
    public void givenEmployeeIdAndEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception{
          long employeeId = 1L;
          Employee savedEmployee = Employee.builder()
                .firstName("Subham")
                .lastName("Mohanty")
                .email("subham087mohanty@gmail.com")
                .build();

          Employee updatedEmployee = Employee.builder()
                .firstName("Subham")
                .lastName("Mohanty")
                .email("mohantysubham076@gmail.com")
                .build();

          given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));

          given(employeeService.updateEmployee(any(Employee.class)))
                  .willAnswer((invocationOnMock)->invocationOnMock.getArgument(0));

          ResultActions response = mockMvc.perform(put("/api/employees/update/{id}",employeeId)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(objectMapper.writeValueAsString(updatedEmployee)));

          response.andExpect(status().isOk())
                  .andDo(print())
                  .andExpect(jsonPath("$.firstName",is(updatedEmployee.getFirstName())))
                  .andExpect(jsonPath("$.lastName",is(updatedEmployee.getLastName())))
                  .andExpect(jsonPath("$.email",is(updatedEmployee.getEmail())));

    }

}

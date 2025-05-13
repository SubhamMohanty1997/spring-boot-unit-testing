package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUpEmployeeObject(){
        employee = Employee.builder()
                .firstName("Subham")
                .lastName("Mohanty")
                .email("subham087mohanty@gmail.com")
                .build();
    }

    // Junit test for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Subham")
//                .lastName("Mohanty")
//                .email("subham087mohanty@gmail.com")
//                .build();

        //when - action or behaviour that we are going to test

        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output

        //assertNotNull(savedEmployee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    @DisplayName("Junit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        //given - precondition or setup
//        Employee employee1 = Employee.builder()
//                .firstName("Subham")
//                .lastName("Mohanty")
//                .email("subham087mohanty@gmail.com")
//                .build();

        Employee employee2 = Employee.builder()
                .firstName("Simran")
                .lastName("Mohanty")
                .email("simi.mohanty501@gmail.com")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("Barsha")
                .lastName("Pattanayak")
                .email("pattanayakbarsha666@gmail.com")
                .build();

       List<Employee> employeeList = List.of(employee,employee2,employee3);
        employeeRepository.saveAll(employeeList);
        //when - action or behaviour that we are going to test

        List<Employee> employees = employeeRepository.findAll();

        //then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(3);
    }
    @DisplayName("Junit test for get employee by ID operation")
    @Test
    public void givenEmployeeList_whenFindById_thenReturnEmployeeObject(){
            //given - precondition or setup

//        Employee employee1 = Employee.builder()
//                .firstName("Subham")
//                .lastName("Mohanty")
//                .email("subham087mohanty@gmail.com")
//                .build();

        Employee employee2 = Employee.builder()
                .firstName("Simran")
                .lastName("Mohanty")
                .email("simi.mohanty501@gmail.com")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("Barsha")
                .lastName("Pattanayak")
                .email("pattanayakbarsha666@gmail.com")
                .build();

        List<Employee> employeeList = List.of(employee,employee2,employee3);
        employeeRepository.saveAll(employeeList);

            //when - action or behaviour that we are going to test

        Employee employee4 = employeeRepository.findById(employee.getId()).get();

            //then - verify the output
        assertThat(employee4).isNotNull();
        assertThat(employee4).isEqualTo(employee);
        }
        @DisplayName("Junit test for get employee by Email operation")
        @Test
        public void givenEmployeeList_whenFindByEmail_thenReturnEmployeeObject() {
            //given - precondition or setup

//            Employee employee1 = Employee.builder()
//                    .firstName("Subham")
//                    .lastName("Mohanty")
//                    .email("subham087mohanty@gmail.com")
//                    .build();

            Employee employee2 = Employee.builder()
                    .firstName("Simran")
                    .lastName("Mohanty")
                    .email("simi.mohanty501@gmail.com")
                    .build();

            Employee employee3 = Employee.builder()
                    .firstName("Barsha")
                    .lastName("Pattanayak")
                    .email("pattanayakbarsha666@gmail.com")
                    .build();

            List<Employee> employeeList = List.of(employee, employee2, employee3);
            employeeRepository.saveAll(employeeList);

            //when - action or behaviour that we are going to test

            Employee employee4 = employeeRepository.findByEmail(employee.getEmail()).get();

            //then - verify the output

            assertThat(employee4).isNotNull();
            assertThat(employee4.getEmail()).isEqualTo(employee.getEmail());
        }
        @DisplayName("Junit test for update employee operation")
        @Test
        public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
                    //given - precondition or setup

//                Employee employee = Employee.builder()
//                        .firstName("Subham")
//                        .lastName("Mohanty")
//                        .email("subham087mohanty@gmail.com")
//                        .build();

                employeeRepository.save(employee);

                    //when - action or behaviour that we are going to test
                Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
                savedEmployee.setEmail("mohantysubham076@gmail.com");

                Employee updatedEmployee = employeeRepository.save(savedEmployee);


                    //then - verify the output
                assertThat(updatedEmployee).isNotNull();
                assertThat(updatedEmployee.getEmail()).isEqualTo("mohantysubham076@gmail.com");
                }

    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
                //given - precondition or setup

//            Employee employee = Employee.builder()
//                    .firstName("Subham")
//                    .lastName("Mohanty")
//                    .email("subham087mohanty@gmail.com")
//                    .build();

            employeeRepository.save(employee);

                //when - action or behaviour that we are going to test
            employeeRepository.delete(employee);

            Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());

                //then - verify the output
            assertThat(optionalEmployee).isEmpty();
            }

            @DisplayName("Junit test for custom query using JPQL index operation")
            @Test
            public void givenEmployeeObject_whenFindByJPQL_thenReturnEmployeeObject(){
                    //given - precondition or setup
//                Employee employee = Employee.builder()
//                        .firstName("Subham")
//                        .lastName("Mohanty")
//                        .email("subham087mohanty@gmail.com")
//                        .build();

                employeeRepository.save(employee);

//                String firstName = "Subham";
//                String lastName = "Mohanty";

                    //when - action or behaviour that we are going to test
                Employee savedEmployee = employeeRepository.findByJPQL(employee.getFirstName(),employee.getLastName());

                    //then - verify the output
                assertThat(savedEmployee).isNotNull();
                }

    @DisplayName("Junit test for custom query using JPQL Named parameter operation")
    @Test
    public void givenEmployeeObject_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Subham")
//                .lastName("Mohanty")
//                .email("subham087mohanty@gmail.com")
//                .build();

        employeeRepository.save(employee);

//        String firstName = "Subham";
//        String lastName = "Mohanty";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(employee.getFirstName(),employee.getLastName());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("Junit test for custom query using Native SQL index operation")
    @Test
    public void givenEmployeeObject_whenFindByNativeSQL_thenReturnEmployeeObject(){
            //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Subham")
//                .lastName("Mohanty")
//                .email("subham087mohanty@gmail.com")
//                .build();

        employeeRepository.save(employee);
//        String firstName = "Subham";
//        String lastName = "Mohanty";
            //when - action or behaviour that we are going to test
             Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(),employee.getLastName());
            //then - verify the output

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
        }

    @DisplayName("Junit test for custom query using Native SQL Named operation")
    @Test
    public void givenEmployeeObject_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Subham")
//                .lastName("Mohanty")
//                .email("subham087mohanty@gmail.com")
//                .build();

        employeeRepository.save(employee);
//        String firstName = "Subham";
//        String lastName = "Mohanty";
        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(),employee.getLastName());
        //then - verify the output

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

}

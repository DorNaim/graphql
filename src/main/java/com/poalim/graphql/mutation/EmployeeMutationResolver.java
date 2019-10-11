package com.poalim.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.poalim.graphql.entities.Employee;
import com.poalim.graphql.entities.Manager;
import com.poalim.graphql.exceptions.EmployeeNotFoundException;
import com.poalim.graphql.exceptions.ManagerNotFoundException;
import com.poalim.graphql.repositories.EmployeeRepository;
import com.poalim.graphql.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@Service
public class EmployeeMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee newEmployee(String firstName, String lastName, Long managerId) {
        Manager manager = null;

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);

        Optional<Manager> managerOptional = managerRepository.findById(managerId);
        if (!managerOptional.isPresent()) {
            throw new ManagerNotFoundException("The manager to assign the employee to was not found", managerId);
        }
        manager = managerOptional.get();
        employee.setManager(manager);

        employeeRepository.save(employee);

        return employee;
    }

    public Employee updateEmployee(Long id, String firstName, String lastName, Long managerId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (!employeeOptional.isPresent()) {
            throw new EmployeeNotFoundException("The employee to be updated was not found", id);
        }

        Employee employee = employeeOptional.get();
        if (firstName != null) {
            employee.setFirstName(firstName);
        }
        if (lastName != null) {
            employee.setLastName(lastName);
        }
        if (managerId != null && managerId >= 0) {
            Optional<Manager> managerOptional = managerRepository.findById(id);
            if (!managerOptional.isPresent()) {
                throw new ManagerNotFoundException("The manager to be assigned was not found", id);
            }
            employee.setManager(managerOptional.get());
        }

        employeeRepository.save(employee);

        return employee;
    }

    public boolean deleteEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (!employeeOptional.isPresent()) {
            throw new EmployeeNotFoundException("The employee to be deleted was not found", id);
        }
        employeeRepository.deleteById(id);
        return true;
    }
}

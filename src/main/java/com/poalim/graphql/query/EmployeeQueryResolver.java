package com.poalim.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.poalim.graphql.entities.Employee;
import com.poalim.graphql.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public long countEmployees() {
        return employeeRepository.count();
    }

    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

}

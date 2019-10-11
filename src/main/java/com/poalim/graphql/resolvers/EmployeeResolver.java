package com.poalim.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.poalim.graphql.entities.Employee;
import com.poalim.graphql.entities.Manager;
import com.poalim.graphql.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeResolver implements GraphQLResolver<Employee> {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Manager getManager(Employee employee) {
        Manager manager = null;

        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        if (employeeOptional.isPresent()) {
            manager = employeeOptional.get().getManager();

        }
        return manager;

    }
}
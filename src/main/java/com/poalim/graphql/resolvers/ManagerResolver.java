package com.poalim.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.poalim.graphql.entities.Employee;
import com.poalim.graphql.entities.Manager;
import com.poalim.graphql.repositories.EmployeeRepository;
import com.poalim.graphql.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ManagerResolver implements GraphQLResolver<Manager> {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees(Manager manager) {
        List<Employee> employees = new ArrayList<>();

        Optional<Manager> managerOptional = managerRepository.findById(manager.getId());
        if (managerOptional.isPresent()) {
            Manager managerActual = managerOptional.get();
            employees = employeeRepository.findByManager(managerActual);
        }

        return employees;

    }
}
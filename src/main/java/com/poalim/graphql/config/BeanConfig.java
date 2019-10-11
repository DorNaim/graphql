package com.poalim.graphql.config;

import com.poalim.graphql.enums.Department;
import com.poalim.graphql.entities.Employee;
import com.poalim.graphql.entities.Manager;
import com.poalim.graphql.handlers.DefaultGraphQLErrorHandler;
import com.poalim.graphql.repositories.EmployeeRepository;
import com.poalim.graphql.repositories.ManagerRepository;
import graphql.servlet.core.GraphQLErrorHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new DefaultGraphQLErrorHandler();
    }

    @Bean
    public CommandLineRunner populateDB(ManagerRepository managerRepository, EmployeeRepository employeeRepository) {
        return (args) -> {

            Manager manager1 = new Manager();
            manager1.setFirstName("m1");
            manager1.setLastName("m1");
            manager1.setDepartment(Department.RESEARCH_AND_DEVELOPMENT);
            Manager manager2 = new Manager();
            manager2.setFirstName("m2");
            manager2.setLastName("m2");
            manager2.setDepartment(Department.INTEGRATION);
            Manager manager3 = new Manager();
            manager3.setFirstName("m3");
            manager3.setLastName("m3");
            manager3.setDepartment(Department.CLOUD);

            manager1 = managerRepository.save(manager1);
            manager2 = managerRepository.save(manager2);
            manager3 = managerRepository.save(manager3);

            Employee employee1 = new Employee();
            employee1.setFirstName("e1");
            employee1.setLastName("e1");
            employee1.setManager(manager1);
            Employee employee2 = new Employee();
            employee2.setFirstName("e2");
            employee2.setLastName("e2");
            employee2.setManager(manager1);
            Employee employee3 = new Employee();
            employee3.setFirstName("e3");
            employee3.setLastName("e3");
            employee3.setManager(manager2);

            employee1 = employeeRepository.save(employee1);
            employee2 = employeeRepository.save(employee2);
            employee3 = employeeRepository.save(employee3);

        };

    }

}

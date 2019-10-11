package com.poalim.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.poalim.graphql.data.input.CreateManagerInput;
import com.poalim.graphql.entities.Employee;
import com.poalim.graphql.entities.Manager;
import com.poalim.graphql.enums.Department;
import com.poalim.graphql.exceptions.ManagerNotFoundException;
import com.poalim.graphql.repositories.EmployeeRepository;
import com.poalim.graphql.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class ManagerMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Manager newManager(String firstName, String lastName, Department department) {
        Manager manager = new Manager();
        manager.setFirstName(firstName);
        manager.setLastName(lastName);
        manager.setDepartment(department);

        managerRepository.save(manager);

        return manager;
    }

    public Manager newManagerByInputObject(CreateManagerInput createManagerInput) {
        Manager manager = new Manager();
        manager.setFirstName(createManagerInput.getFirstName());
        manager.setLastName(createManagerInput.getLastName());
        manager.setDepartment(createManagerInput.getDepartment());

        managerRepository.save(manager);

        return manager;
    }

    public Manager updateManager(Long id, String firstName, String lastName, Department department) {
        Optional<Manager> managerOptional = managerRepository.findById(id);
        if (!managerOptional.isPresent()) {
            throw new ManagerNotFoundException("The manager to be updated was not found", id);
        }

        Manager manager = managerOptional.get();
        if (firstName != null) {
            manager.setFirstName(firstName);
        }
        if (lastName != null) {
            manager.setLastName(lastName);
        }
        if (department != null) {
            manager.setDepartment(department);
        }

        managerRepository.save(manager);

        return manager;
    }

    public boolean deleteManager(Long id) {
        Optional<Manager> managerOptional = managerRepository.findById(id);
        if (!managerOptional.isPresent()) {
            throw new ManagerNotFoundException("The manager to be deleted was not found", id);
        }
        Manager manager = managerOptional.get();

        //detach relationships from employees (remove foreign keys constraints)
        List<Employee> employees = employeeRepository.findByManager(manager);
        for (Employee employee : employees) {
            employee.setManager(null);
            employeeRepository.save(employee);
        }
        manager.setEmployees(null);
        managerRepository.deleteById(id);
        return true;
    }


}

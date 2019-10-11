package com.poalim.graphql.repositories;

import com.poalim.graphql.entities.Employee;
import com.poalim.graphql.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByManager(Manager manager);

}

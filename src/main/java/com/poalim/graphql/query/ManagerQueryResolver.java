package com.poalim.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.poalim.graphql.entities.Manager;
import com.poalim.graphql.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ManagerQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private ManagerRepository managerRepository;

    public Iterable<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public long countManagers() {
        return managerRepository.count();
    }

    public Optional<Manager> getManager(Long id) {
        return managerRepository.findById(id);
    }


}

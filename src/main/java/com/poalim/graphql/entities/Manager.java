package com.poalim.graphql.entities;

import com.poalim.graphql.enums.Department;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private Department department;

    @OneToMany
    private List<Employee> employees;

    @CreationTimestamp
    private Date creationDate;

    public Manager() {
    }

    public Manager(Long id) {
        this.id = id;
    }

}
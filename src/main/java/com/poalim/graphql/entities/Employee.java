package com.poalim.graphql.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @CreationTimestamp
    private Date creationDate;


    public Employee() {
    }

    public Employee(Long id) {
        this.id = id;
    }

}
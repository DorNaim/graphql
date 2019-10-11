package com.poalim.graphql.data.input;

import com.poalim.graphql.enums.Department;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CreateManagerInput {

    private String firstName;
    private String lastName;
    private Department department;
}

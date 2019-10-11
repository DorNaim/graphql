package com.poalim.graphql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class GraphqlEmployeeTestData {
    private static final String EMPLOYEE_ID_PLACEHOLDER = "___employeeId___";
    private static final String MANAGER_ID_PLACEHOLDER = "___managerId___";
    private final String ERROR_MESSAGE = "Error !";
    @Value("classpath:graphql/query-wrapper.json")
    private Resource queryWrapperFile;
    @Value("classpath:graphql/employee/newEmployee.graphql")
    private Resource newEmployeeFile;
    @Value("classpath:graphql/employee/getEmployee.graphql")
    private Resource getEmployeeFile;
    @Value("classpath:graphql/employee/getAllEmployees.graphql")
    private Resource getAllEmployeesFile;
    @Value("classpath:graphql/employee/updateEmployee.graphql")
    private Resource updateEmployeeFile;
    @Value("classpath:graphql/employee/deleteEmployee.graphql")
    private Resource deleteEmployeeFile;

    public String newEmployeePayload(long managerId) {
        try {
            String payload = StreamUtils.copyToString(newEmployeeFile.getInputStream(), StandardCharsets.UTF_8);
            return payload.replace(MANAGER_ID_PLACEHOLDER, String.valueOf(managerId));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    public String getEmployeePayload(long employeeId) {
        try {
            String payload = StreamUtils.copyToString(getEmployeeFile.getInputStream(), StandardCharsets.UTF_8);
            return payload.replace(EMPLOYEE_ID_PLACEHOLDER, String.valueOf(employeeId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    public String getAllEmployeesPayload() {
        try {
            return StreamUtils.copyToString(getAllEmployeesFile.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    public String updateEmployeePayload(long employeeId) {
        try {
            String payload = StreamUtils.copyToString(updateEmployeeFile.getInputStream(), StandardCharsets.UTF_8);
            return payload.replace(EMPLOYEE_ID_PLACEHOLDER, String.valueOf(employeeId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    public String deleteEmployeePayload(long employeeId) {
        try {
            String payload = StreamUtils.copyToString(deleteEmployeeFile.getInputStream(), StandardCharsets.UTF_8);
            return payload.replace(EMPLOYEE_ID_PLACEHOLDER, String.valueOf(employeeId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    //general wrapper
    public String queryWrapper() {
        try {
            return StreamUtils.copyToString(queryWrapperFile.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }
}

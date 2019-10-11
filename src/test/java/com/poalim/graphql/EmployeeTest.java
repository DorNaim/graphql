package com.poalim.graphql;


import com.fasterxml.jackson.databind.JsonNode;
import com.poalim.graphql.entities.Manager;
import com.poalim.graphql.enums.Department;
import com.poalim.graphql.repositories.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GraphQLApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeTest {

    //@Autowired
    //private GraphQLTestTemplate  graphQLTestTemplate;

    private static long employeeId;
    private static long managerId;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private GraphqlEmployeeTestData graphqlEmployeeTestData;
    @Autowired
    private GraphqlTestUtils graphqlTestUtils;

    //for prerequisites
    @Autowired
    private ManagerRepository managerRepository;

    //prerequisites
    @Test
    public void aInitData() {

        Manager manager = new Manager();
        manager.setFirstName("Super manager");
        manager.setLastName("nice last name");
        manager.setDepartment(Department.CLOUD);
        manager= managerRepository.save(manager);
        managerId=manager.getId();
    }

    @Test
    public void bNewEmployee() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlEmployeeTestData.newEmployeePayload(managerId));

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("newEmployee"));
        assertNotNull(parsedResponse.get("data").get("newEmployee").get("id"));
        employeeId = parsedResponse.get("data").get("newEmployee").get("id").asLong();
        assertNull(parsedResponse.get("errors"));
    }

    @Test
    public void cGetEmployee() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlEmployeeTestData.getEmployeePayload(employeeId));

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("getEmployee"));
        assertEquals("Employee id does not match", employeeId,parsedResponse.get("data").get("getEmployee").get("id").asLong());
        assertNull(parsedResponse.get("errors"));
    }

    @Test
    public void dGetAllEmployees() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlEmployeeTestData.getAllEmployeesPayload());

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("getAllEmployees"));
        assertNull(parsedResponse.get("errors"));
    }

    @Test
    public void eUpdateEmployee() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlEmployeeTestData.updateEmployeePayload(employeeId));

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("updateEmployee"));
        assertEquals("Employee id does not match", employeeId,parsedResponse.get("data").get("updateEmployee").get("id").asLong());
        assertEquals("Employee name does not match the expected","New first name", parsedResponse.get("data").get("updateEmployee").get("firstName").asText());
        assertNull(parsedResponse.get("errors"));
    }

    @Test
    public void fDeleteEmployee() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlEmployeeTestData.deleteEmployeePayload(employeeId));

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("deleteEmployee"));
        assertEquals("Employee has not been deleted", true,parsedResponse.get("data").get("deleteEmployee").asBoolean());
        assertNull(parsedResponse.get("errors"));
    }


}


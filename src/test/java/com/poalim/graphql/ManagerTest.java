package com.poalim.graphql;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GraphQLApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Component
public class ManagerTest {

    //@Autowired
    //private GraphQLTestTemplate  graphQLTestTemplate;

    private static long managerId;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private GraphqlManagerTestData graphqlManagerTestData;
    @Autowired
    private GraphqlTestUtils graphqlTestUtils;

    @Test
    public void aNewManager() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlManagerTestData.newManagerPayload());

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("newManager"));
        assertNotNull(parsedResponse.get("data").get("newManager").get("id"));
        managerId = parsedResponse.get("data").get("newManager").get("id").asLong();
        assertNull(parsedResponse.get("errors"));
    }

    @Test
    public void bGetManager() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlManagerTestData.getManagerPayload(managerId));

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("getManager"));
        assertEquals("Manager id does not match", managerId, parsedResponse.get("data").get("getManager").get("id").asLong());
        assertNull(parsedResponse.get("errors"));
    }

    @Test
    public void cGetAllManagers() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlManagerTestData.getAllManagersPayload());

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("getAllManagers"));
        assertNull(parsedResponse.get("errors"));
    }

    @Test
    public void dUpdateManager() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlManagerTestData.updateManagerPayload(managerId));

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("updateManager"));
        assertEquals("Manager id does not match", managerId, parsedResponse.get("data").get("updateManager").get("id").asLong());
        assertEquals("Manager name does not match the expected",  "New First Name",parsedResponse.get("data").get("updateManager").get("firstName").asText());
        assertNull(parsedResponse.get("errors"));
    }

    @Test
    public void eDeleteManager() {

        // create the JSON version of the GraphQL query that will be added to the body of the request
        String payload = graphqlTestUtils.createJsonQuery(graphqlManagerTestData.deleteManagerPayload(managerId));

        // generate headers
        HttpHeaders httpHeaders = graphqlTestUtils.generateHeaders();

        HttpEntity<String> request = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(GraphqlTestUtils.ENDPOINT_LOCATION, HttpMethod.POST, request, String.class);

        assertEquals("Invalid status code", HttpStatus.OK, response.getStatusCode());
        JsonNode parsedResponse = graphqlTestUtils.parse(response.getBody());
        log.info("Response - " + response.toString());
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("deleteManager"));
        assertEquals("Manager has not been deleted",true, parsedResponse.get("data").get("deleteManager").asBoolean());
        assertNull(parsedResponse.get("errors"));
    }


    public long getManagerId() {
        return managerId;

    }
}


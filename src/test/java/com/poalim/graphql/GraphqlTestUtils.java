package com.poalim.graphql;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GraphqlTestUtils {

    //Where to send GraphQL requests
    public static final String ENDPOINT_LOCATION = "/graphql";
    private static final String QUERY_PLACEHOLDER = "__payload__";
    @Autowired
    private GraphqlManagerTestData graphqlManagerTestData;
    private JsonStringEncoder jsonStringEncoder = new JsonStringEncoder();

    /**
     * Call this method with a valid GraphQL query/mutation String
     * This function will escape it properly and wrap it into a JSON query object
     */
    public String createJsonQuery(String graphqlQuery) {
        String queryWrapper = graphqlManagerTestData.queryWrapper();
        String escapedGraphql = escapeQuery(graphqlQuery);
        String jsonQuery = queryWrapper.replace(QUERY_PLACEHOLDER, escapedGraphql);

        return jsonQuery;
    }

    /**
     * Clean the given QraphQL query so that it can be embedded in a JSON string.
     */
    public String escapeQuery(String graphqlQuery) {

        String escapedQuery = graphqlQuery.replaceAll("(\\r|\\n)", "");
        char[] chars = jsonStringEncoder.quoteAsString(escapedQuery);
        escapedQuery = String.valueOf(chars);

        return escapedQuery;
    }


    /**
     * Parse the given payload as a [JsonNode]
     *
     * @return the payload parsed as JSON
     */
    public JsonNode parse(String payload) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return headers;
    }

}

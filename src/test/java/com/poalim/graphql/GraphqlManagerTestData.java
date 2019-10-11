package com.poalim.graphql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class GraphqlManagerTestData {
    private static final String MANAGER_ID_PLACEHOLDER = "___managerId___";
    private final String ERROR_MESSAGE = "Error !";
    @Value("classpath:graphql/query-wrapper.json")
    private Resource queryWrapperFile;
    @Value("classpath:graphql/manager/newManager.graphql")
    private Resource newManagerFile;
    @Value("classpath:graphql/manager/getManager.graphql")
    private Resource getManagerFile;
    @Value("classpath:graphql/manager/getAllManagers.graphql")
    private Resource getAllManagersFile;
    @Value("classpath:graphql/manager/updateManager.graphql")
    private Resource updateManagerFile;
    @Value("classpath:graphql/manager/deleteManager.graphql")
    private Resource deleteManagerFile;

    public String newManagerPayload() {
        try {
            return StreamUtils.copyToString(newManagerFile.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    public String getManagerPayload(long managerId) {
        try {
            String payload = StreamUtils.copyToString(getManagerFile.getInputStream(), StandardCharsets.UTF_8);
            return payload.replace(MANAGER_ID_PLACEHOLDER, String.valueOf(managerId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    public String getAllManagersPayload() {
        try {
            return StreamUtils.copyToString(getAllManagersFile.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    public String updateManagerPayload(long managerId) {
        try {
            String payload = StreamUtils.copyToString(updateManagerFile.getInputStream(), StandardCharsets.UTF_8);
            return payload.replace(MANAGER_ID_PLACEHOLDER, String.valueOf(managerId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_MESSAGE;
    }

    public String deleteManagerPayload(long managerId) {
        try {
            String payload = StreamUtils.copyToString(deleteManagerFile.getInputStream(), StandardCharsets.UTF_8);
            return payload.replace(MANAGER_ID_PLACEHOLDER, String.valueOf(managerId));
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

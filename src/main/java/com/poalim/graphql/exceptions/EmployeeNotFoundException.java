package com.poalim.graphql.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeNotFoundException extends RuntimeException implements GraphQLError {

    /**
     *
     */
    private static final long serialVersionUID = -7341404628059426969L;
    private Map<String, Object> extensions = new HashMap<>();

    public EmployeeNotFoundException(String message, Long invalidManagerId) {
        super(message);
        extensions.put("invalidEmployeeId", invalidManagerId);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}

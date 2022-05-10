package com.skywaet.middlewarefactory.grpcserver.converter.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersionDetector;
import com.skywaet.middlewarefactory.factorycommon.generated.dto.JsonBodyValidatorData;
import com.skywaet.middlewarefactory.grpcserver.exception.BaseFactoryException;
import org.springframework.stereotype.Component;

@Component
public class MiddlewareConfigurationPropertiesConverter {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    public JsonSchema parseJsonSchemaValidatorSettings(Object rawSchema) {
        try {
            JsonBodyValidatorData data = jsonMapper.convertValue(rawSchema, JsonBodyValidatorData.class);
            JsonNode schema = jsonMapper.readTree(jsonMapper.writeValueAsString(data.getSchema()));
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersionDetector.detect(schema));
            return factory.getSchema(schema);
        } catch (JsonProcessingException e) {
            throw new BaseFactoryException(e.getMessage());
        }

    }
}

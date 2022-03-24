package com.skywaet.middlewarefactory.grpcserver.validators;

public interface XMLValidator {

    void validateXML(String xml, String rawSchema);
}
